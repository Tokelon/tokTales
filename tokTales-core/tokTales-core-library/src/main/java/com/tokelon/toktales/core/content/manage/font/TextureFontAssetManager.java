package com.tokelon.toktales.core.content.manage.font;

import javax.inject.Inject;

import com.tokelon.toktales.core.content.manage.DefaultAssetManager;
import com.tokelon.toktales.core.content.manage.IAssetLoader;
import com.tokelon.toktales.core.content.manage.IAssetStore;
import com.tokelon.toktales.core.content.manage.ISpecialAssetManager;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.util.options.IOptions;

public class TextureFontAssetManager extends DefaultAssetManager<ITextureFontAsset, ITextureFontAssetKey, IOptions> implements ITextureFontAssetManager {

	
	@Inject
	public TextureFontAssetManager(ILogger logger, ISpecialAssetManager<ITextureFontAsset> specialAssetManager, IAssetStore<ITextureFontAsset, ITextureFontAssetKey> assetStore, IAssetLoader<ITextureFontAsset, ITextureFontAssetKey, IOptions> assetLoader) {
		super(logger, specialAssetManager, assetStore, assetLoader);
	}

}
