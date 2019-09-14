package com.tokelon.toktales.core.content;

import javax.inject.Inject;

import com.tokelon.toktales.core.content.manage.bitmap.IBitmapAssetManager;
import com.tokelon.toktales.core.content.manage.codepoint.ICodepointAssetManager;
import com.tokelon.toktales.core.content.manage.font.ITextureFontAssetManager;
import com.tokelon.toktales.core.content.manage.sprite.ISpriteAssetManager;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.core.render.ITextureManager;

public class ContentManager implements IContentManager {


	private final ILogger logger;
	private final IResourceManager managerResource;
	private final IBitmapAssetManager bitmapAssetManager;
	private final ISpriteAssetManager spriteAssetManager;
	private final ITextureManager textureManager;
	private final ITextureFontAssetManager fontManager;
	private final ICodepointAssetManager codepointManager;
	
	@Inject
	public ContentManager(
			ILogging logging,
			IResourceManager resourceManager,
			IBitmapAssetManager bitmapAssetManager,
			ISpriteAssetManager spriteAssetManager,
			ITextureManager textureManager,
			ITextureFontAssetManager fontManager,
			ICodepointAssetManager codepointManager
	) {
		this.logger = logging.getLogger(getClass());
		this.managerResource = resourceManager;
		this.bitmapAssetManager = bitmapAssetManager;
		this.spriteAssetManager = spriteAssetManager;
		this.textureManager = textureManager;
		this.fontManager = fontManager;
		this.codepointManager = codepointManager;
	}

	
	@Override
	public void startLoaders() {
		logger.info("Starting content loaders");
		bitmapAssetManager.getLoader().start();
		spriteAssetManager.getLoader().start();
		fontManager.getLoader().start();
		codepointManager.getLoader().start();
	}
	
	@Override
	public void stopLoaders() {
		logger.info("Stopping content loaders");
		bitmapAssetManager.getLoader().stop();
		spriteAssetManager.getLoader().stop();
		fontManager.getLoader().stop();
		codepointManager.getLoader().stop();
	}

	
	@Override
	public IResourceManager getResourceManager() {
		return managerResource;
	}
	
	@Override
	public IBitmapAssetManager getBitmapAssetManager() {
		return bitmapAssetManager;
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
