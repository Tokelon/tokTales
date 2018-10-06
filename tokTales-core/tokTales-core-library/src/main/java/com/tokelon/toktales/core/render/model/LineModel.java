package com.tokelon.toktales.core.render.model;

import org.joml.Vector4f;

public class LineModel extends RenderModel implements ILineModel {

	public static final int DEFAULT_ALIGNMENT = ILineModel.ALIGNMENT_CENTER;
	public static final int DEFAULT_CENTER_ALIGNMENT = ILineModel.ALIGN_CENTER_IN;
	
	private float x1;
	private float y1;
	private float x2;
	private float y2;
	
	private Vector4f color = new Vector4f();
	
	private float width = 1.0f;
	
	private int alignment = DEFAULT_ALIGNMENT;
	private int centerAlignment = DEFAULT_CENTER_ALIGNMENT;
	
	
	@Override
	public void setTargetCoordinates(float x1, float y1, float x2, float y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	@Override
	public void setTargetColor(Vector4f color) {
		this.color = color;
	}

	@Override
	public void setTargetWidth(float width) {
		this.width = width; 
	}
	
	@Override
	public void setTargetAlignment(int alignment) {
		this.alignment = alignment;
	}
	
	@Override
	public void setTargetCenterAlignment(int centerAlignment) {
		this.centerAlignment = centerAlignment;
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
	public Vector4f getTargetColor() {
		return color;
	}
	
	@Override
	public float getTargetWidth() {
		return width;
	}
	
	@Override
	public int getTargetAlignment() {
		return alignment;
	}
	
	@Override
	public int getTargetCenterAlignment() {
		return centerAlignment;
	}
	
	
	
	public static int oppositeAlignmentFrom(int alignment) {
		if(alignment == ALIGNMENT_INNER) {
			return ALIGNMENT_OUTER;
		}
		else if(alignment == ALIGNMENT_OUTER) {
			return ALIGNMENT_INNER;
		}
		else {
			return ALIGNMENT_CENTER;
		}
	}

}
