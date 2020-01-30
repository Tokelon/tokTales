package com.tokelon.toktales.core.content.manage.font;

import javax.inject.Inject;

import org.slf4j.ILoggerFactory;

import com.tokelon.toktales.tools.assets.key.IReadableAssetKey;
import com.tokelon.toktales.tools.assets.loader.IAssetLoader;
import com.tokelon.toktales.tools.assets.manager.DefaultAssetManager;
import com.tokelon.toktales.tools.assets.manager.IAssetStore;
import com.tokelon.toktales.tools.assets.manager.ISpecialAssetManager;
import com.tokelon.toktales.tools.core.objects.options.IOptions;

public class FontAssetManager extends DefaultAssetManager<IFontAsset, IFontAssetKey, IOptions> implements IFontAssetManager {


	@Inject
	public FontAssetManager(
			ILoggerFactory loggerFactory,
			ISpecialAssetManager<IFontAsset> specialAssetManager,
			IAssetStore<IFontAsset, IFontAssetKey> assetStore,
			IAssetLoader<IFontAsset, IFontAssetKey, IOptions> assetLoader
	) {
		super(loggerFactory, specialAssetManager, assetStore, assetLoader);
	}

	
	@Override
	public IFontAssetKey keyOf(IReadableAssetKey readableKey) {
		return new ReadDelegateFontAssetKey(readableKey);
	}
	
}
