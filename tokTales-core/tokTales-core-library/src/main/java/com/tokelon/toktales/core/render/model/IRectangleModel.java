package com.tokelon.toktales.core.render.model;

import org.joml.Vector4f;

public interface IRectangleModel extends IRenderModel {

	
	public static final int OUTLINE_TYPE_CENTER = 1;
	public static final int OUTLINE_TYPE_INNER = 2;
	public static final int OUTLINE_TYPE_OUTER = 3;
	
	
	public void setFill(boolean fill);
	
	public void setOutlineType(int type);
	
	
	
	public void setTargetCoordinates(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4);
	
	public void setTargetOutlineWidth(float outlineWidth);
	
	public void setTargetColor(Vector4f color);
	
	
	
	public boolean isFillSet();

	public int getOutlineType();
	
	
	public float getTargetX1();
	public float getTargetY1();
	public float getTargetX2();
	public float getTargetY2();
	public float getTargetX3();
	public float getTargetY3();
	public float getTargetX4();
	public float getTargetY4();
	
	public float getTargetOutlineWidth();

	public Vector4f getTargetColor();
	
}
