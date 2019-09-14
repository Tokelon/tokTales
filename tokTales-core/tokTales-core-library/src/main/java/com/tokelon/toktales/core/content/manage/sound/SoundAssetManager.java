package com.tokelon.toktales.core.content.manage.sound;

import javax.inject.Inject;

import com.tokelon.toktales.core.content.manage.DefaultAssetManager;
import com.tokelon.toktales.core.content.manage.IAssetLoader;
import com.tokelon.toktales.core.content.manage.IAssetStore;
import com.tokelon.toktales.core.content.manage.ISpecialAssetManager;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.core.util.options.INamedOptions;

public class SoundAssetManager extends DefaultAssetManager<ISoundAsset, ISoundAssetKey, INamedOptions> implements ISoundAssetManager {


	@Inject
	public SoundAssetManager(ILogging logging, ISpecialAssetManager<ISoundAsset> specialAssetManager, IAssetStore<ISoundAsset, ISoundAssetKey> assetStore, IAssetLoader<ISoundAsset, ISoundAssetKey, INamedOptions> assetLoader) {
		super(logging, specialAssetManager, assetStore, assetLoader);
	}

}
