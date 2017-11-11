package com.tokelon.toktales.tools.tiledmap.model;

import com.tokelon.toktales.tools.tiledmap.ITiledMapLevelHolder;

public interface ITiledMapImagelayer extends ITiledMapLevelHolder {


	public String getName();
	
	public int getX();
	public int getY();
	
	public float getOpacity();
	
	public boolean isVisible();
	
	
	public ITiledMapProperties getProperties();
	
	public ITMXImage getImage();
	
}
