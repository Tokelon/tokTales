package com.tokelon.toktales.extensions.core.engine.inject;

import com.tokelon.toktales.tools.core.sub.inject.config.HierarchicalInjectConfig;

public class CoreExtensionsOverrideInjectConfig extends HierarchicalInjectConfig {

	
	@Override
	protected void configure() {
		super.configure();
		
		extend(new CoreExtensionsOverrideInjectModule());
	}
	
}
