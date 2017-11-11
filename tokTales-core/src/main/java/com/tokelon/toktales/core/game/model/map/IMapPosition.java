package com.tokelon.toktales.core.game.model.map;

import com.tokelon.toktales.core.game.world.IGridPosition;

public interface IMapPosition extends IGridPosition {
	
	// TODO: Use IGridPosition instead ?
	
	
	public interface IMutableMapPosition extends IMapPosition, IMutableGridPosition {
		
		//public void set(IMapPosition position);
	}
}
