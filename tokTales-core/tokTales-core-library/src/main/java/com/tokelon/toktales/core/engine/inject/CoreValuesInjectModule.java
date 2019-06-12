package com.tokelon.toktales.core.engine.inject;

import com.tokelon.toktales.core.engine.inject.annotation.GridTileSize;

public class CoreValuesInjectModule extends AbstractInjectModule {


	public static final float DEFAULT_GRID_TILE_SIZE = 32.0f;
	
	
	@Override
	protected void configure() {
		bind(Float.class).annotatedWith(GridTileSize.class).toInstance(DEFAULT_GRID_TILE_SIZE);
	}

}
