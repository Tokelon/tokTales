package com.tokelon.toktales.core.render.model;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public interface IRenderModel {

	public Matrix4f applyModelMatrix();

	public Matrix4f getModelMatrix();

	
	public Vector3f getTranslation();

	public Vector3f getRotation();

	public Vector3f getScale();



	public void setTranslation(float translationX, float translationY, float translationZ);

	public void setTranslation2D(float translationX, float translationY);

	public void translate(float translateX, float translateY, float translateZ);

	public void translate2D(float translateX, float translateY);

	
	public void setRotation(float rotationX, float rotationY, float rotationZ);

	public void setRotation2D(float rotationX, float rotationY);

	public void rotate(float rotateX, float rotateY, float rotateZ);

	public void rotate2D(float rotateX, float rotateY);

	
	public void setScaling(float scalingX, float scalingY, float scalingZ);

	public void setScaling2D(float scalingX, float scalingY);

	public void scale(float scaleX, float scaleY, float scaleZ);

	public void scale2D(float scaleX, float scaleY);

	
}