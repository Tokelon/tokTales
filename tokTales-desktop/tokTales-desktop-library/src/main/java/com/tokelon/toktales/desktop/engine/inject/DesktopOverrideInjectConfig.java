package com.tokelon.toktales.desktop.engine.inject;

import com.tokelon.toktales.core.engine.inject.HierarchicalInjectConfig;
import com.tokelon.toktales.desktop.lwjgl.LWJGLOverrideInjectModule;

public class DesktopOverrideInjectConfig extends HierarchicalInjectConfig {


	@Override
	public void configure() {
		super.configure();
		
		extend(new DesktopOverrideInjectModule());
		
		extend(new LWJGLOverrideInjectModule());
	}
	
}
