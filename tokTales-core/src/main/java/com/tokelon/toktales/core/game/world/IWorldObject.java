package com.tokelon.toktales.core.game.world;

public interface IWorldObject {

	// id from map?
	//public int getID();

	
	public float getLayerPosition();
	
	public String getObjectName();
	
	public String getObjectType();
	
	public float getWorldX();
	public float getWorldY();
	
	public float getWidth();
	public float getHeight();
	
	// int, float ?
	public int getRotation();
	
	public boolean isVisible();
	

	//public boolean hasGeometry(); --> always true, rectangle geometry as fallback
	public IWorldGeometry getGeometry();
	
	
	// for map gid
	// Either have sprite in here or have different type ISpriteObject
	//public ISprite getSprite();
	//public IBaseGraphic getGraphic();
	
	
	
	
}
