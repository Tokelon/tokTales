package com.tokelon.toktales.tools.tiledmap.model;

public interface ITMXTile {

	public int getID();
	
	//public int[] getTerrainValues();
	public String getTerrainValues();
	
	public String getProbability();
	
	
	public boolean hasImage();
	
	public ITMXImage getImage();
	
	
	public boolean hasProperties();
	public ITiledMapProperties getProperties();
	
	public ITiledMapObjectgroup getObjectgroup();
	
	public boolean hasAnimation();
	public ITMXTileAnimation getAnimation();
	
	
	public void setParentTileset(ITiledMapTileset parent);
	public ITiledMapTileset getParentTileset();
	
}
