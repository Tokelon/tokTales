package com.tokelon.toktales.desktop.lwjgl;

import com.tokelon.toktales.core.content.IContentUtils;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.desktop.content.DesktopContentUtils;

public class LWJGLOverrideInjectModule extends AbstractInjectModule {


	@Override
	protected void configure() {
		super.configure();
		
		bind(IContentUtils.class).to(DesktopContentUtils.class);
	}
	
}
