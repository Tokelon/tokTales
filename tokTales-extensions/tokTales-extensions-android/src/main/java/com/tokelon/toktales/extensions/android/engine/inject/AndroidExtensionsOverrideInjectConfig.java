package com.tokelon.toktales.extensions.android.engine.inject;

import com.tokelon.toktales.tools.core.sub.inject.config.HierarchicalInjectConfig;

public class AndroidExtensionsOverrideInjectConfig extends HierarchicalInjectConfig {

	
	@Override
	protected void configure() {
		super.configure();
		
		extend(new AndroidExtensionsOverrideInjectModule());
	}
	
}
