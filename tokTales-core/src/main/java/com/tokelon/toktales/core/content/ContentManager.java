package com.tokelon.toktales.core.content;

import javax.inject.Inject;

import com.tokelon.toktales.core.content.sprite.ISpriteManager;
import com.tokelon.toktales.core.content.sprite.ISpriteManager.ISpriteManagerFactory;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.render.ITextureManager;

public class ContentManager implements IContentManager {

	private static final String TAG = ContentManager.class.getSimpleName();
	

	private ILogger logger;
	private IResourceManager managerResource;
	private ISpriteManager managerSprite;
	private ITextureManager textureManager;
	
	@Inject
	public ContentManager(
			ILogger logger,
			IResourceManager resourceManager,
			ISpriteManager spriteManager,
			ITextureManager textureManager
	) {
		this.logger = logger;
		this.managerResource = resourceManager;
		this.managerSprite = spriteManager;
		this.textureManager = textureManager;
	}

	
	@Override
	public void startLoaders() {
		logger.i(TAG, "Starting content loaders");
		managerSprite.startLoading();		
	}
	
	@Override
	public void stopLoaders() {
		logger.i(TAG, "Stopping content loaders");
		managerSprite.stopLoading();		
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
	

}
