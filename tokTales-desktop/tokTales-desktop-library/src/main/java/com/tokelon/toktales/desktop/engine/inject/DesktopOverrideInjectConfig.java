package com.tokelon.toktales.desktop.engine.inject;

import com.tokelon.toktales.core.engine.inject.HierarchicalInjectConfig;

public class DesktopOverrideInjectConfig extends HierarchicalInjectConfig {


	@Override
	public void configure() {
		super.configure();
		
		extend(new DesktopOverrideInjectModule());
	}
	
}
