package com.tokelon.toktales.core.content.manage.bitmap;

import javax.inject.Inject;

import org.slf4j.ILoggerFactory;

import com.tokelon.toktales.tools.assets.loader.IAssetLoader;
import com.tokelon.toktales.tools.assets.manager.DefaultAssetManager;
import com.tokelon.toktales.tools.assets.manager.IAssetStore;
import com.tokelon.toktales.tools.assets.manager.ISpecialAssetManager;
import com.tokelon.toktales.tools.core.objects.options.IOptions;

public class BitmapAssetManager extends DefaultAssetManager<IBitmapAsset, IBitmapAssetKey, IOptions> implements IBitmapAssetManager {

	
	@Inject
	public BitmapAssetManager(ILoggerFactory loggerFactory, ISpecialAssetManager<IBitmapAsset> specialAssetManager, IAssetStore<IBitmapAsset, IBitmapAssetKey> assetStore, IAssetLoader<IBitmapAsset, IBitmapAssetKey, IOptions> assetLoader) {
		super(loggerFactory, specialAssetManager, assetStore, assetLoader);
	}

}
