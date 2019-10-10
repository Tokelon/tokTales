package com.tokelon.toktales.tools.tiled.model;

import java.util.List;

public interface ITiledMap {


	public static final int ORIENTATION_ORTHOGONAL = 1;
	public static final int ORIENTATION_ISOMETRIC = 2;
	public static final int ORIENTATION_ISOMETRIC_STAGGERED = 3;
	public static final int ORIENTATION_HEXAGONAL_STAGGERED = 4;
	
	public static final int RENDER_ORDER_RIGHT_DOWN = 1;
	public static final int RENDER_ORDER_RIGHT_UP = 2;
	public static final int RENDER_ORDER_LEFT_DOWN = 3;
	public static final int RENDER_ORDER_LEFT_UP = 4;
	
	
	public static final int LEVEL_TYPE_LAYER = 1;
	public static final int LEVEL_TYPE_OBJECTGROUP = 2;
	public static final int LEVEL_TYPE_IMAGELAYER = 3;
	
	
	public int getWidth();
	public int getHeight();
	
	public int getTileWidth();
	public int getTileHeight();
	
	public String getName();
	public String getFilename();
	
	public int getLevelCount();
	public int getTypeForLevel(int level);
	
	
	public ITiledMapLayer getLayerAt(int level);
	public ITiledMapObjectgroup getObjectgroupAt(int level);
	public ITiledMapImagelayer getImagelayerAt(int level);
	
	public List<? extends ITiledMapLayer> getLayerList();
	public List<? extends ITiledMapObjectgroup> getObjectgroupList();
	public List<? extends ITiledMapImagelayer> getImagelayerList();
	
	public List<? extends ITiledMapTileset> getTilesetList();
	
	public List<ITiledMapTileset> getExternalTilesetList();
	
	public ITiledMapProperties getProperties();
	
	
	public ITMXTile getTileForGID(int gid);
	
}
