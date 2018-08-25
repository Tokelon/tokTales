package com.tokelon.toktales.core.content.manage;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.engine.content.ContentNotFoundException;
import com.tokelon.toktales.core.engine.log.ILogger;

import java9.util.concurrent.CompletableFuture;

public class DefaultAssetManager<T, K, O> implements IAssetManager<T, K, O> {

	private static final String TAG = "DefaultAssetManager";
	
	
	private final ILogger logger;
	private final IAssetStore<T, K> assetStore;
	private final IAssetLoader<T, K, O> assetLoader;

	@Inject
	public DefaultAssetManager(ILogger logger, IAssetStore<T, K> assetStore, IAssetLoader<T, K, O> assetLoader) {
		this.logger = logger;
		this.assetStore = assetStore;
		this.assetLoader = assetLoader;
	}
	
	
	protected ILogger getLogger() {
		return logger;
	}
	
	@Override
	public IAssetStore<T, K> getStore() {
		return assetStore;
	}
	
	@Override
	public IAssetLoader<T, K, O> getLoader() {
		return assetLoader;
	}

	
	private void handleAssetResult(K key, T asset) {
		if(asset == null) {
			// TODO: Insert with INullResultAsset?
		}
		
		assetStore.insert(key, asset);
	}
	
	private void handleAssetFuture(K key, CompletableFuture<T> future) {
		future
		.exceptionally((exception) -> {
			// TODO: Insert with ILoadErrorAsset
			assetStore.insert(key, null);
			
			logger.e(TAG, String.format("Asset future completed exceptionally for [key=%s]: %s", key, exception));
			return null;
		})
		.thenAccept((result) -> {
			handleAssetResult(key, result);
		});
	}
	
	@Override
	public T getAsset(K key) {
		T asset = assetStore.retrieve(key);
		if(asset != null) {
			return asset;
		}
		
		handleAssetFuture(key, assetLoader.enqueue(key));
		return null;
	}

	@Override
	public T getAsset(K key, O options) {
		T asset = assetStore.retrieve(key);
		if(asset != null) {
			return asset;
		}
		
		handleAssetFuture(key, assetLoader.enqueue(key, options));
		return null;
	}

	@Override
	public T getAssetOrError(K key) throws ContentNotFoundException {
		T asset = assetStore.retrieve(key);
		if(asset != null) {
			return asset;
		}
		
		// TODO: Really use ContentNotFoundException for this?
		throw new ContentNotFoundException("Asset for key {%s} was not loaded");
	}


	@Override
	public T getAssetLoadIfNeeded(K key) {
		T asset = assetStore.retrieve(key);
		if(asset != null) {
			return asset;
		}
		
		T result = null;
		try {
			result = assetLoader.load(key);
			handleAssetResult(key, asset);
		} catch (ContentException e) {
			logger.e(TAG, String.format("Asset loading failed for [key=%s]: %s", key, e));
		}
		
		return result;
	}

	@Override
	public T getAssetLoadIfNeeded(K key, O options) {
		T asset = assetStore.retrieve(key);
		if(asset != null) {
			return asset;
		}
		
		T result = null;
		try {
			result = assetLoader.load(key, options);
			handleAssetResult(key, asset);
		} catch (ContentException e) {
			logger.e(TAG, String.format("Asset loading failed for [key=%s, options=%s]: %s", key, options, e));
		}
		
		return result;
	}

	@Override
	public T getAssetLoadIfNeededOrError(K key) throws ContentException {
		T asset = assetStore.retrieve(key);
		if(asset != null) {
			return asset;
		}
		
		T result = assetLoader.load(key);
		
		handleAssetResult(key, asset);
		return result;
	}

	@Override
	public T getAssetLoadIfNeededOrError(K key, O options) throws ContentException {
		T asset = assetStore.retrieve(key);
		if(asset != null) {
			return asset;
		}
		
		T result = assetLoader.load(key, options);
		
		handleAssetResult(key, asset);
		return result;
	}

}
