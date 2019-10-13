package com.tokelon.toktales.tools.assets.manager;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;

public class DefaultAssetStore<T, K> implements IAssetStore<T, K> {
	
	
	private final T nullAsset;
	
	private final Map<K, T> assetMap;
	private final Set<IAssetStoreChangeListener<T, K>> listeners;
	
	@Inject
	public DefaultAssetStore(ISpecialAssetFactory<T> specialAssetNullFactory) {
		this(specialAssetNullFactory, 32, 0.75f, 1);
	}
	
	public DefaultAssetStore(ISpecialAssetFactory<T> specialAssetNullFactory, int initialCapacity, float loadFactor, int concurrencyLevel) {
		nullAsset = specialAssetNullFactory.create();
		assetMap = new ConcurrentHashMap<>(initialCapacity, loadFactor, concurrencyLevel);
		listeners = Collections.synchronizedSet(new HashSet<>());
	}
	
	
	protected T getNullAsset() {
		return nullAsset;
	}

	protected T getAssetIn(T in) {
		return in == null ? getNullAsset() : in;
	}
	
	protected T getAssetOut(T out) {
		return out == getNullAsset() ? null : out;
	}
	
	
	@Override
	public boolean insert(K key, T asset) {
		T actualAsset = getAssetIn(asset);
		
		T oldAsset = assetMap.put(key, actualAsset);
		
		synchronized (listeners) {
			for(IAssetStoreChangeListener<T, K> listener: listeners) {
				listener.onAssetInsert(key, actualAsset, oldAsset);
			}
		}
		
		return oldAsset != null;
	}

	@Override
	public T retrieve(K key) {
		return getAssetOut(assetMap.get(key));
	}

	@Override
	public boolean contains(K key) {
		return assetMap.containsKey(key);
	}

	@Override
	public boolean containsAsset(T asset) {
		return assetMap.containsValue(asset);
	}


	@Override
	public T remove(K key) {
		T removedAsset = getAssetOut(assetMap.remove(key));
		
		synchronized (listeners) {
			for(IAssetStoreChangeListener<T, K> listener: listeners) {
				listener.onAssetRemove(key, removedAsset);
			}
		}
		
		return removedAsset;
	}
	
	@Override
	public int clear() {
		int assetCount = assetMap.size();
		assetMap.clear();
		
		synchronized (listeners) {
			for(IAssetStoreChangeListener<T, K> listener: listeners) {
				listener.onAssetStoreClear(assetCount);
			}
		}
		
		return assetCount;
	}

	@Override
	public int getAssetCount() {
		return assetMap.size();
	}

	
	@Override
	public void addChangeListener(IAssetStoreChangeListener<T, K> listener) {
		listeners.add(listener);
	}

	@Override
	public boolean removeChangeListener(IAssetStoreChangeListener<T, K> listener) {
		return listeners.remove(listener);
	}

	@Override
	public boolean hasChangeListener(IAssetStoreChangeListener<T, K> listener) {
		return listeners.contains(listener);
	}
	
}
