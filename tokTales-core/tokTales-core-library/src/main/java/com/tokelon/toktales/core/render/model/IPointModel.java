package com.tokelon.toktales.core.render.model;

import org.joml.Vector4f;

public interface IPointModel extends IRenderModel {

	public void setTargetCoordinates(float x, float y);
	
	public void setTargetColor(Vector4f color);
	
	public void setTargetSize(float size);
	
	
	
	public float getTargetX();
	public float getTargetY();
	
	public Vector4f getTargetColor();
	
	public float getTargetSize(); 
	
}
