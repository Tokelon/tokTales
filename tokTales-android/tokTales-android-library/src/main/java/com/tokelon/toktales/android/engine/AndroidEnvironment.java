package com.tokelon.toktales.android.engine;

import com.tokelon.toktales.core.engine.IEnvironment;

public class AndroidEnvironment implements IEnvironment {


	public static final String ANDROID_PLATFORM_NAME = "Android";


	@Override
	public String getPlatformName() {
		return ANDROID_PLATFORM_NAME;
	}

}
