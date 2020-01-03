package com.tokelon.toktales.core.render;

import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.render.ITextureCoordinator.ITextureCoordinatorFactory;

public class RenderInjectModule extends AbstractInjectModule {


	@Override
	protected void configure() {
		bind(ITextureCoordinator.class).to(DefaultTextureCoordinator.class);
		bind(ITextureCoordinatorFactory.class).to(DefaultTextureCoordinator.TextureCoordinatorFactory.class);
		 
		bind(ICharRenderer.class).to(CharRenderer.class);
		bind(IImageRenderer.class).to(ImageRenderer.class);
		bind(IShapeRenderer.class).to(ShapeRenderer.class);
	}
	
}
