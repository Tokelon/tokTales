package com.tokelon.toktales.core.render.model;

import org.joml.Vector4f;

import com.tokelon.toktales.core.render.model.IManagedTextureModel;

public interface ITextureFontModel extends IManagedTextureModel {

	
	public Vector4f getTargetColor();
	
	public void setTargetColor(Vector4f color);

}
