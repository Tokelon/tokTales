package com.tokelon.toktales.extensions.core.game.screen;

import com.tokelon.toktales.core.game.logic.map.NoMapException;
import com.tokelon.toktales.core.game.model.map.IMapPosition;

public interface IMapRenderingTool {

	// TODO: Use or remove
	
	//public IMapPosition positionForBlock(int x, int y);

	/**
	 * 
	 * @param screenx
	 * @param screeny
	 * @return The respective map position, or null if screen position is outside the map.
	 * @throws NoMapException If there is no map loaded.
	 */
	public IMapPosition screenPositionForMapPosition(int screenx, int screeny) throws NoMapException;
	
}
