package com.tokelon.toktales.core.render.model;

import org.joml.Vector4f;

public class TriangleModel extends RenderModel implements ITriangleModel {

	
	private boolean fill = true;

	
	private float x1;
	private float y1;
	private float x2;
	private float y2;
	private float x3;
	private float y3;

	private float outlineWidth = 1.0f;
	
	private Vector4f color = new Vector4f();
	
	

	@Override
	public void setFill(boolean fill) {
		this.fill = fill;
	}
	
	
	@Override
	public void setTargetCoordinates(float x1, float y1, float x2, float y2, float x3, float y3) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.x3 = x3;
		this.y3 = y3;
	}

	@Override
	public void setTargetOutlineWidth(float outlineWidth) {
		this.outlineWidth = outlineWidth;
	}
	
	@Override
	public void setTargetColor(Vector4f color) {
		this.color = color;
	}

	

	@Override
	public boolean isFillSet() {
		return fill;
	}
	
	
	@Override
	public float getTargetX1() {
		return x1;
	}

	@Override
	public float getTargetY1() {
		return y1;
	}

	@Override
	public float getTargetX2() {
		return x2;
	}

	@Override
	public float getTargetY2() {
		return y2;
	}

	@Override
	public float getTargetX3() {
		return x3;
	}

	@Override
	public float getTargetY3() {
		return y3;
	}
	
	@Override
	public float getTargetOutlineWidth() {
		return outlineWidth;
	}
	
	@Override
	public Vector4f getTargetColor() {
		return color;
	}

}
