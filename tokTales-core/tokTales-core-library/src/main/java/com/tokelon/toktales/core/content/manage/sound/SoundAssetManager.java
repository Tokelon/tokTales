package com.tokelon.toktales.core.content.manage.sound;

import javax.inject.Inject;

import com.tokelon.toktales.core.content.manage.DefaultAssetManager;
import com.tokelon.toktales.core.content.manage.IAssetLoader;
import com.tokelon.toktales.core.content.manage.IAssetStore;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.util.INamedOptions;

public class SoundAssetManager extends DefaultAssetManager<ISoundAsset, ISoundAssetKey, INamedOptions> implements ISoundAssetManager {


	@Inject
	public SoundAssetManager(ILogger logger, IAssetStore<ISoundAsset, ISoundAssetKey> assetStore, IAssetLoader<ISoundAsset, ISoundAssetKey, INamedOptions> assetLoader) {
		super(logger, assetStore, assetLoader);
	}

}
