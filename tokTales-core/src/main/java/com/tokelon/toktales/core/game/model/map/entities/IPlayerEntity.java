package com.tokelon.toktales.core.game.model.map.entities;



public interface IPlayerEntity extends IMapEntity {
	

	
	public void setWalkOneBlockDuration(int millis);
	
	public void setWalkMinBlockDistance(int blocks);	// setMoveMinBlockDistance ?
	
	
	public int getWalkOneBlockDuration();
	
	public int getWalkMinBlockDistance();
	
}
