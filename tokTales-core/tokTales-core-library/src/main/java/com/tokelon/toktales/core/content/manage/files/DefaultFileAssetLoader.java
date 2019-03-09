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
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.engine.log.ILogger;

public class DefaultFileAssetLoader<T, K extends IFileKey, O> extends AbstractExecutorServiceAssetLoader<T, K, O> implements IFileAssetLoader<T, K, O> {

	public static final String TAG = "DefaultFileAssetLoader";
	
	
	public DefaultFileAssetLoader(ILogger logger) {
		super(logger, (k, o, d) -> null);
	}
	
	public DefaultFileAssetLoader(ILogger logger, IAssetDecoder<? extends T, K, O> decoder) {
		super(logger, decoder);
	}
	
	public DefaultFileAssetLoader(ILogger logger, Provider<ExecutorService> executorServiceProvider) {
		super(logger, (k, o, d) -> null, executorServiceProvider);
	}
	
	@Inject
	public DefaultFileAssetLoader(ILogger logger, IAssetDecoder<? extends T, K, O> decoder, Provider<ExecutorService> executorServiceProvider) {
		super(logger, decoder, executorServiceProvider);
	}

	

	@Override
	public T load(K key, O options, IAssetDecoder<? extends T, K, O> decoder) throws ContentException {
		try(InputStream inputStream = openStream(key)) {
			return decoder.decode(inputStream, key, options);
		} catch (IOException e) {
			throw new ContentException(e);
		}
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

		@Inject
		public DefaultFileAssetLoaderFactory(Provider<ILogger> loggerProvider) {
			this.loggerProvider = loggerProvider;
		}

		
		@Override
		public <T, K extends IFileKey, O> IFileAssetLoader<T, K, O> create() {
			return new DefaultFileAssetLoader<>(loggerProvider.get());
		}

		@Override
		public <T, K extends IFileKey, O> IFileAssetLoader<T, K, O> create(IAssetDecoder<T, K, O> decoder) {
			return new DefaultFileAssetLoader<>(loggerProvider.get(), decoder);
		}
		
		@Override
		public <T, K extends IFileKey, O> IFileAssetLoader<T, K, O> create(Provider<ExecutorService> executorServiceProvider) {
			return new DefaultFileAssetLoader<>(loggerProvider.get(), executorServiceProvider);
		}

		@Override
		public <T, K extends IFileKey, O> IFileAssetLoader<T, K, O> create(IAssetDecoder<T, K, O> decoder, Provider<ExecutorService> executorServiceProvider) {
			return new DefaultFileAssetLoader<>(loggerProvider.get(), decoder, executorServiceProvider);
		}
	}

}
