package com.tokelon.toktales.tools.tiledmap.model;

import java.util.List;

public interface ITiledMapProperties {

	
	public boolean hasProperty(String propName);
	
	public String getPropertyValue(String propName);
	
	public ITMXProperty getProperty(String propName);

	
	public int getPropertyCount();
	
	public List<? extends ITMXProperty> getPropertyList();
	
}
