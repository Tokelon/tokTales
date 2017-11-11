package com.tokelon.toktales.core.game.model.map;

import com.tokelon.toktales.core.game.model.IPoint2i;
import com.tokelon.toktales.core.game.model.map.IMapPosition.IMutableMapPosition;
import com.tokelon.toktales.core.game.world.IGridPosition;

public class MapPositionImpl implements IMutableMapPosition {

	public int x = 0;
	public int y = 0;
	
	
	// TODO: Extend GridPositionImpl or remove completely 
	
	
	public MapPositionImpl() { }
	
	public MapPositionImpl(int x, int y) {
		// TODO: Maybe thrown illegal argument exception if x < 0 or y < 0
		this.x = x;
		this.y = y;
	}
	
	public MapPositionImpl(IGridPosition pos) {
		x = pos.x();
		y = pos.y();
	}
	
	public MapPositionImpl xInc() {
		x++;
		return this;
	}
	public MapPositionImpl xDec() {
		x--;
		return this;
	}
	
	public MapPositionImpl yInc() {
		y++;
		return this;
	}	
	public MapPositionImpl yDec() {
		y--;
		return this;
	}
	
	
	@Override
	public int x() {
		return x;
	}
	
	@Override
	public int y() {
		return y;
	}

	
	
	@Override
	public IMutablePoint2i setX(int x) {
		this.x = x;
		return this;
	}

	@Override
	public IMutablePoint2i setY(int y) {
		this.y = y;
		return this;
	}

	@Override
	public IMutablePoint2i set(int x, int y) {
		this.x = x;
		this.y = y;
		return this;
	}	
	
	@Override
	public void set(IGridPosition position) {
		this.x = position.x();
		this.y = position.y();
	}
	
	@Override
	public IMutablePoint2i set(IPoint2i point) {
		this.x = point.x();
		this.y = point.y();
		return this;
	}
	
	
	@Override
	public boolean equals(Object o) {
		if(this == o) {
			return true;
		}
		if(!(o instanceof IMapPosition)) {
			return false;
		}
		IMapPosition that = (IMapPosition)o;
		
		return x == that.x() && y == that.y();
	}

	@Override
	public int hashCode() {
		//int result = 37;
		// TODO: Possibly implement different hashcode
		return 13 + 41*x + 67*y;
	}
	
}
