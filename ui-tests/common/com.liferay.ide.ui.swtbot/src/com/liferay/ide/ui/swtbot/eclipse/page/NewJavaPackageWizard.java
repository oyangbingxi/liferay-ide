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

package com.liferay.ide.ui.swtbot.eclipse.page;

import com.liferay.ide.ui.swtbot.page.Text;
import com.liferay.ide.ui.swtbot.page.Wizard;

import org.eclipse.swtbot.swt.finder.SWTBot;

/**
 * @author Ying Xu
 */
public class NewJavaPackageWizard extends Wizard {

	public NewJavaPackageWizard(SWTBot bot) {
		super(bot);
	}

	public Text getName() {
		return new Text(getShell().bot(), NAME);
	}

	public Text getSourceFolder() {
		return new Text(getShell().bot(), SOURCE_FOLDER);
	}

}