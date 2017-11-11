package com.tokelon.toktales.core.content;

import com.tokelon.toktales.core.content.sprite.ISpriteManager;
import com.tokelon.toktales.core.prog.IProgramInterface;

public interface IContentManager extends IProgramInterface {

	
	public IResourceManager getResourceManager();
	
	public ISpriteManager getSpriteManager();
	

	
	public void notifyResourcesChanged();

	
	
	
	public enum SpecialContent implements ISpecialContent {
		SPRITE_EMPTY,
		SPRITE_NOT_FOUND,
		SPRITE_LOAD_ERROR;
	}

}
