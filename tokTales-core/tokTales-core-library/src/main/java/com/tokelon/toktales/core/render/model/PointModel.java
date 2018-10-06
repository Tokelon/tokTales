package com.tokelon.toktales.core.render.model;

import org.joml.Vector4f;

public class PointModel extends RenderModel implements IPointModel {

	private float x;
	private float y;
	
	private Vector4f color = new Vector4f();
	
	private float size = 1.0f;
	
	
	@Override
	public void setTargetCoordinates(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void setTargetColor(Vector4f color) {
		this.color = color;
	}

	@Override
	public void setTargetSize(float size) {
		this.size = size;
	}

	@Override
	public float getTargetX() {
		return x;
	}

	@Override
	public float getTargetY() {
		return y;
	}

	@Override
	public Vector4f getTargetColor() {
		return color;
	}

	@Override
	public float getTargetSize() {
		return size;
	}

}
