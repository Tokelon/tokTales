package com.tokelon.toktales.core.content.manage.font;

import javax.inject.Inject;

import org.slf4j.ILoggerFactory;

import com.tokelon.toktales.tools.assets.loader.IAssetLoader;
import com.tokelon.toktales.tools.assets.manager.DefaultAssetManager;
import com.tokelon.toktales.tools.assets.manager.IAssetStore;
import com.tokelon.toktales.tools.assets.manager.ISpecialAssetManager;
import com.tokelon.toktales.tools.core.objects.options.IOptions;

public class TextureFontAssetManager extends DefaultAssetManager<ITextureFontAsset, ITextureFontAssetKey, IOptions> implements ITextureFontAssetManager {

	
	@Inject
	public TextureFontAssetManager(ILoggerFactory loggerFactory, ISpecialAssetManager<ITextureFontAsset> specialAssetManager, IAssetStore<ITextureFontAsset, ITextureFontAssetKey> assetStore, IAssetLoader<ITextureFontAsset, ITextureFontAssetKey, IOptions> assetLoader) {
		super(loggerFactory, specialAssetManager, assetStore, assetLoader);
	}

}
