package com.tokelon.toktales.tools.tiled.model;

import java.util.List;

import com.tokelon.toktales.tools.tiled.ITiledMapLevelHolder;

public interface ITiledMapObjectgroup extends ITiledMapLevelHolder {

	public String getName();
	
	public String getColor();
	
	public float getOpacity();
	
	public boolean isVisible();
	
	
	
	public int getObjectCount();
	
	public ITMXObject getObject(int index);
	
	public List<? extends ITMXObject> getObjectList();
	
	
	public ITiledMapProperties getProperties();
	
}
