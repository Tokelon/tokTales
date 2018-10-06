package com.tokelon.toktales.core.game.model.map.entities;


public class PlayerEntity extends MapEntity implements IPlayerEntity {

	public static final IEntityType ENTITY_TYPE = MapEntityTypes.TYPE_PLAYER;
	
	private int mWalkOneBlockDurationMillis = 0;
	private int mWalkMinBlockDistanceBlocks = 1;
	
	/*
	public PlayerEntity() {
		super(ENTITY_TYPE);
	}
	*/





	@Override
	public void setWalkOneBlockDuration(int millis) {
		if(millis < 0) {
			throw new IllegalArgumentException("millis must be >= 0");
		}
		
		this.mWalkOneBlockDurationMillis = millis;
	}

	@Override
	public void setWalkMinBlockDistance(int blocks) {
		if(blocks < 1) {
			throw new IllegalArgumentException("blocks must be > 0");
		}
		
		this.mWalkMinBlockDistanceBlocks = blocks;
	}


	@Override
	public int getWalkOneBlockDuration() {
		return mWalkOneBlockDurationMillis;
	}

	@Override
	public int getWalkMinBlockDistance() {
		return mWalkMinBlockDistanceBlocks;
	}


}
