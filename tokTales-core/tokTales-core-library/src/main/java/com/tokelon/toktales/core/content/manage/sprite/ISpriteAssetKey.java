package com.tokelon.toktales.core.content.manage.sprite;

public interface ISpriteAssetKey { // Extend from ISpritesetAssetKey ?
	// TODO: Define equals
	// If there is a spriteset -> only consider that
	
	// TODO: Maybe just include in ISprite?

	
	/* How to do loading of spriteset instead of sprite
	 * 
	 * 1. Define equals as: If spriteset -> compare spritesets --> not good
	 * equal in the sense of loading but not in the sense of interchangeable
	 * 
	 * 2. Make spriteset extend sprite and then store spriteset instead of sprite in the store
	 * also if spriteset, pass the spriteset as key
	 */
	
	
	public String getSpriteName();
	
	
	// This will all be removed and replaced by the methods in ISprite
	public boolean isSpriteset();
	
	public ISpritesetAssetKey getSpritesetKey();
	
	public int getSpritesetIndex();
	
}
