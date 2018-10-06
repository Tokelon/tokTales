package com.tokelon.toktales.core.game.model;

public interface IPoint2i {
	
	public int x();
	public int y();
	
	
	public interface IMutablePoint2i extends IPoint2i {
		
		public IMutablePoint2i setX(int x);
		public IMutablePoint2i setY(int y);
		
		// check where these are used and whether we can optimize now that they return 'this'
		public IMutablePoint2i set(int x, int y);
		public IMutablePoint2i set(IPoint2i point);
		
	}
	
}
