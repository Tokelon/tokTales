package com.tokelon.toktales.tools.tiled.model;

import java.util.List;

public interface ITiledMapTileset {
	
	public int getFirstGID();
	
	public boolean isReferenceToExternal();
	public boolean isExternal();
	
	public void setExternalTileset(ITiledMapTileset externalTileset);
	public ITiledMapTileset getExternalTileset();
	
	
	public String getSource();
	
	public String getName();
	
	
	public int getTileWidth();
	public int getTileHeight();
	
	
	public int getSpacing();
	public int getMargin();
	
	
	public ITMXTileoffset getTileoffset();
	public ITiledMapProperties getProperties();
	public ITMXTerraintypes getTerraintypes();
	public ITMXImage getImage();
	public List<? extends ITMXTile> getTileList();

	
	public int getTileCount();
	
	public boolean hasTileForGID(int gid);
	public ITMXTile getTileForGID(int gid);
	
	
	/**
	 * 
	 * @param tileIndex The index in the tileset, or tile ID.
	 * @return
	 */
	public int getHorizontalOffsetFor(int tileIndex);
	/**
	 * 
	 * @param tileIndex The index in the tileset, or tile ID.
	 * @return
	 */
	public int getVerticalOffsetFor(int tileIndex);
	

	public int getHorizontalTileCount();
	public int getVerticalTileCount();
	
}
