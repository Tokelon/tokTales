package com.tokelon.toktales.tools.core.tiled.model;

import com.tokelon.toktales.tools.core.tiled.ITiledMapLevelHolder;

public interface ITiledMapImagelayer extends ITiledMapLevelHolder {


	public String getName();
	
	public int getX();
	public int getY();
	
	public float getOpacity();
	
	public boolean isVisible();
	
	
	public ITiledMapProperties getProperties();
	
	public ITMXImage getImage();
	
}
