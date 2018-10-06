package com.tokelon.toktales.core.render.model;

import com.tokelon.toktales.core.content.sprite.ISprite;

public class SpriteModel extends ManagedTextureModel implements ISpriteModel {

	
	private ISprite sprite;
	

	@Override
	public void setTargetSprite(ISprite sprite) {
		this.sprite = sprite;
	}
	
	@Override
	public ISprite getTargetSprite() {
		return sprite;
	}
	
}
