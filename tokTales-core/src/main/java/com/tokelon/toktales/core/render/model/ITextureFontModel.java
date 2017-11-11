package com.tokelon.toktales.core.render.model;

import org.joml.Vector4f;

import com.tokelon.toktales.core.render.model.IManagedTextureModel;


public interface ITextureFontModel extends IManagedTextureModel {	// TODO: Maybe rename to IFontModel

	// TODO: Important! - Move from extensions into src
	
	public void setTargetColor(Vector4f color);
	
	public Vector4f getColor();

}
