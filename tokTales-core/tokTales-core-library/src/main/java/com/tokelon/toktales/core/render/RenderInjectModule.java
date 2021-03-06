package com.tokelon.toktales.core.render;

import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.render.renderer.CharRenderer;
import com.tokelon.toktales.core.render.renderer.ICharRenderer;
import com.tokelon.toktales.core.render.renderer.IImageRenderer;
import com.tokelon.toktales.core.render.renderer.IShapeRenderer;
import com.tokelon.toktales.core.render.renderer.ImageRenderer;
import com.tokelon.toktales.core.render.renderer.ShapeRenderer;
import com.tokelon.toktales.core.render.texture.DefaultTextureCoordinator;
import com.tokelon.toktales.core.render.texture.ITextureCoordinator;
import com.tokelon.toktales.core.render.texture.ITextureCoordinator.ITextureCoordinatorFactory;

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
