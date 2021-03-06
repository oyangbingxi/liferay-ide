/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.ide.gradle.core.tests;

import com.liferay.ide.core.IBundleProject;
import com.liferay.ide.core.LiferayCore;
import com.liferay.ide.core.LiferayNature;
import com.liferay.ide.core.tests.TestUtil;
import com.liferay.ide.core.util.CoreUtil;
import com.liferay.ide.project.core.ProjectCore;
import com.liferay.ide.project.core.modules.BladeCLI;
import com.liferay.ide.project.core.workspace.ImportLiferayWorkspaceOp;
import com.liferay.ide.server.util.ServerUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.sapphire.platform.ProgressMonitorBridge;
import org.eclipse.wst.server.core.IModule;
import org.eclipse.wst.server.core.IServer;
import org.eclipse.wst.server.core.IServerWorkingCopy;
import org.eclipse.wst.server.core.internal.Module;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Gregory Amerson
 */
@Ignore
@SuppressWarnings("restriction")
public class AllBladeSamplesPublishTest {

	@BeforeClass
	public static void importAllBladeSamples() throws Exception {
		Util.deleteAllWorkspaceProjects();

		ImportLiferayWorkspaceOp op = ImportLiferayWorkspaceOp.TYPE.instantiate();

		File projectDir = _copyTestProjectToWorkspace("projects/all-blade-samples");

		op.setWorkspaceLocation(projectDir.getCanonicalPath());

		op.setProvisionLiferayBundle(true);

		op.setServerName(_WORKSPACE_SERVER_NAME);

		NullProgressMonitor monitor = new NullProgressMonitor();

		op.execute(ProgressMonitorBridge.create(monitor));

		Util.waitForBuildAndValidation();

		IWorkspaceRoot root = CoreUtil.getWorkspaceRoot();

		IProject project = root.getProject("all-blade-samples");

		Assert.assertNotNull(project);

		Assert.assertTrue(project.exists());

		_assertLiferayProject("blade.portlet.jsp");

		_assertLiferayProject("blade.gogo");

		IServer wsServer = ServerUtil.getServer(_WORKSPACE_SERVER_NAME);

		Assert.assertNotNull(wsServer);

		long timeoutExpiredMs = System.currentTimeMillis() + 300000;

		wsServer.start("run", monitor);

		while (true) {
			Assert.assertEquals(wsServer.getServerState(), IServer.STATE_STARTING, wsServer.getServerState());

			Thread.sleep(500);

			if ((wsServer.getServerState() == IServer.STATE_STARTED) ||
				(System.currentTimeMillis() >= timeoutExpiredMs)) {

				break;
			}
		}

		Assert.assertEquals(wsServer.getServerState(), IServer.STATE_STARTED, wsServer.getServerState());
	}

	@AfterClass
	public static void restoreBladeCLIPrefsToDefault() throws Exception {
		IEclipsePreferences defaults = DefaultScope.INSTANCE.getNode(ProjectCore.PLUGIN_ID);

		IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(ProjectCore.PLUGIN_ID);

		final String defaultValue = defaults.get(BladeCLI.BLADE_CLI_REPO_URL, "");

		prefs.put(BladeCLI.BLADE_CLI_REPO_URL, defaultValue);

		IServer wsServer = ServerUtil.getServer(_WORKSPACE_SERVER_NAME);

		wsServer.stop(true);
	}

	@Test
	public void testAllProjectsImported() throws Exception {
		List<IProject> bladeProjects = _getAllBladeProjects();

		Assert.assertEquals("", 35, bladeProjects.size());
	}

	@Test
	public void testPublishAllBladeSamples() throws Exception {
		IServer server = ServerUtil.getServer(_WORKSPACE_SERVER_NAME);

		IServerWorkingCopy serverWC = server.createWorkingCopy();

		List<IProject> bladeProjects = _getAllBladeProjects();

		List<IModule> modules = new ArrayList<>();

		for (IProject bladeProject : bladeProjects) {
			String name = bladeProject.getName();

			IModule module = new Module(null, name, name, "liferay.bundle", "1.0", bladeProject);

			modules.add(module);
		}

		IProgressMonitor monitor = new NullProgressMonitor();

		serverWC.modifyModules(modules.toArray(new IModule[0]), null, monitor);

		server = serverWC.save(true, monitor);

		Util.waitForBuildAndValidation();

		IModule[] serverModules = server.getModules();

		Assert.assertEquals("", 35, serverModules.length);

		String[] retval = BladeCLI.execute("sh lb -s blade");

		for (IModule serverModule : serverModules) {
			IBundleProject bundleProject = LiferayCore.create(IBundleProject.class, serverModule.getProject());

			String bsn = bundleProject.getSymbolicName();

			boolean foundBundle = false;

			for (String bundleString : retval) {
				if (bundleString.contains(bsn)) {
					foundBundle = true;

					if (!bundleString.contains("blade.hook.jsp")) {
						Assert.assertTrue("Error in bundle state: " + bundleString, bundleString.contains("Active"));
					}
					else {
						Assert.assertTrue("Error in bundle state: " + bundleString, bundleString.contains("Resolved"));
					}

					break;
				}
			}

			Assert.assertTrue(foundBundle);
		}
	}

	private static void _assertLiferayProject(String projectName) {
		IProject project = CoreUtil.getProject(projectName);

		Assert.assertTrue("Project " + projectName + " doesn't exist.", project.exists());
		Assert.assertTrue("Project " + projectName + " doesn't haven liferay nature", LiferayNature.hasNature(project));
	}

	private static File _copyTestProjectToWorkspace(String path) throws Exception {
		File src = new File(path);

		IPath location = CoreUtil.getWorkspaceRootLocation();

		File dst = new File(location.toFile(), src.getName());

		TestUtil.copyDir(src, dst);

		return dst;
	}

	private List<IProject> _getAllBladeProjects() {
		List<IProject> bladeProjects = new ArrayList<>();

		Stream<IProject> projects = Stream.of(CoreUtil.getAllProjects());

		projects.filter(
			project -> project.getName().startsWith("blade")
		).forEach(
			bladeProjects::add
		);

		return bladeProjects;
	}

	private static final String _WORKSPACE_SERVER_NAME = "workspace-server";

}