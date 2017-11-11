package com.tokelon.toktales.core.render.model;

import org.joml.Vector4f;

import com.tokelon.toktales.core.render.model.ManagedTextureModel;


public class TextureFontModel extends ManagedTextureModel implements ITextureFontModel {

	// TODO: Important! - Move from extensions into src
	
	private Vector4f color;
	
	@Override
	public void setTargetColor(Vector4f color) {
		this.color = color;
	}

	@Override
	public Vector4f getColor() {
		return color;
	}

}
