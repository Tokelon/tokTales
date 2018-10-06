package com.tokelon.toktales.core.render.shapes;

public class LineShape extends BaseShape implements ILineShape {

	private float shapeX1 = 0.0f;
	private float shapeY1 = 0.0f;
	private float shapeX2 = 1.0f;
	private float shapeY2 = 1.0f;
	
	private float width = 1.0f;
	
	@Override
	public void setShape(float x1, float y1, float x2, float y2) {
		this.shapeX1 = x1;
		this.shapeY1 = y1;
		this.shapeX2 = x2;
		this.shapeY2 = y2;
	}

	@Override
	public void setWidth(float width) {
		this.width = width;
	}
	
	@Override
	public void setRelativeWidth(float relWidth) {
		this.width += relWidth;
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
	public float getWidth() {
		return width;
	}
	

}
