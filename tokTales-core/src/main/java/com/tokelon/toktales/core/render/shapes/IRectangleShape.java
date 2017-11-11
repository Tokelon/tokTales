package com.tokelon.toktales.core.render.shapes;

public interface IRectangleShape extends IRenderShape {
	
	// Not a parallelogram

	
	//public void setShapeSquare(float sizeLength);
	
	public void setShape(float hLength, float vLength);
	
	public void setSize(float size);
	public void setRelativeSize(float relSize);

	public void setPosition(float wx, float wy);
	public void setRelativePosition(float wxRel, float wyRel);

	
	public float getShapeLengthH();
	public float getShapeLengthV();
	
	public float getSize();
	
	public float getPositionX();
	public float getPositionY();
	
}
