package com.tokelon.toktales.core.content.graphics;

/** Represents a color with red, green, blue and alpha components.
 * <br>
 * Float values are in the range [0.0, 1.0]. 
 * 
 *
 */
public interface IRGBAColor {

	
	public float getRed();
	public float getGreen();
	public float getBlue();
	public float getAlpha();
	
	/*
	public int getRed();
	public int getGreen();
	public int getBlue();
	public int getAlpha();
	*/
	
	
	public interface IMutableRGBAColor extends IRGBAColor {
		
		public IMutableRGBAColor set(float red, float green, float blue, float alpha);
		
		public IMutableRGBAColor setRed(float red);
		public IMutableRGBAColor setGreen(float green);
		public IMutableRGBAColor setBlue(float blue);
		public IMutableRGBAColor setAlpha(float alpha);
		
	}
	
}
