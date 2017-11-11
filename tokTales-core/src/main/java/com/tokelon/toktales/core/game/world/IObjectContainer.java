package com.tokelon.toktales.core.game.world;

import java.util.Map;
import java.util.Set;


public interface IObjectContainer<T> {

	public void addObject(String id, T object);
	
	public T removeObject(String id);
	
	public boolean containsObject(String id);
	
	public T getObject(String id);
	
	
	//public Set<String> getObjectIdSet();
	
	public Map<String, T> getObjectMap();
	
	
}
