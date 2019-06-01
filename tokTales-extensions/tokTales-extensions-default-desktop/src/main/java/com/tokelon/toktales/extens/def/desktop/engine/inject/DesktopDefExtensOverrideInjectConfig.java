package com.tokelon.toktales.extens.def.desktop.engine.inject;

import com.tokelon.toktales.core.engine.inject.HierarchicalInjectConfig;

public class DesktopDefExtensOverrideInjectConfig extends HierarchicalInjectConfig {

	
	@Override
	protected void configure() {
		super.configure();
		
		extend(new DesktopDefExtensOverrideInjectModule());
	}
	
}
