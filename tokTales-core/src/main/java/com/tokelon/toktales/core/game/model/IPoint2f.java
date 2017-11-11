package com.tokelon.toktales.core.game.model;

public interface IPoint2f {

	/* TODO: Important - Implement
	 * 
	 *  For equals and hashCode()
	 * 
	 */
	//public static float EPSILON = 0.01f;
	/*
	public boolean isEqual(IPoint2i point);
	public boolean isEqual(float x, float y);
	
	public boolean isEqual(IPoint2i point, float epsilon);
	public boolean isEqual(float x, float y, float epsilon);
	*/
	
	public float x();
	public float y();

	
	public interface IMutablePoint2f extends IPoint2f {
		
		public IMutablePoint2f setX(float x);
		public IMutablePoint2f setY(float y);
		
		// check where these are used and whether we can optimize now that they return 'this'
		public IMutablePoint2f set(float x, float y);
		public IMutablePoint2f set(IPoint2f p);
		
	}
	
}
