package com.tokelon.toktales.core.content.manage;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.ILoggerFactory;

import com.tokelon.toktales.core.content.manage.keys.IReadDelegateAssetKey;
import com.tokelon.toktales.core.engine.content.AssetException;

/** Default asset loader, using an executor service.
 * 
 * @param <T>
 * @param <K>
 * @param <O>
 */
public class DefaultAssetLoader<T, K, O> extends AbstractExecutorServiceAssetLoader<T, K, O> {


	public DefaultAssetLoader(ILoggerFactory loggerFactory, IAssetReaderManager readerManager, IAssetDecoder<? extends T, K, O> decoder) {
		super(loggerFactory, readerManager, decoder);
	}
	
	//@Inject - Avoid injection because of bound generic type in decoder
	public DefaultAssetLoader(ILoggerFactory loggerFactory, IAssetReaderManager readerManager, IAssetDecoder<? extends T, K, O> decoder, Provider<ExecutorService> executorServiceProvider) {
		super(loggerFactory, readerManager, decoder, executorServiceProvider);
	}

	
	@Override
	public T load(K key, O options, IAssetReader reader, IAssetDecoder<? extends T, K, O> decoder) throws AssetException {
		getLogger().debug("Starting loading for asset key=[{}], options=[{}], reader=[{}], decoder=[{}]", key, options, reader, decoder);
		
		if(reader == null) {
			throw new AssetException(String.format("No reader for key=[%s]", key));
		}
		if(decoder == null) {
			throw new AssetException(String.format("No decoder for key=[%s]", decoder));
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
		
		private final Provider<ILoggerFactory> loggerFactoryProvider;
		private final Provider<IAssetReaderManager> readerManagerProvider;

		@Inject
		public DefaultAssetLoaderFactory(Provider<ILoggerFactory> loggerFactoryProvider, Provider<IAssetReaderManager> readerManagerProvider) {
			this.loggerFactoryProvider = loggerFactoryProvider;
			this.readerManagerProvider = readerManagerProvider;
		}
		
		
		@Override
		public <T, K, O> DefaultAssetLoader<T, K, O> create(IAssetDecoder<? extends T, K, O> decoder) {
			return new DefaultAssetLoader<>(loggerFactoryProvider.get(), readerManagerProvider.get(), decoder);
		}
		
		@Override
		public <T, K, O> DefaultAssetLoader<T, K, O> create(IAssetDecoder<? extends T, K, O> decoder, IAssetReaderManager readerManager) {
			return new DefaultAssetLoader<>(loggerFactoryProvider.get(), readerManager, decoder);
		}
		
		public <T, K, O> DefaultAssetLoader<T, K, O> create(IAssetDecoder<? extends T, K, O> decoder, Provider<ExecutorService> executorServiceProvider) {
			return new DefaultAssetLoader<>(loggerFactoryProvider.get(), readerManagerProvider.get(), decoder, executorServiceProvider);
		}
		
		public <T, K, O> DefaultAssetLoader<T, K, O> create(IAssetDecoder<? extends T, K, O> decoder, IAssetReaderManager readerManager, Provider<ExecutorService> executorServiceProvider) {
			return new DefaultAssetLoader<>(loggerFactoryProvider.get(), readerManager, decoder, executorServiceProvider);
		}
	}

}
