package com.tokelon.toktales.extensions.core.engine.inject;

import com.tokelon.toktales.core.engine.inject.CoreInjectConfig;
import com.tokelon.toktales.core.engine.inject.CoreOverrideInjectConfig;
import com.tokelon.toktales.tools.core.sub.inject.config.MasterInjectConfig;

public class MasterCoreDefExtensInjectConfig extends MasterInjectConfig {


	@Override
	protected void configure() {
		super.configure();
		
		// Core
		overrideWithFilters(new CoreOverrideInjectConfig());
		extendWithFilters(new CoreInjectConfig());
		
        // Core DefaultExtensions
        overrideWithFilters(new CoreDefExtensOverrideInjectConfig());
        extendWithFilters(new CoreDefExtensInjectConfig());
	}
	
}
