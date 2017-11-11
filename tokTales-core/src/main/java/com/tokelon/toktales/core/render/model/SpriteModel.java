package com.tokelon.toktales.core.render.model;

import com.tokelon.toktales.core.content.sprite.ISprite;
import com.tokelon.toktales.core.render.IKeyedTextureManager;
import com.tokelon.toktales.core.render.IRenderTexture;

public class SpriteModel extends TexturedModel implements ISpriteModel {

	
	private IKeyedTextureManager<ISprite> textureManager;
	
	private ISprite sprite;
	private IRenderTexture spriteTexture;
	
	

	public void setTextureManager(IKeyedTextureManager<ISprite> textureManager) {
		this.textureManager = textureManager;
	}
	
	public void setTarget(ISprite sprite, IRenderTexture spriteTexture) {
		this.sprite = sprite;
		this.spriteTexture = spriteTexture;
	}
	
	

	public IKeyedTextureManager<ISprite> getTextureManager() {
		return textureManager;
	}
	
	public ISprite getSprite() {
		return sprite;
	}
	
	public IRenderTexture getTexture() {
		return spriteTexture;
	}
	
	
}
