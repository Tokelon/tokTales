package com.tokelon.toktales.core.content.manage;

import java.util.concurrent.ExecutorService;

import javax.inject.Inject;
import javax.inject.Provider;

import com.tokelon.toktales.core.engine.inject.annotation.AssetLoader;
import com.tokelon.toktales.core.engine.log.ILogging;

/** Default injectable asset loader, avoiding a bound type for the decoder.
 *
 * @param <T>
 * @param <K>
 * @param <O>
 */
public class DefaultInjectableAssetLoader<T, K, O> extends DefaultAssetLoader<T, K, O> {
	// Needed to avoid having to declare injection types with the bound generic type in the decoder
	
	public DefaultInjectableAssetLoader(ILogging logging, IAssetReaderManager readerManager, IAssetDecoder<T, K, O> decoder) {
		super(logging, readerManager, decoder);
	}
	
	@Inject
	public DefaultInjectableAssetLoader(ILogging logging, IAssetReaderManager readerManager, IAssetDecoder<T, K, O> decoder, @AssetLoader Provider<ExecutorService> executorServiceProvider) {
		super(logging, readerManager, decoder, executorServiceProvider);
	}

}
