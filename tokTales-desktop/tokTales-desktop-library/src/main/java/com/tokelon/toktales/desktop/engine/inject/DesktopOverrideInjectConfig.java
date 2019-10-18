package com.tokelon.toktales.desktop.engine.inject;

import com.tokelon.toktales.desktop.lwjgl.LWJGLOverrideInjectModule;
import com.tokelon.toktales.tools.core.sub.inject.config.HierarchicalInjectConfig;

public class DesktopOverrideInjectConfig extends HierarchicalInjectConfig {


	@Override
	public void configure() {
		super.configure();
		
		extend(new DesktopOverrideInjectModule());
		
		extend(new LWJGLOverrideInjectModule());
	}
	
}
