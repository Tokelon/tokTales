package com.tokelon.toktales.core.content.manage.files;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;
import javax.inject.Provider;

import com.tokelon.toktales.core.content.manage.AbstractExecutorServiceAssetLoader;
import com.tokelon.toktales.core.content.manage.IAssetDecoder;
import com.tokelon.toktales.core.content.manage.IAssetReader;
import com.tokelon.toktales.core.content.manage.IAssetReaderManager;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.engine.log.ILogger;

public class DefaultFileAssetLoader<T, K extends IFileKey, O> extends AbstractExecutorServiceAssetLoader<T, K, O> implements IFileAssetLoader<T, K, O> {
	// TODO: Delete if obsolete because of reader
	// TODO: Also delete or other loaders if replaceable with default loader
	
	public static final String TAG = "DefaultFileAssetLoader";
	
	
	public DefaultFileAssetLoader(ILogger logger, IAssetReaderManager readerManager) {
		super(logger, readerManager, (k, o, d) -> null);
	}
	
	public DefaultFileAssetLoader(ILogger logger, IAssetReaderManager readerManager, IAssetDecoder<? extends T, K, O> decoder) {
		super(logger, readerManager, decoder);
	}
	
	public DefaultFileAssetLoader(ILogger logger, IAssetReaderManager readerManager, Provider<ExecutorService> executorServiceProvider) {
		super(logger, readerManager, (k, o, d) -> null, executorServiceProvider);
	}
	
	@Inject // TODO: This is not going to work well because of the bound generic type of the decoder
	public DefaultFileAssetLoader(ILogger logger, IAssetReaderManager readerManager, IAssetDecoder<? extends T, K, O> decoder, Provider<ExecutorService> executorServiceProvider) {
		super(logger, readerManager, decoder, executorServiceProvider);
	}

	

	@Override
	public T load(K key, O options, IAssetReader reader, IAssetDecoder<? extends T, K, O> decoder) throws ContentException {
		T result = null;
		try(InputStream inputStream = reader.read(key, options)) {
			result = decoder.decode(inputStream, key, options);
		} catch (IOException e) {
			// Exception on close()
			//throw new ContentException(e);
			getLogger().w(TAG, "close() threw exception: " + e);
		}
		
		return result;
	}
	

	@Override
	public <U, V, W> U loadWithCustomDecoder(V key, W options, IAssetDecoder<U, V, W> decoder, IFileKey fileKey) throws ContentException {
		try(InputStream inputStream = openStream(fileKey)) {
			return decoder.decode(inputStream, key, options);
		} catch (IOException e) {
			throw new ContentException(e);
		}
	}
	
	
	public InputStream openStream(IFileKey fileKey) throws IOException, ContentException {
		Path path = fileKey.getPath();
		if(Files.isReadable(path)) { // TODO: File.isDirectory?
			return Files.newInputStream(path, StandardOpenOption.READ);
		}
		else {
			throw new ContentException("File is not readable: " + path);
		}
	}
	

	
	@Override
	protected O getDefaultOptions() {
		return null;
	}
	
	@Override
	public String getTag() {
		return TAG;
	}
	
	
	
	public static class DefaultFileAssetLoaderFactory implements IFileAssetLoaderFactory {
		private final Provider<ILogger> loggerProvider;
		private final Provider<IAssetReaderManager> readerManagerProvider;

		@Inject
		public DefaultFileAssetLoaderFactory(Provider<ILogger> loggerProvider, Provider<IAssetReaderManager> readerManagerProvider) {
			this.loggerProvider = loggerProvider;
			this.readerManagerProvider = readerManagerProvider;
		}

		
		@Override
		public <T, K extends IFileKey, O> IFileAssetLoader<T, K, O> create() {
			return new DefaultFileAssetLoader<>(loggerProvider.get(), readerManagerProvider.get());
		}

		@Override
		public <T, K extends IFileKey, O> IFileAssetLoader<T, K, O> create(IAssetDecoder<T, K, O> decoder) {
			return new DefaultFileAssetLoader<>(loggerProvider.get(), readerManagerProvider.get(), decoder);
		}
		
		@Override
		public <T, K extends IFileKey, O> IFileAssetLoader<T, K, O> create(Provider<ExecutorService> executorServiceProvider) {
			return new DefaultFileAssetLoader<>(loggerProvider.get(), readerManagerProvider.get(), executorServiceProvider);
		}

		@Override
		public <T, K extends IFileKey, O> IFileAssetLoader<T, K, O> create(IAssetDecoder<T, K, O> decoder, Provider<ExecutorService> executorServiceProvider) {
			return new DefaultFileAssetLoader<>(loggerProvider.get(), readerManagerProvider.get(), decoder, executorServiceProvider);
		}
	}

}
