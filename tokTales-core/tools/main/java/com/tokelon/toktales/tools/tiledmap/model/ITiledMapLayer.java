package com.tokelon.toktales.tools.tiledmap.model;

import com.tokelon.toktales.tools.tiledmap.ITiledMapLevelHolder;

public interface ITiledMapLayer extends ITiledMapLevelHolder {

	
	public String getName();
	
	public float getOpacity();
	
	public boolean isVisible();
	
	
	public ITMXData getData();
	
	public int getGIDForIndex(int index);
	
	public ITiledMapProperties getProperties();
	
}
