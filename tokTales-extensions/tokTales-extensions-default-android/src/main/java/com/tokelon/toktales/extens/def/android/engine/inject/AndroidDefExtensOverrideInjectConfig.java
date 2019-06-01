package com.tokelon.toktales.extens.def.android.engine.inject;

import com.tokelon.toktales.core.engine.inject.HierarchicalInjectConfig;

public class AndroidDefExtensOverrideInjectConfig extends HierarchicalInjectConfig {

	
	@Override
	protected void configure() {
		super.configure();
		
		extend(new AndroidDefExtensOverrideInjectModule());
	}
	
}
