package com.tokelon.toktales.core.game.model;

import com.tokelon.toktales.core.game.model.IPoint2i.IMutablePoint2i;

public class Point2iImpl implements IMutablePoint2i {

	
	public int x = 0;
	public int y = 0;
	
	public Point2iImpl() { }
	
	public Point2iImpl(int x, int y) {
		this.x = x;
		this.y = y;
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
	public IMutablePoint2i set(IPoint2i point) {
		this.x = point.x();
		this.y = point.y();
		return this;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(!(obj instanceof IPoint2i)) {
			return false;
		}
		IPoint2i that = (IPoint2i) obj;
		
		return x == that.x() && y == that.y();
	}
	
	@Override
	public int hashCode() {
		return 11 + x*13 + y*17;
	}

}
