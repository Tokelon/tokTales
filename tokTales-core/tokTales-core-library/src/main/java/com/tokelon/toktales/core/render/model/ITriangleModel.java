package com.tokelon.toktales.core.render.model;

import org.joml.Vector4f;

public interface ITriangleModel extends IRenderModel {

	
	public void setFill(boolean fill);

	
	public void setTargetCoordinates(float x1, float y1, float x2, float y2, float x3, float y3);
	
	public void setTargetOutlineWidth(float outlineWidth);
	//public void setTargetOutlineColor(Vector4f outlineColor);

	public void setTargetColor(Vector4f color);
	
	
	

	public boolean isFillSet();

	
	public float getTargetX1();
	public float getTargetY1();
	public float getTargetX2();
	public float getTargetY2();
	public float getTargetX3();
	public float getTargetY3();
	
	public float getTargetOutlineWidth();
	//public Vector4f getTargetOutlineColor();
	
	public Vector4f getTargetColor();
	
	
}
