package com.tokelon.toktales.core.content.sprite;

import com.tokelon.toktales.core.resources.IResource;

public interface ISpriteset {

	// TODO: Rename to ISpritesheet ?
	

	public String getSpritesetName();
	
	public boolean hasResourceAttached();
	
	public IResource getResource();
	
	
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
}
