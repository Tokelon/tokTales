package com.tokelon.toktales.core.render.model;

import org.joml.Vector4f;

import com.tokelon.toktales.core.render.model.ISpriteModel;


public interface ISpriteFontModel extends ISpriteModel {

	
	public void setTargetColor(Vector4f color);
	
	
	public Vector4f getColor();
	
}
