package com.tokelon.toktales.core.render.model;

import org.joml.Vector4f;

public interface ILineModel extends IRenderModel {

	public static final int ALIGNMENT_CENTER = 1;
	public static final int ALIGNMENT_INNER = 2;
	public static final int ALIGNMENT_OUTER = 3;
	//public static final int ALIGNMENT_INNER_FRAME = ;
	//public static final int ALIGNMENT_OUTER_FRAME = ;
	
	public static final int ALIGN_CENTER_IN = 11;
	public static final int ALIGN_CENTER_OUT = 12;
	
	public void setTargetCoordinates(float x1, float y1, float x2, float y2);
	
	public void setTargetColor(Vector4f color);
	
	public void setTargetWidth(float width);
	
	public void setTargetAlignment(int alignment);
	public void setTargetCenterAlignment(int centerAlignment);
	
	public float getTargetX1();
	public float getTargetY1();
	public float getTargetX2();
	public float getTargetY2();
	
	public Vector4f getTargetColor();
	
	public float getTargetWidth();
	
	public int getTargetAlignment();
	public int getTargetCenterAlignment();
	
}
