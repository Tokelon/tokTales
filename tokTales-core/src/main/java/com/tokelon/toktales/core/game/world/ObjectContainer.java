package com.tokelon.toktales.core.game.world;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ObjectContainer<T> implements IObjectContainer<T> {

	private final Map<String, T> map;
	
	public ObjectContainer() {
		map = Collections.synchronizedMap(new HashMap<String, T>());
	}
	
	
	@Override
	public void addObject(String id, T object) {
		map.put(id, object);
	}

	@Override
	public T removeObject(String id) {
		return map.remove(id);
	}

	@Override
	public boolean containsObject(String id) {
		return map.containsKey(id);
	}

	@Override
	public T getObject(String id) {
		return map.get(id);
	}

	@Override
	public Map<String, T> getObjectMap() {
		// TODO: Make unmodifiable
		return map;
	}
	
}
