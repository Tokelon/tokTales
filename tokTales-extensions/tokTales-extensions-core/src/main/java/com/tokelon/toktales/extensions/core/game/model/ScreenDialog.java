package com.tokelon.toktales.extensions.core.game.model;

import com.tokelon.toktales.core.content.graphics.IRGBAColor;
import com.tokelon.toktales.core.content.graphics.RGBAColor;

public class ScreenDialog extends TextBox implements IScreenDialog {

	
	private IRGBAColor textColor = RGBAColor.createFromCode("#FFF");
	
	private IRGBAColor borderColor = RGBAColor.createFromCode("FFF");
	private float borderSize = 3f;
	
	private IRGBAColor backgroundColor = RGBAColor.createFromCodeWithAlpha("#FFFFFF", 0.3f);
	
	public ScreenDialog(float wWidth, float wHeight, float textSize) {
		super(wWidth, wHeight, textSize);
	}

	
	public void setTextColor(IRGBAColor textColor) {
		this.textColor = textColor;
	}
	
	@Override
	public IRGBAColor getTextColor() {
		return textColor;
	}

	
	public void setBorderColor(IRGBAColor borderColor) {
		this.borderColor = borderColor;
	}
	
	@Override
	public IRGBAColor getBorderColor() {
		return borderColor;
	}

	public void setBorderSize(float borderSize) {
		this.borderSize = borderSize;
	}
	
	@Override
	public float getBorderSize() {
		return borderSize;
	}

	
	public void setBackgroundColor(IRGBAColor backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	
	@Override
	public IRGBAColor getBackgroundColor() {
		return backgroundColor;
	}
	
	
}
