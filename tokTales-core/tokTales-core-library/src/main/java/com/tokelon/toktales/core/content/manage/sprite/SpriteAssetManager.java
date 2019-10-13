package com.tokelon.toktales.core.content.manage.sprite;

import javax.inject.Inject;

import org.slf4j.ILoggerFactory;

import com.tokelon.toktales.core.content.sprite.ISpriteAsset;
import com.tokelon.toktales.tools.assets.loader.IAssetLoader;
import com.tokelon.toktales.tools.assets.manager.DefaultAssetManager;
import com.tokelon.toktales.tools.assets.manager.IAssetStore;
import com.tokelon.toktales.tools.assets.manager.ISpecialAssetManager;
import com.tokelon.toktales.tools.core.objects.options.IOptions;

public class SpriteAssetManager extends DefaultAssetManager<ISpriteAsset, ISpriteAssetKey, IOptions> implements ISpriteAssetManager {

	
	@Inject
	public SpriteAssetManager(ILoggerFactory loggerFactory, ISpecialAssetManager<ISpriteAsset> specialAssetManager, IAssetStore<ISpriteAsset, ISpriteAssetKey> assetStore, IAssetLoader<ISpriteAsset, ISpriteAssetKey, IOptions> assetLoader) {
		super(loggerFactory, specialAssetManager, assetStore, assetLoader);
	}
	
}
