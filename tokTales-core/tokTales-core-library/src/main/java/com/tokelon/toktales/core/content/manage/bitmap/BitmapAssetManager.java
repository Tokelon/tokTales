package com.tokelon.toktales.core.content.manage.bitmap;

import javax.inject.Inject;

import org.slf4j.ILoggerFactory;

import com.tokelon.toktales.core.content.manage.DefaultAssetManager;
import com.tokelon.toktales.core.content.manage.IAssetLoader;
import com.tokelon.toktales.core.content.manage.IAssetStore;
import com.tokelon.toktales.core.content.manage.ISpecialAssetManager;
import com.tokelon.toktales.tools.core.objects.options.IOptions;

public class BitmapAssetManager extends DefaultAssetManager<IBitmapAsset, IBitmapAssetKey, IOptions> implements IBitmapAssetManager {

	
	@Inject
	public BitmapAssetManager(ILoggerFactory loggerFactory, ISpecialAssetManager<IBitmapAsset> specialAssetManager, IAssetStore<IBitmapAsset, IBitmapAssetKey> assetStore, IAssetLoader<IBitmapAsset, IBitmapAssetKey, IOptions> assetLoader) {
		super(loggerFactory, specialAssetManager, assetStore, assetLoader);
	}

}
