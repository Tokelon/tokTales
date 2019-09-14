package com.tokelon.toktales.core.content.manage;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;
import javax.inject.Provider;

import com.tokelon.toktales.core.content.manage.keys.IReadDelegateAssetKey;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.engine.log.ILogging;

/** Default asset loader, using an executor service.
 * 
 * @param <T>
 * @param <K>
 * @param <O>
 */
public class DefaultAssetLoader<T, K, O> extends AbstractExecutorServiceAssetLoader<T, K, O> {


	public DefaultAssetLoader(ILogging logging, IAssetReaderManager readerManager, IAssetDecoder<? extends T, K, O> decoder) {
		super(logging, readerManager, decoder);
	}
	
	//@Inject - Avoid injection because of bound generic type in decoder
	public DefaultAssetLoader(ILogging logging, IAssetReaderManager readerManager, IAssetDecoder<? extends T, K, O> decoder, Provider<ExecutorService> executorServiceProvider) {
		super(logging, readerManager, decoder, executorServiceProvider);
	}

	
	@Override
	public T load(K key, O options, IAssetReader reader, IAssetDecoder<? extends T, K, O> decoder) throws ContentException {
		getLogger().debug("Starting loading for asset key=[{}], options=[{}], reader=[{}], decoder=[{}]", key, options, reader, decoder);
		
		if(reader == null) {
			throw new ContentException(String.format("No reader for key=[%s]", key));
		}
		if(decoder == null) {
			throw new ContentException(String.format("No decoder for key=[%s]", decoder));
		}
		
		Object readableKey;
		if(key instanceof IReadDelegateAssetKey) {
			readableKey = ((IReadDelegateAssetKey) key).getReadableKey();
			getLogger().debug("Key is delegating to readable key=[{}]", readableKey);
		}
		else {
			readableKey = key;
		}
		
		T result = null;
		try(InputStream inputStream = reader.read(readableKey, options)) {
			result = decoder.decode(inputStream, key, options);
		} catch (IOException e) { // Exception on close()
			// throw exception instead?
			getLogger().warn("close() threw exception", e);
		}
		
		return result;
	}

	
	@Override
	protected O getDefaultOptions() {
		return null;
	}
	
	
	
	public static class DefaultAssetLoaderFactory implements IAssetLoaderFactory {
		
		private final Provider<ILogging> loggingProvider;
		private final Provider<IAssetReaderManager> readerManagerProvider;

		@Inject
		public DefaultAssetLoaderFactory(Provider<ILogging> loggingProvider, Provider<IAssetReaderManager> readerManagerProvider) {
			this.loggingProvider = loggingProvider;
			this.readerManagerProvider = readerManagerProvider;
		}
		
		
		@Override
		public <T, K, O> DefaultAssetLoader<T, K, O> create(IAssetDecoder<? extends T, K, O> decoder) {
			return new DefaultAssetLoader<>(loggingProvider.get(), readerManagerProvider.get(), decoder);
		}
		
		@Override
		public <T, K, O> DefaultAssetLoader<T, K, O> create(IAssetDecoder<? extends T, K, O> decoder, IAssetReaderManager readerManager) {
			return new DefaultAssetLoader<>(loggingProvider.get(), readerManager, decoder);
		}
		
		public <T, K, O> DefaultAssetLoader<T, K, O> create(IAssetDecoder<? extends T, K, O> decoder, Provider<ExecutorService> executorServiceProvider) {
			return new DefaultAssetLoader<>(loggingProvider.get(), readerManagerProvider.get(), decoder, executorServiceProvider);
		}
		
		public <T, K, O> DefaultAssetLoader<T, K, O> create(IAssetDecoder<? extends T, K, O> decoder, IAssetReaderManager readerManager, Provider<ExecutorService> executorServiceProvider) {
			return new DefaultAssetLoader<>(loggingProvider.get(), readerManager, decoder, executorServiceProvider);
		}
	}

}
