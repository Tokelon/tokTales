package com.tokelon.toktales.extensions.android.engine.inject;

import com.tokelon.toktales.android.engine.inject.AndroidInjectConfig;
import com.tokelon.toktales.android.engine.inject.AndroidOverrideInjectConfig;
import com.tokelon.toktales.core.engine.inject.CoreInjectConfig;
import com.tokelon.toktales.core.engine.inject.CoreOverrideInjectConfig;
import com.tokelon.toktales.extensions.core.engine.inject.CoreExtensionsInjectConfig;
import com.tokelon.toktales.extensions.core.engine.inject.CoreExtensionsOverrideInjectConfig;
import com.tokelon.toktales.tools.core.sub.inject.config.MasterInjectConfig;

public class MasterAndroidExtensionsInjectConfig extends MasterInjectConfig {


	@Override
	protected void configure() {
		super.configure();

		// Core
		overrideWithFilters(new CoreOverrideInjectConfig());
		extendWithFilters(new CoreInjectConfig());
		
		// Android
		overrideWithFilters(new AndroidOverrideInjectConfig());
		extendWithFilters(new AndroidInjectConfig());

		// Core DefaultExtensions
        overrideWithFilters(new CoreExtensionsOverrideInjectConfig());
        extendWithFilters(new CoreExtensionsInjectConfig());
		
		// Android DefaultExtensions
		overrideWithFilters(new AndroidExtensionsOverrideInjectConfig());
		extendWithFilters(new AndroidExtensionsInjectConfig());
	}
	
}
