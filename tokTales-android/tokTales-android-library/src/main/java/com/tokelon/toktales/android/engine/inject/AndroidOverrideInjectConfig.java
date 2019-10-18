package com.tokelon.toktales.android.engine.inject;

import com.tokelon.toktales.tools.core.sub.inject.config.HierarchicalInjectConfig;

public class AndroidOverrideInjectConfig extends HierarchicalInjectConfig {

	
	@Override
	protected void configure() {
		super.configure();
		
		extend(new AndroidOverrideInjectModule());
		
		extend(new AndroidPlatformOverrideInjectModule());
	}
	
}
