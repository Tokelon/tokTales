package com.tokelon.toktales.core.content;

import javax.inject.Inject;

import com.tokelon.toktales.core.content.sprite.ISpriteManager;
import com.tokelon.toktales.core.content.text.ICodepointManager;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.render.ITextureManager;

public class ContentManager implements IContentManager {

	private static final String TAG = ContentManager.class.getSimpleName();
	

	private final ILogger logger;
	private final IResourceManager managerResource;
	private final ISpriteManager managerSprite;
	private final ITextureManager textureManager;
	private final ICodepointManager codepointManager;
	
	@Inject
	public ContentManager(
			ILogger logger,
			IResourceManager resourceManager,
			ISpriteManager spriteManager,
			ITextureManager textureManager,
			ICodepointManager codepointManager
	) {
		this.logger = logger;
		this.managerResource = resourceManager;
		this.managerSprite = spriteManager;
		this.textureManager = textureManager;
		this.codepointManager = codepointManager;
	}

	
	@Override
	public void startLoaders() {
		logger.i(TAG, "Starting content loaders");
		managerSprite.startLoading();
		codepointManager.getLoader().start();
	}
	
	@Override
	public void stopLoaders() {
		logger.i(TAG, "Stopping content loaders");
		managerSprite.stopLoading();
		codepointManager.getLoader().stop();
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
	public ICodepointManager getCodepointManager() {
		return codepointManager;
	}

}
