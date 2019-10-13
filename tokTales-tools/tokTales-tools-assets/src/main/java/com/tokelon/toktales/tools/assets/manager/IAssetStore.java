package com.tokelon.toktales.tools.assets.manager;

public interface IAssetStore<T, K> {
	// TODO: Keep listeners? Disable recursive callbacks?
	// TODO: Implement groups
	// TODO: Implement StoringStrategy (Lifetime) (Forever, None, Normal, References)
	// with StoringManager (onFreeMemory etc.)
	
	// Implement listener support?
	// Allow null values?
	// Implement explicit "is loaded" logic in here?
	

	public boolean insert(K key, T asset);
	//public boolean insert(K key, T asset, String group);
	
	public T retrieve(K key);
	//public K getFirst(T asset);
	
	public boolean contains(K key);
	public boolean containsAsset(T asset);
	
	public T remove(K key);
	public int clear();

	public int getAssetCount();
	
	
	public void addChangeListener(IAssetStoreChangeListener<T, K> listener);
	public boolean removeChangeListener(IAssetStoreChangeListener<T, K> listener);
	public boolean hasChangeListener(IAssetStoreChangeListener<T, K> listener);
	
	
	/*
	public boolean assignGroup(K key, String group);
	public boolean unassignGroup(K key, String group);
	public boolean hasGroupAssigned(K key, String group);
	
	public int deleteGroupAssets(String group);
	
	public Stream<String> getGroups(K key);
	*/
	
	/*
	// Use immutable collections?
	public Map<K, T> assetMap();
	public Map<String, K> groupMap();
	
	// Or use streams?
	public Stream<T> assets();
	public Stream<K> keys();
	public Stream<String> groups();
	
	public Stream<K> find(T asset);
	*/

	/* Use group objects? Expose interface?
	public interface IAssetGroup<K, T> {
		public String getName();
		
		public boolean add(K key);
		public T remove(K key);
		public boolean contains(K key);
	}
	*/

	public interface IAssetStoreChangeListener<T, K> {
		//public void onAssetInsert(K key, T asset, String group);
		public void onAssetInsert(K key, T asset, T previousValue);
		public void onAssetRemove(K key, T asset);
		public void onAssetStoreClear(int clearedAssetCount);
		
		//public void onAssetGroupAssign(K key, T asset, String group);
		//public void onAssetGroupUnassign(K key, T asset, String group);
	}
	
	/*
	public interface IAssetStoreAccessListener<T, K> {
		public void onAssetRetrieve(K key, T asset);
		public void onAssetContains(K key);
		public void onAssetContainsKey(T asset);
	}
	*/
	
	/* TODO: Add with default binding
	public interface IAssetStoreFactory {
		
		public <T, K> IAssetStore<T, K> create();
	}
	*/
	
}
