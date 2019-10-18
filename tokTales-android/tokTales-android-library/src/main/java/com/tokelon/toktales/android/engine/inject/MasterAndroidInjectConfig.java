package com.tokelon.toktales.android.engine.inject;

import com.tokelon.toktales.core.engine.inject.CoreInjectConfig;
import com.tokelon.toktales.core.engine.inject.CoreOverrideInjectConfig;
import com.tokelon.toktales.tools.core.sub.inject.config.MasterInjectConfig;

public class MasterAndroidInjectConfig extends MasterInjectConfig {


	@Override
	protected void configure() {
		super.configure();

		// Core
		overrideWithFilters(new CoreOverrideInjectConfig());
		extendWithFilters(new CoreInjectConfig());
		
		// Android
		overrideWithFilters(new AndroidOverrideInjectConfig());
		extendWithFilters(new AndroidInjectConfig());
	}
	
}
