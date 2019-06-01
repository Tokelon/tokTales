package com.tokelon.toktales.extens.def.core.engine.inject;

import com.tokelon.toktales.core.engine.inject.CoreInjectConfig;
import com.tokelon.toktales.core.engine.inject.CoreOverrideInjectConfig;
import com.tokelon.toktales.core.engine.inject.MasterInjectConfig;

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
