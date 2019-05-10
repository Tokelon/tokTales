package com.tokelon.toktales.core.content.manage;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;
import javax.inject.Provider;

import com.tokelon.toktales.core.content.manage.keys.IReadDelegateAssetKey;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.engine.log.ILogger;

/** Default asset loader, using an executor service.
 * 
 * @param <T>
 * @param <K>
 * @param <O>
 */
public class DefaultAssetLoader<T, K, O> extends AbstractExecutorServiceAssetLoader<T, K, O> {

	public static final String TAG = "DefaultAssetLoader";
	
	
	public DefaultAssetLoader(ILogger logger, IAssetReaderManager readerManager, IAssetDecoder<? extends T, K, O> decoder) {
		super(logger, readerManager, decoder);
	}
	
	//@Inject - Avoid injection because of bound generic type in decoder
	public DefaultAssetLoader(ILogger logger, IAssetReaderManager readerManager, IAssetDecoder<? extends T, K, O> decoder, Provider<ExecutorService> executorServiceProvider) {
		super(logger, readerManager, decoder, executorServiceProvider);
	}

	
	@Override
	public T load(K key, O options, IAssetReader reader, IAssetDecoder<? extends T, K, O> decoder) throws ContentException {
		getLogger().d(TAG, String.format("Starting loading for asset key=[%s], options=[%s], reader=[%s], decoder=[%s]", key, options, reader, decoder));
		
		if(reader == null) {
			throw new ContentException(String.format("No reader for key=[%s]", key));
		}
		if(decoder == null) {
			throw new ContentException(String.format("No decoder for key=[%s]", decoder));
		}
		
		Object readableKey;
		if(key instanceof IReadDelegateAssetKey) {
			readableKey = ((IReadDelegateAssetKey) key).getReadableKey();
			getLogger().d(TAG, String.format("Key is delegating to readable key=[%s]", readableKey));
		}
		else {
			readableKey = key;
		}
		
		T result = null;
		try(InputStream inputStream = reader.read(readableKey, options)) {
			result = decoder.decode(inputStream, key, options);
		} catch (IOException e) { // Exception on close()
			// throw exception instead?
			getLogger().w(getTag(), "close() threw exception: " + e);
		}
		
		return result;
	}

	
	@Override
	protected O getDefaultOptions() {
		return null;
	}
	
	@Override
	public String getTag() {
		return TAG;
	}
	
	
	
	public static class DefaultAssetLoaderFactory implements IAssetLoaderFactory {
		
		private final Provider<ILogger> loggerProvider;
		private final Provider<IAssetReaderManager> readerManagerProvider;

		@Inject
		public DefaultAssetLoaderFactory(Provider<ILogger> loggerProvider, Provider<IAssetReaderManager> readerManagerProvider) {
			this.loggerProvider = loggerProvider;
			this.readerManagerProvider = readerManagerProvider;
		}
		
		
		@Override
		public <T, K, O> DefaultAssetLoader<T, K, O> create(IAssetDecoder<? extends T, K, O> decoder) {
			return new DefaultAssetLoader<>(loggerProvider.get(), readerManagerProvider.get(), decoder);
		}
		
		@Override
		public <T, K, O> DefaultAssetLoader<T, K, O> create(IAssetDecoder<? extends T, K, O> decoder, IAssetReaderManager readerManager) {
			return new DefaultAssetLoader<>(loggerProvider.get(), readerManager, decoder);
		}
		
		public <T, K, O> DefaultAssetLoader<T, K, O> create(IAssetDecoder<? extends T, K, O> decoder, Provider<ExecutorService> executorServiceProvider) {
			return new DefaultAssetLoader<>(loggerProvider.get(), readerManagerProvider.get(), decoder, executorServiceProvider);
		}
		
		public <T, K, O> DefaultAssetLoader<T, K, O> create(IAssetDecoder<? extends T, K, O> decoder, IAssetReaderManager readerManager, Provider<ExecutorService> executorServiceProvider) {
			return new DefaultAssetLoader<>(loggerProvider.get(), readerManager, decoder, executorServiceProvider);
		}
	}

}
