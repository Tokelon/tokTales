package com.tokelon.toktales.core.content.manage;

import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.engine.content.ContentNotFoundException;

import java9.util.concurrent.CompletableFuture;

public interface IAssetManager<T, K, O> {
	// TODO: Document all asset management types
	// Implement memory management strategy
	// Support for getting multiple assets?

	
	public ISpecialAssetManager<T> getSpecialAssetManager();
	
	// Maybe don't expose this here?
	public IAssetStore<T, K> getStore();
	
	public IAssetLoader<T, K, O> getLoader();
	//public IAssetLoader<? extends T, ? extends K> getLoader();
	
	
	

	
	public T getAsset(K key);
	public T getAsset(K key, O options);
	//public T getAsset(K key, O options, String group);
	
	public T getAssetIfKeyValid(K key, String tag);
	public T getAssetIfKeyValid(K key, O options, String tag);
	
	// The exception used here is more of a ContentNotLoadedException - Make it custom?
	public T getAssetOrError(K key) throws ContentNotFoundException;
	

	public T getAssetLoadIfNeeded(K key);
	public T getAssetLoadIfNeeded(K key, O options);

	public T getAssetLoadIfNeededIfKeyValid(K key, String tag);
	public T getAssetLoadIfNeededIfKeyValid(K key, O options, String tag);
	
	public T getAssetLoadIfNeededOrError(K key) throws ContentException;
	public T getAssetLoadIfNeededOrError(K key, O options) throws ContentException;
	
	
	// add, store, insert?
	public CompletableFuture<T> addAssetResult(CompletableFuture<T> future, K key);
	public CompletableFuture<T> addAssetResult(CompletableFuture<T> future, K key, O options);

	public T addAsset(T asset, K key);
	public T addAsset(T asset, K key, O options);

	/* Manage not loaded assets like this? What's the point of this functionality?
	public boolean addAsset(K key);
	
	// Maybe in addition to addAsset above?
	public boolean hasAsset(K key);
	public T removeAsset(K key);
	*/

	
	public boolean isAssetValid(T asset);
	public boolean isAssetValidForKey(K key);
	
	
	/* Implement these how?
	public void unloadAll();
	public void removeAll();

	public void reloadAll();
	public void reloadMissing();
	*/
	
	/* Replaced by IAssetStore methods
	public boolean addAssetLoaded(K key, T asset);
	public boolean hasAssetLoaded(K key);
	public T removeLoadedAsset(K key);
	*/
	
	
	
	/* TODO: Add with default binding
	public interface IAssetManagerFactory {
		
		// Is type resolution enough or does it need the types as parameters?
		public <T, K, O> IAssetManager<T, K, O> create(IAssetStore<T, K> assetStore, IAssetLoader<T, K, O> assetLoader);
	}
	*/
	
}
