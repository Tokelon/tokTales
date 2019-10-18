package com.tokelon.toktales.core.engine.inject;

import com.tokelon.toktales.tools.core.sub.inject.config.HierarchicalInjectConfig;

public class CoreOverrideInjectConfig extends HierarchicalInjectConfig {


	@Override
	public void configure() {
		super.configure();
		
		// Add override modules here
		// This is where a CoreOverridesInjectModule would be placed
		// Nothing yet
	}
	
}
