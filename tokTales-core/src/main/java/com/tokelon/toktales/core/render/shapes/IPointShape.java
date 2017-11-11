package com.tokelon.toktales.core.render.shapes;

public interface IPointShape extends IRenderShape {

	public void setSize(float size);
	public void setRelativeSize(float relSize);

	public void setPosition(float wx, float wy);
	public void setRelativePosition(float wxRel, float wyRel);

	
	public float getSize();
	
	public float getPositionX();
	
	public float getPositionY();
	
}
