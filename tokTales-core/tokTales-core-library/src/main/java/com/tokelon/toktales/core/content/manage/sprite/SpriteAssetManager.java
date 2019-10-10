package com.tokelon.toktales.core.content.manage.sprite;

import javax.inject.Inject;

import com.tokelon.toktales.core.content.manage.DefaultAssetManager;
import com.tokelon.toktales.core.content.manage.IAssetLoader;
import com.tokelon.toktales.core.content.manage.IAssetStore;
import com.tokelon.toktales.core.content.manage.ISpecialAssetManager;
import com.tokelon.toktales.core.content.sprite.ISpriteAsset;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.tools.core.objects.options.IOptions;

public class SpriteAssetManager extends DefaultAssetManager<ISpriteAsset, ISpriteAssetKey, IOptions> implements ISpriteAssetManager {

	
	@Inject
	public SpriteAssetManager(ILogging logging, ISpecialAssetManager<ISpriteAsset> specialAssetManager, IAssetStore<ISpriteAsset, ISpriteAssetKey> assetStore, IAssetLoader<ISpriteAsset, ISpriteAssetKey, IOptions> assetLoader) {
		super(logging, specialAssetManager, assetStore, assetLoader);
	}
	
}
