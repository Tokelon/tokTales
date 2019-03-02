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

public class CodepointLoader extends AbstractExecutorServiceAssetLoader<ICodepointAsset, ICodepointKey, INamedOptions> {
	
	public static final String TAG = "CodepointLoader";
	
	public static final INamedOptions EMPTY_OPTIONS = new NamedOptionsImpl();
	
	
	@Inject
	public CodepointLoader(ILogger logger, IAssetDecoder<ICodepointAsset, ICodepointKey, INamedOptions> decoder) {
		super(logger, decoder);
	}
	
	public CodepointLoader(ILogger logger, IAssetDecoder<ICodepointAsset, ICodepointKey, INamedOptions> decoder, Provider<ExecutorService> executorServiceProvider) {
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
	public ICodepointAsset load(ICodepointKey key, INamedOptions options, IAssetDecoder<? extends ICodepointAsset, ICodepointKey, INamedOptions> decoder) throws ContentException {
		return decoder.decode(null, key, options);
	}
	
}