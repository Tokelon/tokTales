package com.tokelon.toktales.core.game.world;

public class World implements IWorld {


	private final float tileSize;
	private final WorldGrid grid;
	
	
	public World(float gridTileSize) {
		this.tileSize = gridTileSize;
		grid = new WorldGrid(gridTileSize);
	}
	
	@Override
	public float getGridTileSize() {
		return tileSize;
	}

	@Override
	public IWorldGrid getGrid() {
		return grid;
	}

}
