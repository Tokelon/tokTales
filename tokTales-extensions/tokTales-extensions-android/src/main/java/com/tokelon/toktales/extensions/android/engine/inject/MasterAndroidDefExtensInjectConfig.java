package com.tokelon.toktales.extensions.android.engine.inject;

import com.tokelon.toktales.android.engine.inject.AndroidInjectConfig;
import com.tokelon.toktales.android.engine.inject.AndroidOverrideInjectConfig;
import com.tokelon.toktales.core.engine.inject.CoreInjectConfig;
import com.tokelon.toktales.core.engine.inject.CoreOverrideInjectConfig;
import com.tokelon.toktales.extensions.core.engine.inject.CoreDefExtensInjectConfig;
import com.tokelon.toktales.extensions.core.engine.inject.CoreDefExtensOverrideInjectConfig;
import com.tokelon.toktales.tools.core.sub.inject.config.MasterInjectConfig;

public class MasterAndroidDefExtensInjectConfig extends MasterInjectConfig {


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
        overrideWithFilters(new CoreDefExtensOverrideInjectConfig());
        extendWithFilters(new CoreDefExtensInjectConfig());
		
		// Android DefaultExtensions
		overrideWithFilters(new AndroidDefExtensOverrideInjectConfig());
		extendWithFilters(new AndroidDefExtensInjectConfig());
	}
	
}
