package com.tokelon.toktales.core.render.model;

import org.joml.Vector4f;

public class FontModel extends ManagedTextureModel implements IFontModel {

	
	private Vector4f color;
	
	@Override
	public Vector4f getTargetColor() {
		return color;
	}
	
	@Override
	public void setTargetColor(Vector4f color) {
		this.color = color;
	}

}
