package com.tokelon.toktales.core.content.manage.sound;

import javax.inject.Inject;

import org.slf4j.ILoggerFactory;

import com.tokelon.toktales.tools.assets.loader.IAssetLoader;
import com.tokelon.toktales.tools.assets.manager.DefaultAssetManager;
import com.tokelon.toktales.tools.assets.manager.IAssetStore;
import com.tokelon.toktales.tools.assets.manager.ISpecialAssetManager;
import com.tokelon.toktales.tools.core.objects.options.INamedOptions;

public class SoundAssetManager extends DefaultAssetManager<ISoundAsset, ISoundAssetKey, INamedOptions> implements ISoundAssetManager {


	@Inject
	public SoundAssetManager(ILoggerFactory loggerFactory, ISpecialAssetManager<ISoundAsset> specialAssetManager, IAssetStore<ISoundAsset, ISoundAssetKey> assetStore, IAssetLoader<ISoundAsset, ISoundAssetKey, INamedOptions> assetLoader) {
		super(loggerFactory, specialAssetManager, assetStore, assetLoader);
	}

}
