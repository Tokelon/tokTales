package com.tokelon.toktales.core.render.shapes;

public class RectangleShape extends BaseShape implements IRectangleShape {

	
	private float shapeLengthH = 1.0f;
	private float shapeLengthV = 1.0f;
	
	
	@Override
	public void setShape(float hLength, float vLength) {
		this.shapeLengthH = hLength;
		this.shapeLengthV = vLength;
	}


	
	@Override
	public float getShapeLengthH() {
		return shapeLengthH;
	}

	@Override
	public float getShapeLengthV() {
		return shapeLengthV;
	}

}
