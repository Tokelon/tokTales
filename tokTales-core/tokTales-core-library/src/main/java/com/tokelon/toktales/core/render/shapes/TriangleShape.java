package com.tokelon.toktales.core.render.shapes;

public class TriangleShape extends BaseShape implements ITriangleShape {
	

	private float shapeX1 = 0.5f;
	private float shapeY1 = 0.0f;
	private float shapeX2 = 0.0f;
	private float shapeY2 = 1.0f;
	private float shapeX3 = 1.0f;
	private float shapeY3 = 1.0f;
	
	
	@Override
	public void setShape(float x1, float y1, float x2, float y2, float x3, float y3) {
		this.shapeX1 = x1;
		this.shapeY1 = y1;
		this.shapeX2 = x2;
		this.shapeY2 = y2;
		this.shapeX3 = x3;
		this.shapeY3 = y3;
	}


	
	@Override
	public float getShapeX1() {
		return shapeX1;
	}

	@Override
	public float getShapeY1() {
		return shapeY1;
	}

	@Override
	public float getShapeX2() {
		return shapeX2;
	}

	@Override
	public float getShapeY2() {
		return shapeY2;
	}

	@Override
	public float getShapeX3() {
		return shapeX3;
	}
	
	@Override
	public float getShapeY3() {
		return shapeY3;
	}
	
}
