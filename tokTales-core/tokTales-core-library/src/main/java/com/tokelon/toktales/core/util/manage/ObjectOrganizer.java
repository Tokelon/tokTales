package com.tokelon.toktales.core.util.manage;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ObjectOrganizer<K, T> implements ISealedObjectOrganizer.IObjectOrganizer<K, T>, IObjectCoordinator.IOpenObjectCoordinator<K, T> {

	
	private final Map<K, T> map;
	private final Map<K, T> unmodifiableMap;

	public ObjectOrganizer() {
		map = new HashMap<>();
		unmodifiableMap = Collections.unmodifiableMap(map);
	}
	
	
	@Override
	public T get(K key) {
		return map.get(key);
	}

	@Override
	public boolean has(K key) {
		return map.containsKey(key);
	}

	@Override
	public int count() {
		return map.size();
	}

	@Override
	public Map<K, T> getObjectMap() {
		return unmodifiableMap;
	}

	@Override
	public T add(K key, T object) {
		return map.put(key, object);
	}

	@Override
	public T remove(K key) {
		return map.remove(key);
	}

	@Override
	public void empty() {
		map.clear();
	}

}
