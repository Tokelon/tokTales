package com.tokelon.toktales.core.engine.content;

public interface IGraphicLoadingOptions {
	
	public static final int SCALING_NONE = 1;
	public static final int SCALING_MULTIPLIER = 2;
	public static final int SCALING_FIXED = 3;
	
	public int getScalingOption();
	
	public int getHorizontalScaling();
	public int getVerticalScaling();
	
}
