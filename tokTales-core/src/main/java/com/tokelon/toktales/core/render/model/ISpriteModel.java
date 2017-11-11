package com.tokelon.toktales.core.render.model;

import com.tokelon.toktales.core.content.sprite.ISprite;
import com.tokelon.toktales.core.render.IKeyedTextureManager;
import com.tokelon.toktales.core.render.IRenderTexture;

public interface ISpriteModel extends ITexturedModel {

	
	public void setTextureManager(IKeyedTextureManager<ISprite> textureManager);

	// TODO: Split this up in two 'target' methods
	public void setTarget(ISprite sprite, IRenderTexture spriteTexture);

	
	public IKeyedTextureManager<ISprite> getTextureManager();

	public ISprite getSprite();

	public IRenderTexture getTexture();

	
}