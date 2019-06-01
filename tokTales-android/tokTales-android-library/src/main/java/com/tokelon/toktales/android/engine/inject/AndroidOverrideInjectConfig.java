package com.tokelon.toktales.android.engine.inject;

import com.tokelon.toktales.core.engine.inject.HierarchicalInjectConfig;

public class AndroidOverrideInjectConfig extends HierarchicalInjectConfig {

	
	@Override
	protected void configure() {
		super.configure();
		
		extend(new AndroidOverrideInjectModule());
	}
	
}
