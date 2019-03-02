package com.tokelon.toktales.core.content.manage.sound;

import java.util.concurrent.ExecutorService;

import javax.inject.Inject;
import javax.inject.Provider;

import com.tokelon.toktales.core.content.manage.AbstractExecutorServiceAssetLoader;
import com.tokelon.toktales.core.content.manage.IAssetDecoder;
import com.tokelon.toktales.core.content.manage.IAssetLoader;
import com.tokelon.toktales.core.content.manage.files.DefaultFileAssetLoader;
import com.tokelon.toktales.core.content.manage.files.IFileKey;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.util.INamedOptions;

public class SoundFileLoader extends AbstractExecutorServiceAssetLoader<ISoundAsset, ISoundAssetKey, INamedOptions> implements IAssetLoader<ISoundAsset, ISoundAssetKey, INamedOptions> {

	
	private final DefaultFileAssetLoader<ISoundAsset, IFileKey, INamedOptions> fileAssetLoader;

	@Inject
	public SoundFileLoader(ILogger logger, IAssetDecoder<ISoundAsset, ISoundAssetKey, INamedOptions> decoder) {
		super(logger, decoder);
		this.fileAssetLoader = new DefaultFileAssetLoader<ISoundAsset, IFileKey, INamedOptions>(logger, null);
	}

	public SoundFileLoader(ILogger logger, IAssetDecoder<ISoundAsset, ISoundAssetKey, INamedOptions> decoder, Provider<ExecutorService> executorServiceProvider) {
		super(logger, decoder, executorServiceProvider);
		this.fileAssetLoader = new DefaultFileAssetLoader<ISoundAsset, IFileKey, INamedOptions>(logger, null);;
	}
	
	
	@Override
	public ISoundAsset load(ISoundAssetKey key, INamedOptions options, IAssetDecoder<? extends ISoundAsset, ISoundAssetKey, INamedOptions> decoder) throws ContentException {
		if(!(key instanceof IFileKey)) {
			throw new ContentException("Invalid key type: " + key);
		}
		IFileKey fileKey = (IFileKey) key;
		
		return fileAssetLoader.loadWithCustomDecoder(key, options, decoder, fileKey);
	}

	
	@Override
	protected INamedOptions getDefaultOptions() {
		return null;
	}
	
}
