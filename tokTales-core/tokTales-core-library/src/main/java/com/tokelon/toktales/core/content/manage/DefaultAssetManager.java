package com.tokelon.toktales.core.content.manage;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.engine.content.ContentNotFoundException;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;

import java9.util.concurrent.CompletableFuture;

public class DefaultAssetManager<T, K, O> implements IAssetManager<T, K, O> {


	private final ILogger logger;
	private final ISpecialAssetManager<T> specialAssetManager;
	private final IAssetStore<T, K> assetStore;
	private final IAssetLoader<T, K, O> assetLoader;

	@Inject
	public DefaultAssetManager(ILogging logging, ISpecialAssetManager<T> specialAssetManager, IAssetStore<T, K> assetStore, IAssetLoader<T, K, O> assetLoader) {
		this.logger = logging.getLogger(getClass());
		this.specialAssetManager = specialAssetManager;
		this.assetStore = assetStore;
		this.assetLoader = assetLoader;
	}
	
	
	protected ILogger getLogger() {
		return logger;
	}
	
	@Override
	public ISpecialAssetManager<T> getSpecialAssetManager() {
		return specialAssetManager;
	}
	
	@Override
	public IAssetStore<T, K> getStore() {
		return assetStore;
	}
	
	@Override
	public IAssetLoader<T, K, O> getLoader() {
		return assetLoader;
	}

	
	private T handleAssetResult(K key, T asset) {
		T assetResult = asset;
		if(asset == null) {
			assetResult = getSpecialAssetManager().getSpecialAssetNull();
		}
		
		getStore().insert(key, assetResult);
		return assetResult;
	}
	
	private CompletableFuture<T> handleAssetFuture(K key, CompletableFuture<T> future) {
		return future
		.exceptionally((exception) -> {
			getLogger().error("Asset future completed exceptionally for [key={}]:", key, exception); //Throwables.getStackTraceAsString(exception)
			
			T assetResult = getSpecialAssetManager().getSpecialAssetLoadError();
			getStore().insert(key, assetResult);
			
			return assetResult;
		})
		.thenApply((result) -> {
			return handleAssetResult(key, result);
		});
	}
	
	@Override
	public T getAsset(K key) {
		T asset = getStore().retrieve(key);
		if(asset != null) {
			return asset;
		}
		
		handleAssetFuture(key, getLoader().enqueue(key));
		return null;
	}

	@Override
	public T getAsset(K key, O options) {
		T asset = getStore().retrieve(key);
		if(asset != null) {
			return asset;
		}
		
		handleAssetFuture(key, getLoader().enqueue(key, options));
		return null;
	}

	@Override
	public T getAssetIfKeyValid(K key, String tag) {
		if(key == null) {
			getLogger().warnOnceForId(tag, "Asset key was null for tag: {}", tag);
			return null;
		}
		
		return getAsset(key);
	}
	
	@Override
	public T getAssetIfKeyValid(K key, O options, String tag) {
		if(key == null) {
			getLogger().warnOnceForId(tag, "(With Options) Asset key was null for tag: {}", tag);
			return null;
		}
		
		return getAsset(key, options);
	}
	
	@Override
	public T getAssetOrError(K key) throws ContentNotFoundException {
		T asset = getStore().retrieve(key);
		if(asset != null) {
			return asset;
		}
		
		// TODO: Really use ContentNotFoundException for this?
		throw new ContentNotFoundException("Asset for key {%s} was not loaded");
	}


	@Override
	public T getAssetLoadIfNeeded(K key) {
		T asset = getStore().retrieve(key);
		if(asset != null) {
			return asset;
		}
		
		T result = null;
		try {
			result = handleAssetResult(key, getLoader().load(key));
		} catch (ContentException e) {
			getLogger().error("Asset loading failed for [key={}]:", key, e);
		}
		
		return result;
	}

	@Override
	public T getAssetLoadIfNeeded(K key, O options) {
		T asset = getStore().retrieve(key);
		if(asset != null) {
			return asset;
		}
		
		T result = null;
		try {
			result = handleAssetResult(key, getLoader().load(key, options));
		} catch (ContentException e) {
			getLogger().error("Asset loading failed for [key={}, options={}]:", key, options, e);
		}
		
		return result;
	}
	
	@Override
	public T getAssetLoadIfNeededIfKeyValid(K key, String tag) {
		if(key == null) {
			getLogger().warnOnceForId(tag, "(Load if Needed) Asset key was null for tag: {}", tag);
			return null;
		}
		
		return getAssetLoadIfNeeded(key);
	}
	
	@Override
	public T getAssetLoadIfNeededIfKeyValid(K key, O options, String tag) {
		if(key == null) {
			getLogger().warnOnceForId(tag, "(Load if Needed) (With Options) Asset key was null for tag: {}", tag);
			return null;
		}
		
		return getAssetLoadIfNeeded(key, options);
	}

	@Override
	public T getAssetLoadIfNeededOrError(K key) throws ContentException {
		T asset = getStore().retrieve(key);
		if(asset != null) {
			return asset;
		}
		
		return handleAssetResult(key, getLoader().load(key));
	}

	@Override
	public T getAssetLoadIfNeededOrError(K key, O options) throws ContentException {
		T asset = getStore().retrieve(key);
		if(asset != null) {
			return asset;
		}
		
		return handleAssetResult(key, getLoader().load(key, options));
	}


	@Override
	public CompletableFuture<T> addAssetResult(CompletableFuture<T> future, K key) {
		return handleAssetFuture(key, future);
	}

	@Override
	public CompletableFuture<T> addAssetResult(CompletableFuture<T> future, K key, O options) {
		return handleAssetFuture(key, future);
	}


	@Override
	public T addAsset(T asset, K key) {
		return handleAssetResult(key, asset);
	}

	@Override
	public T addAsset(T asset, K key, O options) {
		return handleAssetResult(key, asset);
	}

	
	@Override
	public boolean isAssetValid(T asset) {
		return getSpecialAssetManager().isAssetValid(asset);
	}
	
	@Override
	public boolean isAssetValidForKey(K key) {
		return getSpecialAssetManager().isAssetValid(getStore().retrieve(key));
	}
	
	@Override
	public boolean isAssetSpecial(T asset) {
		return getSpecialAssetManager().isAssetSpecial(asset);
	}
	
	@Override
	public boolean isAssetSpecialForKey(K key) {
		return getSpecialAssetManager().isAssetSpecial(getStore().retrieve(key));
	}
	
}
