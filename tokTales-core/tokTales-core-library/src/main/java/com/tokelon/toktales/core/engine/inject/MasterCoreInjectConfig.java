package com.tokelon.toktales.core.engine.inject;

public class MasterCoreInjectConfig extends MasterInjectConfig {


	@Override
	protected void configure() {
		super.configure();
		
		// Core
		overrideWithFilters(new CoreOverrideInjectConfig());
		extendWithFilters(new CoreInjectConfig());
	}
	
}
