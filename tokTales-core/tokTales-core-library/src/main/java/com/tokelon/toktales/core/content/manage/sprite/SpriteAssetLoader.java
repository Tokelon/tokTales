package com.tokelon.toktales.core.content.manage.sprite;

import java.util.concurrent.ExecutorService;

import javax.inject.Inject;
import javax.inject.Provider;

import com.tokelon.toktales.core.content.manage.DefaultAssetLoader;
import com.tokelon.toktales.core.content.manage.IAssetDecoder;
import com.tokelon.toktales.core.content.manage.IAssetReaderManager;
import com.tokelon.toktales.core.content.sprite.ISpriteAsset;
import com.tokelon.toktales.core.engine.inject.annotation.AssetLoader;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.util.options.IOptions;

public class SpriteAssetLoader extends DefaultAssetLoader<ISpriteAsset, ISpriteAssetKey, IOptions> implements ISpriteAssetLoader {
	
	public static final String TAG = "SpriteAssetLoader";
	
	
	public SpriteAssetLoader(ILogger logger, IAssetReaderManager readerManager, IAssetDecoder<ISpriteAsset, ISpriteAssetKey, IOptions> decoder) {
		super(logger, readerManager, decoder);
	}

	@Inject
	public SpriteAssetLoader(ILogger logger, IAssetReaderManager readerManager, IAssetDecoder<ISpriteAsset, ISpriteAssetKey, IOptions> decoder, @AssetLoader Provider<ExecutorService> executorServiceProvider) {
		super(logger, readerManager, decoder, executorServiceProvider);
	}
	
	
	@Override
	protected IOptions getDefaultOptions() {
		return super.getDefaultOptions();
	}
	
	@Override
	public String getTag() {
		return TAG;
	}
	
}
