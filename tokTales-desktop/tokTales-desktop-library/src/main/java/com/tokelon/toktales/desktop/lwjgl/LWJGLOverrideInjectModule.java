package com.tokelon.toktales.desktop.lwjgl;

import com.google.inject.Scopes;
import com.tokelon.toktales.core.content.IContentUtils;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.render.opengl.IGLBufferUtils;
import com.tokelon.toktales.desktop.content.DesktopContentUtils;
import com.tokelon.toktales.desktop.lwjgl.render.opengl.DesktopGLBufferUtils;

public class LWJGLOverrideInjectModule extends AbstractInjectModule {


	@Override
	protected void configure() {
		super.configure();
		
		bind(IContentUtils.class).to(DesktopContentUtils.class);
		
		bind(IGLBufferUtils.class).to(DesktopGLBufferUtils.class).in(Scopes.SINGLETON);
	}
	
}
