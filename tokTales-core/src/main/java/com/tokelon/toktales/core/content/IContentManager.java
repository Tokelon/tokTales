package com.tokelon.toktales.core.content;

import com.tokelon.toktales.core.content.sprite.ISpriteManager;
import com.tokelon.toktales.core.render.ITextureManager;

public interface IContentManager {


	public void startLoaders();
	public void stopLoaders();
	
	
	public IResourceManager getResourceManager();
	
	public ISpriteManager getSpriteManager();
	
	public ITextureManager getTextureManager();

	
	
	
	
	public enum SpecialContent implements ISpecialContent {
		SPRITE_EMPTY,
		SPRITE_NOT_FOUND,
		SPRITE_LOAD_ERROR;
	}

}
