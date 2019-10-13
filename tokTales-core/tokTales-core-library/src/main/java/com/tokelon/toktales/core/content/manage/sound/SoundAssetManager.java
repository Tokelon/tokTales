package com.tokelon.toktales.core.content.manage.sound;

import javax.inject.Inject;

import org.slf4j.ILoggerFactory;

import com.tokelon.toktales.core.content.manage.DefaultAssetManager;
import com.tokelon.toktales.core.content.manage.IAssetLoader;
import com.tokelon.toktales.core.content.manage.IAssetStore;
import com.tokelon.toktales.core.content.manage.ISpecialAssetManager;
import com.tokelon.toktales.tools.core.objects.options.INamedOptions;

public class SoundAssetManager extends DefaultAssetManager<ISoundAsset, ISoundAssetKey, INamedOptions> implements ISoundAssetManager {


	@Inject
	public SoundAssetManager(ILoggerFactory loggerFactory, ISpecialAssetManager<ISoundAsset> specialAssetManager, IAssetStore<ISoundAsset, ISoundAssetKey> assetStore, IAssetLoader<ISoundAsset, ISoundAssetKey, INamedOptions> assetLoader) {
		super(loggerFactory, specialAssetManager, assetStore, assetLoader);
	}

}
