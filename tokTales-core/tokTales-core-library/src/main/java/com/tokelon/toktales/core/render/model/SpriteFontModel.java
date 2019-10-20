package com.tokelon.toktales.core.render.model;

import org.joml.Vector4f;

import com.tokelon.toktales.core.render.model.SpriteModel;
import com.tokelon.toktales.tools.core.annotations.condition.Experimental;

@Experimental
public class SpriteFontModel extends SpriteModel implements ISpriteFontModel {


	private Vector4f mColor;
	
	@Override
	public void setTargetColor(Vector4f color) {
		this.mColor = color;
	}

	@Override
	public Vector4f getTargetColor() {
		return mColor;
	}

}
