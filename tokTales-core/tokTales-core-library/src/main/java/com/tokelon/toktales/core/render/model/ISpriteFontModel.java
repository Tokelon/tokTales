package com.tokelon.toktales.core.render.model;

import org.joml.Vector4f;

import com.tokelon.toktales.core.prog.annotation.Experimental;
import com.tokelon.toktales.core.render.model.ISpriteModel;

@Experimental
public interface ISpriteFontModel extends ISpriteModel {

	
	public Vector4f getTargetColor();
	
	public void setTargetColor(Vector4f color);
	
}
