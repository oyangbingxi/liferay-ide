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

package com.liferay.ide.ui.service.builder.tests;

import com.liferay.ide.ui.liferay.support.server.PureTomcatDxpSupport;
import com.liferay.ide.ui.liferay.support.server.ServerSupport;
import com.liferay.ide.ui.liferay.util.RuleUtil;
import com.liferay.ide.ui.service.builder.deploy.base.DeployServiceBuilderModuleGradleTomcat7xBase;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.RuleChain;

/**
 * @author Ying Xu
 */
public class DeployServiceBuilderModuleGradleTomcatDxpTests extends DeployServiceBuilderModuleGradleTomcat7xBase {

	@ClassRule
	public static RuleChain chain = RuleUtil.getTomcat7xRunningRuleChain(bot, getServer());

	public static ServerSupport getServer() {
		if (PureTomcatDxpSupport.isNot(server)) {
			server = new PureTomcatDxpSupport(bot);
		}

		return server;
	}

	@Test
	public void deployServiceBuilder() {
		super.deployServiceBuilder();
	}

}