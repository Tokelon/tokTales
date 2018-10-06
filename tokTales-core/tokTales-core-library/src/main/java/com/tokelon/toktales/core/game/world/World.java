package com.tokelon.toktales.core.game.world;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.inject.annotation.GridTileSize;

public class World implements IWorld {


	private final WorldGrid grid;

	private final float tileSize;
	
	@Inject
	public World(@GridTileSize float gridTileSize) {
		this.tileSize = gridTileSize;
		this.grid = new WorldGrid(gridTileSize);
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
