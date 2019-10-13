package com.tokelon.toktales.tools.assets.loader;

import java.util.concurrent.ExecutorService;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.ILoggerFactory;

import com.tokelon.toktales.tools.assets.annotation.AssetLoader;
import com.tokelon.toktales.tools.assets.reader.IAssetReaderManager;

/** Default injectable asset loader, avoiding a bound type for the decoder.
 *
 * @param <T>
 * @param <K>
 * @param <O>
 */
public class DefaultInjectableAssetLoader<T, K, O> extends DefaultAssetLoader<T, K, O> {
	// Needed to avoid having to declare injection types with the bound generic type in the decoder
	
	public DefaultInjectableAssetLoader(ILoggerFactory loggerFactory, IAssetReaderManager readerManager, IAssetDecoder<T, K, O> decoder) {
		super(loggerFactory, readerManager, decoder);
	}
	
	@Inject
	public DefaultInjectableAssetLoader(ILoggerFactory loggerFactory, IAssetReaderManager readerManager, IAssetDecoder<T, K, O> decoder, @AssetLoader Provider<ExecutorService> executorServiceProvider) {
		super(loggerFactory, readerManager, decoder, executorServiceProvider);
	}

}
