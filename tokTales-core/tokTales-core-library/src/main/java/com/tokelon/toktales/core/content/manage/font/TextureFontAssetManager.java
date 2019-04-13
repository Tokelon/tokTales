package com.tokelon.toktales.core.content.manage.font;

import com.tokelon.toktales.core.content.manage.DefaultAssetManager;
import com.tokelon.toktales.core.content.manage.IAssetLoader;
import com.tokelon.toktales.core.content.manage.IAssetStore;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.util.options.IOptions;

public class TextureFontAssetManager extends DefaultAssetManager<ITextureFontAsset, ITextureFontAssetKey, IOptions> implements ITextureFontAssetManager {

	
	public TextureFontAssetManager(ILogger logger, IAssetStore<ITextureFontAsset, ITextureFontAssetKey> assetStore, IAssetLoader<ITextureFontAsset, ITextureFontAssetKey, IOptions> assetLoader) {
		super(logger, assetStore, assetLoader);
	}

}
