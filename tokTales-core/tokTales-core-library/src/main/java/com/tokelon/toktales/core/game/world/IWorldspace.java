package com.tokelon.toktales.core.game.world;

import java.util.Map;
import java.util.Set;

import com.tokelon.toktales.core.game.model.entity.IGameEntity;

public interface IWorldspace extends ICollisionManager {

	// Entities live in the worldspace
	
	
	/* 1. The worldspace should contains the entities and not the map.
	 * 2. The worldspace should update it's entities.
	 * 3. One map for one scene
	 * 
	 */

	
	public void adjustState(long timeMillis);
	
	//public long getUpdateTimePrevious();
	
	
	
	/** Puts the given entity into the worldspace with the given id.
	 * If an entry for the given id exists, it will be overridden.
	 * 
	 * @param id
	 * @param entity
	 * @return True if an entry for the given id existed and was overridden, false if not.
	 */
	public boolean putEntity(String id, IGameEntity entity);	// Rename to attachEntity ?
	
	/**
	 * @param id
	 * @return True if an entry for the given id exists, false if not.
	 */
	public boolean hasEntity(String id);
	
	/**
	 * 
	 * @param id
	 * @return The entity for the given id.
	 */
	public IGameEntity getEntity(String id);
	
	
	/** Removes the entry for the given id.
	 * 
	 * @param id
	 * @return True if an entry for the given id existed and was removed, false if not.
	 */
	public boolean removeEntity(String id);
	
	
	
	
	// TODO: Important - Implement iteration and fix this
	//public Iterator<IGameEntity> iterator();
	
	public Set<String> getEntityIDSet();
	public Map<String, IGameEntity> getEntityMap();
	
	
	
	// Add IWorldObject interface for things like collision blocks ?
	// But store them in the map?
	

	/*
	public boolean areaHasTrigger(IMWRectangle area);
	public boolean areaIsUnblocked(IMWRectangle area);
	*/
	
}
