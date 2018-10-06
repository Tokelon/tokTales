package com.tokelon.toktales.core.render.shapes;

public interface ITriangleShape extends IRenderShape {

	
	public void setShape(float x1, float y1, float x2, float y2, float x3, float y3);
	
	//public void setShape(float startx, float starty, float length1, float angle1, float length2, float angle2);
	
	public void setSize(float size);
	public void setRelativeSize(float relSize);
	
	public void setPosition(float wx, float wy);
	public void setRelativePosition(float wxRel, float wyRel);
	
	
	public float getShapeX1();
	public float getShapeY1();
	public float getShapeX2();
	public float getShapeY2();
	public float getShapeX3();
	public float getShapeY3();
	
	public float getSize();
	
	public float getPositionX();
	public float getPositionY();
	
}
