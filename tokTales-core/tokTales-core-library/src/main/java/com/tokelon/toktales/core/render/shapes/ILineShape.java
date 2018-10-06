package com.tokelon.toktales.core.render.shapes;

public interface ILineShape extends IRenderShape {

	
	public void setShape(float x1, float y1, float x2, float y2);
	
	//public void setShape(float startx, float starty, float length, float angle);
	
	public void setWidth(float width);
	public void setRelativeWidth(float relWidth);
	
	public void setSize(float size);
	public void setRelativeSize(float relSize);
	
	public void setPosition(float wx, float wy);
	public void setRelativePosition(float wxRel, float wyRel);
	
	
	public float getShapeX1();
	public float getShapeY1();
	public float getShapeX2();
	public float getShapeY2();

	public float getWidth();
	
	public float getSize();
	
	public float getPositionX();
	public float getPositionY();
	
}
