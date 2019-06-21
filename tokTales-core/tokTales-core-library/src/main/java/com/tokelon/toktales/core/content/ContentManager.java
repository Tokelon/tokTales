package com.tokelon.toktales.core.content;

import javax.inject.Inject;

import com.tokelon.toktales.core.content.manage.codepoint.ICodepointAssetManager;
import com.tokelon.toktales.core.content.manage.font.ITextureFontAssetManager;
import com.tokelon.toktales.core.content.sprite.ISpriteManager;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.render.ITextureManager;

public class ContentManager implements IContentManager {

	private static final String TAG = ContentManager.class.getSimpleName();
	

	private final ILogger logger;
	private final IResourceManager managerResource;
	private final ISpriteManager managerSprite;
	private final ITextureManager textureManager;
	private final ITextureFontAssetManager fontManager;
	private final ICodepointAssetManager codepointManager;
	
	@Inject
	public ContentManager(
			ILogger logger,
			IResourceManager resourceManager,
			ISpriteManager spriteManager,
			ITextureManager textureManager,
			ITextureFontAssetManager fontManager,
			ICodepointAssetManager codepointManager
	) {
		this.logger = logger;
		this.managerResource = resourceManager;
		this.managerSprite = spriteManager;
		this.textureManager = textureManager;
		this.fontManager = fontManager;
		this.codepointManager = codepointManager;
	}

	
	@Override
	public void startLoaders() {
		logger.i(TAG, "Starting content loaders");
		managerSprite.startLoading();
		fontManager.getLoader().start();
		codepointManager.getLoader().start();
	}
	
	@Override
	public void stopLoaders() {
		logger.i(TAG, "Stopping content loaders");
		managerSprite.stopLoading();
		fontManager.getLoader().stop();
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
	public ITextureFontAssetManager getFontAssetManager() {
		return fontManager;
	}
	
	@Override
	public ICodepointAssetManager getCodepointAssetManager() {
		return codepointManager;
	}

}
