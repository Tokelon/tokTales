package com.tokelon.toktales.core.game.logic.map;

import java.util.Iterator;

import com.tokelon.toktales.core.game.model.map.entities.IMapEntity;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public class EntityHolder implements IEntityHolder {


	private final TIntObjectMap<IMapEntity> entityMap = new TIntObjectHashMap<>();
	
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
		return entityMap.valueCollection().iterator(); // TODO: Important - This needs synchronizing
	}
	
}
