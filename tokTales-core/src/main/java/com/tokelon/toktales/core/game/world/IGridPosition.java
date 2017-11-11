package com.tokelon.toktales.core.game.world;

import com.tokelon.toktales.core.game.model.IPoint2i;

public interface IGridPosition extends IPoint2i {

	
	// TODO: Maybe rename to ITilePosition
	
	public interface IMutableGridPosition extends IGridPosition, IMutablePoint2i {
		
		public void set(IGridPosition position);
	}
	
}
