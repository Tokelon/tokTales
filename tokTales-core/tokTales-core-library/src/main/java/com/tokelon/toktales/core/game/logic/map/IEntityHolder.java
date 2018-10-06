package com.tokelon.toktales.core.game.logic.map;

import com.tokelon.toktales.core.game.model.map.entities.IMapEntity;

public interface IEntityHolder extends Iterable<IMapEntity> {

	// TODO: Delete and replace with IEntityContainer
	
	public int addEntity(IMapEntity entity);
	
	public IMapEntity removeEntity(int entityID);
	
	public boolean hasEntity(int entityID);
	
	public IMapEntity getEntity(int entityID);
	

	//public int getIDForEntity(IMapEntity entity);
	
}
