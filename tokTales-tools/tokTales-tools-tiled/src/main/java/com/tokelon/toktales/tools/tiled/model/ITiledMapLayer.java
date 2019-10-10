package com.tokelon.toktales.tools.tiled.model;

import com.tokelon.toktales.tools.tiled.ITiledMapLevelHolder;

public interface ITiledMapLayer extends ITiledMapLevelHolder {

	
	public String getName();
	
	public float getOpacity();
	
	public boolean isVisible();
	
	
	public ITMXData getData();
	
	public int getGIDForIndex(int index);
	
	public ITiledMapProperties getProperties();
	
}
