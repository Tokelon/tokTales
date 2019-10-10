package com.tokelon.toktales.core.content.manage.sprite;

import java.util.concurrent.ExecutorService;

import javax.inject.Inject;
import javax.inject.Provider;

import com.tokelon.toktales.core.content.manage.DefaultAssetLoader;
import com.tokelon.toktales.core.content.manage.IAssetDecoder;
import com.tokelon.toktales.core.content.manage.IAssetReaderManager;
import com.tokelon.toktales.core.content.sprite.ISpriteAsset;
import com.tokelon.toktales.core.engine.inject.annotation.AssetLoader;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.tools.core.objects.options.IOptions;

public class SpriteAssetLoader extends DefaultAssetLoader<ISpriteAsset, ISpriteAssetKey, IOptions> implements ISpriteAssetLoader {


	public SpriteAssetLoader(ILogging logging, IAssetReaderManager readerManager, IAssetDecoder<ISpriteAsset, ISpriteAssetKey, IOptions> decoder) {
		super(logging, readerManager, decoder);
	}

	@Inject
	public SpriteAssetLoader(ILogging logging, IAssetReaderManager readerManager, IAssetDecoder<ISpriteAsset, ISpriteAssetKey, IOptions> decoder, @AssetLoader Provider<ExecutorService> executorServiceProvider) {
		super(logging, readerManager, decoder, executorServiceProvider);
	}
	
	
	@Override
	protected IOptions getDefaultOptions() {
		return super.getDefaultOptions();
	}
	
}
