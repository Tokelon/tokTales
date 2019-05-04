package com.tokelon.toktales.core.content.manage.sprite;

public interface ISpritesetAssetKey {
	// TODO: Define equals - use sprite count and size?
	
	// TODO: Maybe just include in ISpriteset and simply have a name?
	
	
	public String getSpritesetName(); // getName()
	
	
	public int getSpriteCount();
	
	
	
	public int getSpriteWidth();
	public int getSpriteHeight();
	
	public int getHorizontalOffsetFor(int spriteIndex);
	public int getVerticalOffsetFor(int spriteIndex);
	
	
	/* Use these?
	public float getHorizontalStartFor(int spriteIndex);
	public float getHorizontalEndFor(int spriteIndex);
	
	public float getVerticalStartFor(int spriteIndex);
	public float getVerticalEndFor(int spriteIndex);
	*/
	
}
