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

package com.liferay.ide.ui.kaleo.tests;

import com.liferay.ide.ui.liferay.SwtbotBase;
import com.liferay.ide.ui.liferay.base.ProjectSupport;
import com.liferay.ide.ui.liferay.base.SdkSupport;
import com.liferay.ide.ui.liferay.base.TomcatSupport;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;

/**
 * @author Haoyi Sun
 */
public class NewKaleoProjectWizardSdkTests extends SwtbotBase {

	public static TomcatSupport tomcat = new TomcatSupport(bot);

	@ClassRule
	public static RuleChain chain = RuleChain.outerRule(tomcat).around(new SdkSupport(bot, tomcat));

	@Test
	public void createKaleoWorkflowAssignCreatorOnProject() {
		viewAction.switchLiferayPerspective();

		wizardAction.openNewLiferayPluginProjectWizard();

		wizardAction.newPlugin.preparePortletSdk(project.getName());

		wizardAction.finish();

		jobAction.waitForIvy();

		jobAction.waitForValidate(project.getName());

		viewAction.switchKaleoDesignerPerspective();

		wizardAction.openNewLiferayKaleoWorkflowWizard();

		wizardAction.newKaleoWorkflow.openSelectProjectDialog();

		dialogAction.prepareText(project.getName());

		dialogAction.confirm();

		wizardAction.next();

		wizardAction.next();

		wizardAction.finish();

		viewAction.project.closeAndDelete(project.getName());
	}

	@Test
	public void createKaleoWorkflowAssignResourceActionOnProject() {
		viewAction.switchLiferayPerspective();

		wizardAction.openNewLiferayPluginProjectWizard();

		wizardAction.newPlugin.preparePortletSdk(project.getName());

		wizardAction.finish();

		jobAction.waitForIvy();

		jobAction.waitForValidate(project.getName());

		viewAction.switchKaleoDesignerPerspective();

		wizardAction.openNewLiferayKaleoWorkflowWizard();

		wizardAction.newKaleoWorkflow.openSelectProjectDialog();

		dialogAction.prepareText(project.getName());

		dialogAction.confirm();

		wizardAction.next();

		wizardAction.chooseAssignmentType.selectAssignResourceActions();

		wizardAction.next();

		wizardAction.makeTaskAssignAction.addResourceAction("test-action");

		wizardAction.finish();

		viewAction.project.closeAndDelete(project.getName());
	}

	@Test
	public void createKaleoWorkflowAssignRoleByIdOnProject() {
		viewAction.switchLiferayPerspective();

		wizardAction.openNewLiferayPluginProjectWizard();

		wizardAction.newPlugin.preparePortletSdk(project.getName());

		wizardAction.finish();

		jobAction.waitForIvy();

		jobAction.waitForValidate(project.getName());

		viewAction.switchKaleoDesignerPerspective();

		wizardAction.openNewLiferayKaleoWorkflowWizard();

		wizardAction.newKaleoWorkflow.openSelectProjectDialog();

		dialogAction.prepareText(project.getName());

		dialogAction.confirm();

		wizardAction.next();

		wizardAction.chooseAssignmentType.selectAssignRoleById();

		wizardAction.next();

		wizardAction.makeTaskAssignRoleById.prepareRoleId("123");

		wizardAction.finish();

		viewAction.project.closeAndDelete(project.getName());
	}

	@Test
	public void createKaleoWorkflowAssignRoleTypeOnProject() {
		viewAction.switchLiferayPerspective();

		wizardAction.openNewLiferayPluginProjectWizard();

		wizardAction.newPlugin.preparePortletSdk(project.getName());

		wizardAction.finish();

		jobAction.waitForIvy();

		jobAction.waitForValidate(project.getName());

		viewAction.switchKaleoDesignerPerspective();

		wizardAction.openNewLiferayKaleoWorkflowWizard();

		wizardAction.newKaleoWorkflow.openSelectProjectDialog();

		dialogAction.prepareText(project.getName());

		dialogAction.confirm();

		wizardAction.next();

		wizardAction.chooseAssignmentType.selectAssignRoleType();

		wizardAction.next();

		wizardAction.makeTaskAssignRoleType.addRole();

		wizardAction.makeTaskAssignRoleType.deleteRole();

		wizardAction.finish();

		viewAction.project.closeAndDelete(project.getName());
	}

	@Test
	public void createKaleoWorkflowAssignScriptOnProject() {
		viewAction.switchLiferayPerspective();

		wizardAction.openNewLiferayPluginProjectWizard();

		wizardAction.newPlugin.preparePortletSdk(project.getName());

		wizardAction.finish();

		jobAction.waitForIvy();

		jobAction.waitForValidate(project.getName());

		viewAction.switchKaleoDesignerPerspective();

		wizardAction.openNewLiferayKaleoWorkflowWizard();

		wizardAction.newKaleoWorkflow.openSelectProjectDialog();

		dialogAction.prepareText(project.getName());

		dialogAction.confirm();

		wizardAction.next();

		wizardAction.chooseAssignmentType.selectAssignScriptedAssignment();

		wizardAction.next();

		wizardAction.makeTaskAssignScript.prepareScriptLanguage("Groovy");

		wizardAction.finish();

		viewAction.project.closeAndDelete(project.getName());
	}

	@Test
	public void createKaleoWorkflowAssignUserOnProject() {
		viewAction.switchLiferayPerspective();

		wizardAction.openNewLiferayPluginProjectWizard();

		wizardAction.newPlugin.preparePortletSdk(project.getName());

		wizardAction.finish();

		jobAction.waitForIvy();

		jobAction.waitForValidate(project.getName());

		viewAction.switchKaleoDesignerPerspective();

		wizardAction.openNewLiferayKaleoWorkflowWizard();

		wizardAction.newKaleoWorkflow.openSelectProjectDialog();

		dialogAction.prepareText(project.getName());

		dialogAction.confirm();

		wizardAction.next();

		wizardAction.chooseAssignmentType.selectAssignUser();

		wizardAction.next();

		wizardAction.makeTaskAssignUser.prepareEmailAddress("test@liferay.com");

		wizardAction.finish();

		viewAction.project.closeAndDelete(project.getName());
	}

	@Test
	public void createKaleoWorkflowOnProject() {
		viewAction.switchLiferayPerspective();

		wizardAction.openNewLiferayPluginProjectWizard();

		wizardAction.newPlugin.preparePortletSdk(project.getName());

		wizardAction.finish();

		jobAction.waitForIvy();

		jobAction.waitForValidate(project.getName());

		viewAction.switchKaleoDesignerPerspective();

		wizardAction.openNewLiferayKaleoWorkflowWizard();

		wizardAction.newKaleoWorkflow.openSelectProjectDialog();

		dialogAction.prepareText(project.getName());

		dialogAction.confirm();

		wizardAction.finish();

		viewAction.project.closeAndDelete(project.getName());
	}

	@Rule
	public ProjectSupport project = new ProjectSupport(bot);

}