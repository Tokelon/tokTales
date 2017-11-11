package com.tokelon.toktales.core.game.logic.map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.tokelon.toktales.core.game.model.map.entities.IMapEntity;

public class EntityHolder implements IEntityHolder {

	private final Map<Integer, IMapEntity> entityMap = new HashMap<Integer, IMapEntity>();
	
	private int idCounter = 1;
	
	
	@Override
	public int addEntity(IMapEntity entity) {
		int resID = idCounter++;
		entityMap.put(resID, entity);
		return resID;
	}

	@Override
	public IMapEntity removeEntity(int entityID) {
		return entityMap.remove(entityID);
	}
	
	@Override
	public boolean hasEntity(int entityID) {
		return entityMap.containsKey(entityID);
	}

	@Override
	public IMapEntity getEntity(int entityID) {
		return entityMap.get(entityID);
	}

	
	@Override
	public Iterator<IMapEntity> iterator() {
		return entityMap.values().iterator();			// TODO: Important - This needs synchronizing
	}

	
}
