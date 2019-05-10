package com.tokelon.toktales.core.content.sprite;

import com.tokelon.toktales.core.content.manage.sprite.ISpriteAssetKey;
import com.tokelon.toktales.core.resources.IResource;

public interface ISpriteset {
	// TODO: Rename to ISpritesheet ?
	// TODO: Define equals with key - use sprite count and size?
	
	
	// TODO: Replace these with the asset key
	public boolean hasResourceAttached();
	
	public IResource getResource();
	

	
	public ISpriteAssetKey getAssetKey();
	
	
	
	public String getSpritesetName();

	
	public int getSpriteWidth();
	public int getSpriteHeight();
	
	public int getHorizontalOffsetFor(int spriteIndex);
	public int getVerticalOffsetFor(int spriteIndex);
	
	public int getSpriteCount();
	
	/* Are these needed ? Do they make sense?
	public int getHorizontalSpriteCount();
	public int getVerticalSpriteCount();
	*/
	
	/*
	public int getSpacing();
	public int getMargin();
	*/
	
	/* TODO: Use these instead!
	public float getHorizontalStartFor(int spriteIndex);
	public float getHorizontalEndFor(int spriteIndex);
	
	public float getVerticalStartFor(int spriteIndex);
	public float getVerticalEndFor(int spriteIndex);
	*/
	
}
