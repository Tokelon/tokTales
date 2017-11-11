package com.tokelon.toktales.tools.tiledmap.model;

import java.util.List;

public interface ITMXTerraintypes {

	
	public boolean hasTerrain(String terrainName);
	
	public ITMXTerrain getTerrain(String terrainName);
	
	
	public int getTerrainCount();
	
	public List<? extends ITMXTerrain> getTerrainList();
	
}
