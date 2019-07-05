package com.tokelon.toktales.core.content;

import javax.inject.Inject;

import com.tokelon.toktales.core.content.manage.codepoint.ICodepointAssetManager;
import com.tokelon.toktales.core.content.manage.font.ITextureFontAssetManager;
import com.tokelon.toktales.core.content.manage.sprite.ISpriteAssetManager;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.render.ITextureManager;

public class ContentManager implements IContentManager {

	private static final String TAG = ContentManager.class.getSimpleName();
	

	private final ILogger logger;
	private final IResourceManager managerResource;
	private final ISpriteAssetManager spriteAssetManager;
	private final ITextureManager textureManager;
	private final ITextureFontAssetManager fontManager;
	private final ICodepointAssetManager codepointManager;
	
	@Inject
	public ContentManager(
			ILogger logger,
			IResourceManager resourceManager,
			ISpriteAssetManager spriteAssetManager,
			ITextureManager textureManager,
			ITextureFontAssetManager fontManager,
			ICodepointAssetManager codepointManager
	) {
		this.logger = logger;
		this.managerResource = resourceManager;
		this.spriteAssetManager = spriteAssetManager;
		this.textureManager = textureManager;
		this.fontManager = fontManager;
		this.codepointManager = codepointManager;
	}

	
	@Override
	public void startLoaders() {
		logger.i(TAG, "Starting content loaders");
		spriteAssetManager.getLoader().start();
		fontManager.getLoader().start();
		codepointManager.getLoader().start();
	}
	
	@Override
	public void stopLoaders() {
		logger.i(TAG, "Stopping content loaders");
		spriteAssetManager.getLoader().stop();
		fontManager.getLoader().stop();
		codepointManager.getLoader().stop();
	}

	
	@Override
	public IResourceManager getResourceManager() {
		return managerResource;
	}
	
	@Override
	public ISpriteAssetManager getSpriteAssetManager() {
		return spriteAssetManager;
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
