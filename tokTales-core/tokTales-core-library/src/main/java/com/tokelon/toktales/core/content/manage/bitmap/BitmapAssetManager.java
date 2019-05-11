package com.tokelon.toktales.core.content.manage.bitmap;

import javax.inject.Inject;

import com.tokelon.toktales.core.content.manage.DefaultAssetManager;
import com.tokelon.toktales.core.content.manage.IAssetLoader;
import com.tokelon.toktales.core.content.manage.IAssetStore;
import com.tokelon.toktales.core.content.manage.ISpecialAssetManager;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.util.options.IOptions;

public class BitmapAssetManager extends DefaultAssetManager<IBitmapAsset, IBitmapAssetKey, IOptions> implements IBitmapAssetManager {

	
	@Inject
	public BitmapAssetManager(ILogger logger, ISpecialAssetManager<IBitmapAsset> specialAssetManager, IAssetStore<IBitmapAsset, IBitmapAssetKey> assetStore, IAssetLoader<IBitmapAsset, IBitmapAssetKey, IOptions> assetLoader) {
		super(logger, specialAssetManager, assetStore, assetLoader);
	}

}
