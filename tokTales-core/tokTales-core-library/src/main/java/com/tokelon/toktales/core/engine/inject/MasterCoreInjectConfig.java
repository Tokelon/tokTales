package com.tokelon.toktales.core.engine.inject;

import com.tokelon.toktales.tools.core.sub.inject.config.MasterInjectConfig;

public class MasterCoreInjectConfig extends MasterInjectConfig {


	@Override
	protected void configure() {
		super.configure();
		
		// Core
		overrideWithFilters(new CoreOverrideInjectConfig());
		extendWithFilters(new CoreInjectConfig());
	}
	
}
