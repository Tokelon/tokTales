package com.tokelon.toktales.tools.tiled.marshal;

import com.tokelon.toktales.tools.tiled.model.ITMXImage;
import com.tokelon.toktales.tools.tiled.model.ITMXTile;
import com.tokelon.toktales.tools.tiled.model.ITMXTileAnimation;
import com.tokelon.toktales.tools.tiled.model.ITiledMapObjectgroup;
import com.tokelon.toktales.tools.tiled.model.ITiledMapProperties;
import com.tokelon.toktales.tools.tiled.model.ITiledMapTileset;

public class TMXTileImageRef implements ITMXTile {

	
	private final int mID;
	private ITiledMapTileset parentTileset;
	
	public TMXTileImageRef(int id) {
		this.mID = id;
	}
	
	public TMXTileImageRef(int id, ITiledMapTileset parent) {
		this.mID = id;
		this.parentTileset = parent;
	}
	
	
	
	@Override
	public int getID() {
		return mID;
	}

	@Override
	public String getTerrainValues() {
		return null;
	}

	@Override
	public String getProbability() {
		return null;
	}

	
	@Override
	public boolean hasProperties() {
		return false;
	}
	@Override
	public ITiledMapProperties getProperties() {
		return null;
	}

	
	@Override
	public boolean hasImage() {
		return false;
	}
	
	@Override
	public ITMXImage getImage() {
		return null;
	}

	@Override
	public ITiledMapObjectgroup getObjectgroup() {
		return null;
	}

	@Override
	public boolean hasAnimation() {
		return false;
	}
	
	@Override
	public ITMXTileAnimation getAnimation() {
		return null;
	}
	

	@Override
	public void setParentTileset(ITiledMapTileset parent) {
		this.parentTileset = parent;
	}

	@Override
	public ITiledMapTileset getParentTileset() {
		return parentTileset;
	}
	
	@Override
	public String toString() {
		return String.format("TMXTileImageRef (mID): %d", mID);
	}

}
