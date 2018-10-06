package com.tokelon.toktales.core.game.model;

import com.tokelon.toktales.core.game.model.IPoint2f.IMutablePoint2f;

public class Point2fImpl implements IMutablePoint2f {

	
	public float x = 0.0f;
	public float y = 0.0f;
	
	
	public Point2fImpl() { }
	
	public Point2fImpl(float x, float y) {
		this.x = x;
		this.y = y;
	}
	

	@Override
	public float x() {
		return x;
	}

	@Override
	public float y() {
		return y;
	}

	@Override
	public IMutablePoint2f setX(float x) {
		this.x = x;
		return this;
	}

	@Override
	public IMutablePoint2f setY(float y) {
		this.y = y;
		return this;
	}

	@Override
	public IMutablePoint2f set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	@Override
	public IMutablePoint2f set(IPoint2f p) {
		this.x = p.x();
		this.y = p.y();
		return this;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(!(obj instanceof IPoint2f)) {
			return false;
		}
		IPoint2f that = (IPoint2f) obj;
		
		return x == that.x() && y == that.y();
	}
	
	@Override
	public int hashCode() {
		return (int) (11 + x*13 + y*17);
	}
	
	
}
