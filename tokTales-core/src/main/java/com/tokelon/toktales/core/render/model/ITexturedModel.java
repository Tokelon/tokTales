package com.tokelon.toktales.core.render.model;

import org.joml.Vector2f;

public interface ITexturedModel extends IRenderModel {


	public void setInvertYAxis(boolean enabled);

	public float[] applyTextureCoordinates();
	
	
	public Vector2f getTextureTranslation();

	public Vector2f getTextureRotation();		// Should rotation be a Vector3f ? Rotation around the z axis?

	public Vector2f getTextureScaling();

	
	public void setTextureTranslation(float translationX, float translationY);

	public void translateTexture(float translateX, float translateY);
	
	
	public void setTextureRotation(float rotationX, float rotationY);
	
	public void rotateTexture(float rotateX, float rotateY);
	
	
	public void setTextureScaling(float scalingX, float scalingY);
	
	public void scaleTexture(float scaleX, float scaleY);
	
	
}