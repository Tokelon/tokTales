package com.tokelon.toktales.extens.def.android.engine.inject;

import com.tokelon.toktales.tools.core.sub.inject.config.HierarchicalInjectConfig;

public class AndroidDefExtensOverrideInjectConfig extends HierarchicalInjectConfig {

	
	@Override
	protected void configure() {
		super.configure();
		
		extend(new AndroidDefExtensOverrideInjectModule());
	}
	
}
