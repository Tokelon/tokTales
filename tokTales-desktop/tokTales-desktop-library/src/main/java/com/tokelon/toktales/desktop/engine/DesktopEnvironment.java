package com.tokelon.toktales.desktop.engine;

import com.tokelon.toktales.core.engine.IEnvironment;

public class DesktopEnvironment implements IEnvironment {


	public static final String DESKTOP_PLATFORM_NAME = "Desktop";


	@Override
	public String getPlatformName() {
		return DESKTOP_PLATFORM_NAME;
	}

}
