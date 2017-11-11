package com.tokelon.toktales.core.render.model;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class RenderModel implements IRenderModel {

	
	private final Matrix4f modelMatrix;
	
	private final Vector3f translation;
	private final Vector3f rotation;
	private final Vector3f scaling;
	
	
	public RenderModel() {
		
		modelMatrix = new Matrix4f();
		
		translation = new Vector3f(0.0f, 0.0f, 0.0f);
		rotation = new Vector3f(0.0f, 0.0f, 0.0f);
		scaling = new Vector3f(1.0f, 1.0f, 1.0f);
	}
	
	
	
	public Vector3f getTranslation() {
		return translation;
	}
	
	public Vector3f getRotation() {
		return rotation;
	}
	
	public Vector3f getScale() {
		return scaling;
	}
	
	
	public Matrix4f getModelMatrix() {
		return modelMatrix;
	}
	
	public Matrix4f applyModelMatrix() {
		modelMatrix.identity()
			.translate(translation)
			.rotateXYZ((float) Math.toRadians(rotation.x), (float) Math.toRadians(rotation.y), (float) Math.toRadians(rotation.z))
			.scale(scaling);
		
		return modelMatrix;
	}
	
	
	
	public void setTranslation(float translationX, float translationY, float translationZ) {
		translation.set(translationX, translationY, translationZ);
	}
	
	public void setTranslation2D(float translationX, float translationY) {
		translation.set(translationX, translationY, 0.0f);
	}
	
	public void translate(float translateX, float translateY, float translateZ) {
		translation.add(translateX, translateY, translateZ);
	}
	
	public void translate2D(float translateX, float translateY) {
		translation.add(translateX, translateY, 0.0f);
	}
	
	
	public void setRotation(float rotationX, float rotationY, float rotationZ) {
		rotation.set(rotationX, rotationY, rotationZ);
	}
	
	public void setRotation2D(float rotationX, float rotationY) {
		rotation.set(rotationX, rotationY, 0.0f);
	}
	
	public void rotate(float rotateX, float rotateY, float rotateZ) {
		rotation.add(rotateX, rotateY, rotateZ);
	}
	
	public void rotate2D(float rotateX, float rotateY) {
		rotation.add(rotateX, rotateY, 0.0f);
	}
	
	
	public void setScaling(float scalingX, float scalingY, float scalingZ) {
		scaling.set(scalingX, scalingY, scalingZ);
	}
	
	public void setScaling2D(float scalingX, float scalingY) {
		scaling.set(scalingX, scalingY, 1.0f);
	}
	
	public void scale(float scaleX, float scaleY, float scaleZ) {
		scaling.mul(scaleX, scaleY, scaleZ);
	}
	
	public void scale2D(float scaleX, float scaleY) {
		scaling.mul(scaleX, scaleY, 1.0f);
	}
	
	
	
}
