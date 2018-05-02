package com.tokelon.toktales.core.content;

import javax.inject.Inject;

import com.tokelon.toktales.core.content.sprite.ISpriteManager;
import com.tokelon.toktales.core.content.sprite.ISpriteManager.ISpriteManagerFactory;
import com.tokelon.toktales.core.render.ITextureManager;

public class ContentManager implements IContentManager {

	private static final String TAG = ContentManager.class.getSimpleName();
	
	
	private IResourceManager managerResource;
	private ISpriteManager managerSprite;
	private ITextureManager textureManager;
	
	@Inject
	public ContentManager(IResourceManager resourceManager, ISpriteManagerFactory spriteManagerFactory, ITextureManager textureManager) {
		this.managerResource = resourceManager;
		this.managerSprite = spriteManagerFactory.create(resourceManager);
		this.textureManager = textureManager;
		
		managerSprite.startLoading();
		
		//managerSprite.stopLoading();	// TODO: Important - Stop this when terminating!
	}
	

	
	@Override
	public IResourceManager getResourceManager() {
		return managerResource;
	}
	
	@Override
	public ISpriteManager getSpriteManager() {
		return managerSprite;
	}

	@Override
	public ITextureManager getTextureManager() {
		return textureManager;
	}
	

	@Override
	public void notifyResourcesChanged() {
		
		// Do something?
	}

}
