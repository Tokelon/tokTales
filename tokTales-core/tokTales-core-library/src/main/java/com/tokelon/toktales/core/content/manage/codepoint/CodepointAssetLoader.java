package com.tokelon.toktales.core.content.manage.codepoint;

import java.util.concurrent.ExecutorService;

import javax.inject.Inject;
import javax.inject.Provider;

import com.tokelon.toktales.core.content.manage.AbstractExecutorServiceAssetLoader;
import com.tokelon.toktales.core.content.manage.IAssetDecoder;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.util.INamedOptions;
import com.tokelon.toktales.core.util.NamedOptionsImpl;

public class CodepointAssetLoader extends AbstractExecutorServiceAssetLoader<ICodepointAsset, ICodepointAssetKey, INamedOptions> {
	
	public static final String TAG = "CodepointLoader";
	
	public static final INamedOptions EMPTY_OPTIONS = new NamedOptionsImpl();
	
	
	public CodepointAssetLoader(ILogger logger, IAssetDecoder<ICodepointAsset, ICodepointAssetKey, INamedOptions> decoder) {
		super(logger, decoder);
	}
	
	@Inject
	public CodepointAssetLoader(ILogger logger, IAssetDecoder<ICodepointAsset, ICodepointAssetKey, INamedOptions> decoder, Provider<ExecutorService> executorServiceProvider) {
		super(logger, decoder, executorServiceProvider);
	}

	
	@Override
	protected INamedOptions getDefaultOptions() {
		return EMPTY_OPTIONS;
	}
	
	@Override
	public String getTag() {
		return super.getTag() + "_" + TAG;
	}

	
	@Override
	public ICodepointAsset load(ICodepointAssetKey key, INamedOptions options, IAssetDecoder<? extends ICodepointAsset, ICodepointAssetKey, INamedOptions> decoder) throws ContentException {
		return decoder.decode(null, key, options);
	}
	
}