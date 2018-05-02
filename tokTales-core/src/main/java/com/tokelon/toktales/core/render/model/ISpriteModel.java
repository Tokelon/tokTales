package com.tokelon.toktales.core.render.model;

import com.tokelon.toktales.core.content.sprite.ISprite;

public interface ISpriteModel extends IManagedTextureModel {

	
	public void setTargetSprite(ISprite sprite);
	
	public ISprite getTargetSprite();

}