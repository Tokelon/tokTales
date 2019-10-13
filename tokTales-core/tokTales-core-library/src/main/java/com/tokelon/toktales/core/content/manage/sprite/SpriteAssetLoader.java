package com.tokelon.toktales.core.content.manage.sprite;

import java.util.concurrent.ExecutorService;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.ILoggerFactory;

import com.tokelon.toktales.core.content.sprite.ISpriteAsset;
import com.tokelon.toktales.tools.assets.annotation.AssetLoader;
import com.tokelon.toktales.tools.assets.loader.DefaultAssetLoader;
import com.tokelon.toktales.tools.assets.loader.IAssetDecoder;
import com.tokelon.toktales.tools.assets.reader.IAssetReaderManager;
import com.tokelon.toktales.tools.core.objects.options.IOptions;

public class SpriteAssetLoader extends DefaultAssetLoader<ISpriteAsset, ISpriteAssetKey, IOptions> implements ISpriteAssetLoader {


	public SpriteAssetLoader(ILoggerFactory loggerFactory, IAssetReaderManager readerManager, IAssetDecoder<ISpriteAsset, ISpriteAssetKey, IOptions> decoder) {
		super(loggerFactory, readerManager, decoder);
	}

	@Inject
	public SpriteAssetLoader(ILoggerFactory loggerFactory, IAssetReaderManager readerManager, IAssetDecoder<ISpriteAsset, ISpriteAssetKey, IOptions> decoder, @AssetLoader Provider<ExecutorService> executorServiceProvider) {
		super(loggerFactory, readerManager, decoder, executorServiceProvider);
	}
	
	
	@Override
	protected IOptions getDefaultOptions() {
		return super.getDefaultOptions();
	}
	
}
