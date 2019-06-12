package com.tokelon.toktales.desktop.lwjgl.render;

import com.google.inject.multibindings.Multibinder;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.engine.inject.annotation.RenderDrivers;
import com.tokelon.toktales.core.render.IRenderDriverFactory;

public class LWJGLRenderDriversInjectModule extends AbstractInjectModule {

	
	@Override
	protected void configure() {
		Multibinder<IRenderDriverFactory> renderDriverFactoryBinder = Multibinder.newSetBinder(binder(), IRenderDriverFactory.class, RenderDrivers.class);
		
		renderDriverFactoryBinder.addBinding().to(GLSpriteDriver.GLSpriteDriverFactory.class);
		renderDriverFactoryBinder.addBinding().to(GLSpriteFontDriver.GLSpriteFontDriverFactory.class);
		renderDriverFactoryBinder.addBinding().to(GLBitmapFontDriver.GLBitmapFontDriverFactory.class);
		renderDriverFactoryBinder.addBinding().to(GLShapeDriver.GLShapeDriverFactory.class);
		renderDriverFactoryBinder.addBinding().to(GLBitmapDriver.GLBitmapDriverFactory.class);
	}
	
}
