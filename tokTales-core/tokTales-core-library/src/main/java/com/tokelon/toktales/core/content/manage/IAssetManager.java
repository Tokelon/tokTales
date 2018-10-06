package com.tokelon.toktales.core.content.manage;

import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.engine.content.ContentNotFoundException;

public interface IAssetManager<T, K, O> {
	// TODO: Document all asset management types
	// Implement memory management strategy and maybe make asset type disposable?

	// Support for getting multiple assets?

	
	
	// Maybe don't expose this here?
	public IAssetStore<T, K> getStore();
	
	public IAssetLoader<T, K, O> getLoader();
	//public IAssetLoader<? extends T, ? extends K> getLoader();
	

	
	public T getAsset(K key);
	public T getAsset(K key, O options);
	//public T getAsset(K key, O options, String group);
	
	
	// The exception used here is more of a ContentNotLoadedException - Make it custom?
	public T getAssetOrError(K key) throws ContentNotFoundException;
	

	public T getAssetLoadIfNeeded(K key);
	public T getAssetLoadIfNeeded(K key, O options);
	
	public T getAssetLoadIfNeededOrError(K key) throws ContentException;
	public T getAssetLoadIfNeededOrError(K key, O options) throws ContentException;
	
	//public CompletableFuture<T> requestAsset(K key);



	/* Manage not loaded assets like this? What's the point of this functionality?
	public boolean addAsset(K key);
	public boolean hasAsset(K key);
	public T removeAsset(K key);
	*/
	
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
	

	// How to handle special assets? -> No special treatment; Maybe add an identifier method.
	//public T getSpecialAsset(K key);
	//public boolean isAssetSpecial(T asset);
	//public boolean isAssetKeySpecial(K key);
	//public String/Type getSpecialAssetType(T asset);
	
	
	/* TODO: Add with default binding
	public interface IAssetManagerFactory {
		
		// Is type resolution enough or does it need the types as parameters?
		public <T, K, O> IAssetManager<T, K, O> create(IAssetStore<T, K> assetStore, IAssetLoader<T, K, O> assetLoader);
	}
	*/
	
}
