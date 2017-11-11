package com.tokelon.toktales.core.engine.content;

public class GraphicLoadingOptionsImpl implements IGraphicLoadingOptions {

	private int mScaling = SCALING_NONE;
	private int mHorScaling;
	private int mVerScaling;
	
	
	public void setScaling(int mode, int hor, int ver) {
		this.mScaling = mode;
		this.mHorScaling = hor;
		this.mVerScaling = ver;
	}
	
	
	@Override
	public int getScalingOption() {
		return mScaling;
	}

	@Override
	public int getHorizontalScaling() {
		return mHorScaling;
	}

	@Override
	public int getVerticalScaling() {
		return mVerScaling;
	}

}
