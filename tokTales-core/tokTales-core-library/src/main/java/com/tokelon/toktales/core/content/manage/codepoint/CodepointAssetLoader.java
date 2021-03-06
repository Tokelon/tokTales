package com.tokelon.toktales.core.content.manage.codepoint;

import java.util.concurrent.ExecutorService;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.ILoggerFactory;

import com.tokelon.toktales.tools.assets.annotation.AssetLoader;
import com.tokelon.toktales.tools.assets.exception.AssetException;
import com.tokelon.toktales.tools.assets.loader.AbstractExecutorServiceAssetLoader;
import com.tokelon.toktales.tools.assets.loader.IAssetDecoder;
import com.tokelon.toktales.tools.assets.reader.IAssetReader;
import com.tokelon.toktales.tools.assets.reader.IAssetReaderManager;
import com.tokelon.toktales.tools.core.objects.options.INamedOptions;
import com.tokelon.toktales.tools.core.objects.options.NamedOptionsImpl;

public class CodepointAssetLoader extends AbstractExecutorServiceAssetLoader<ICodepointAsset, ICodepointAssetKey, INamedOptions> {


	public static final INamedOptions EMPTY_OPTIONS = new NamedOptionsImpl();
	
	
	public CodepointAssetLoader(ILoggerFactory loggerFactory, IAssetReaderManager readerManager, IAssetDecoder<ICodepointAsset, ICodepointAssetKey, INamedOptions> decoder) {
		super(loggerFactory, readerManager, decoder);
	}
	
	@Inject
	public CodepointAssetLoader(ILoggerFactory loggerFactory, IAssetReaderManager readerManager, IAssetDecoder<ICodepointAsset, ICodepointAssetKey, INamedOptions> decoder, @AssetLoader Provider<ExecutorService> executorServiceProvider) {
		super(loggerFactory, readerManager, decoder, executorServiceProvider);
	}

	
	@Override
	protected INamedOptions getDefaultOptions() {
		return EMPTY_OPTIONS;
	}
	

	
	@Override
	protected IAssetReader findReader(ICodepointAssetKey key, INamedOptions options) {
		// Reader is not used at the moment so no need to find one
		return null;
	}
	
	@Override
	public ICodepointAsset load(ICodepointAssetKey key, INamedOptions options, IAssetReader reader, IAssetDecoder<? extends ICodepointAsset, ICodepointAssetKey, INamedOptions> decoder) throws AssetException {
		return decoder.decode(null, key, options);
	}
	
}