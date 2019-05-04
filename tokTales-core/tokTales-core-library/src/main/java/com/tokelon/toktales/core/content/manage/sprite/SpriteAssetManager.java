package com.tokelon.toktales.core.content.manage.sprite;

import javax.inject.Inject;

import com.tokelon.toktales.core.content.manage.DefaultAssetManager;
import com.tokelon.toktales.core.content.manage.IAssetLoader;
import com.tokelon.toktales.core.content.manage.IAssetStore;
import com.tokelon.toktales.core.content.sprite.ISpriteAsset;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.util.options.IOptions;

public class SpriteAssetManager extends DefaultAssetManager<ISpriteAsset, ISpriteAssetKey, IOptions> implements ISpriteAssetManager {

	
	@Inject
	public SpriteAssetManager(ILogger logger, IAssetStore<ISpriteAsset, ISpriteAssetKey> assetStore, IAssetLoader<ISpriteAsset, ISpriteAssetKey, IOptions> assetLoader) {
		super(logger, assetStore, assetLoader);
	}
	
}
