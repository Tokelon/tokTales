package com.tokelon.toktales.core.tiled;

import com.tokelon.toktales.core.content.sprite.ISprite;
import com.tokelon.toktales.core.game.model.map.elements.IMapElement;
import com.tokelon.toktales.tools.tiled.model.ITMXProperty;
import com.tokelon.toktales.tools.tiled.model.ITMXTile;
import com.tokelon.toktales.tools.tiled.model.ITiledMapProperties;

public class TiledTileElement implements ITiledTileElement {

	private static final IElementType ELEMENT_TYPE = TiledMapElementTypes.TYPE_TILED_TILE;
	
	
	private final ITMXTile mTile;
	private final TiledTileSprite mSprite;
	
	private int mElementID;	// TODO: Implement
	private int mWalkable;
	private boolean mVisible = true;
	
	
	
	public TiledTileElement(ITMXTile tile, TiledTileSprite tileSprite) {

		this.mTile = tile;	// Do I need this tile??
		this.mSprite = tileSprite;
		
		
		if(tile.hasProperties()) {
			processProperties(tile.getProperties());
		}
	}
	

	
	
	private void processProperties(ITiledMapProperties properties) {
		ITMXProperty walkableProp = properties.getProperty(MetaValuesTiledMap.PROPERTY_ID_WALKABLE);
		if(walkableProp != null) {
			mWalkable = processWalkable(walkableProp.getValue());
		}
		
	}
	
	private int processWalkable(String value) {
		if(value == null || MetaValuesTiledMap.PROPERTY_VALUE_WALKABLE_YES.equals(value)) {
			return IMapElement.WALKABLE_YES;
		}
		else if(MetaValuesTiledMap.PROPERTY_VALUE_WALKABLE_NO.equals(value)) {
			return IMapElement.WALKABLE_NO;
		}
		else if(MetaValuesTiledMap.PROPERTY_VALUE_WALKABLE_PLAYER_ONLY.equals(value)) {
			return IMapElement.WALKABLE_PLAYER_ONLY;
		}
		else if(MetaValuesTiledMap.PROPERTY_VALUE_WALKABLE_NPC_ONLY.equals(value)) {
			return IMapElement.WALKABLE_NPC_ONLY;
		}
		else {
			return -1;
		}
	}
	
	
	public void setVisible(boolean visible) {
		this.mVisible = visible;
	}
	
	
	
	
	@Override
	public ITMXTile getTile() {
		return mTile;
	}
	
	@Override
	public ISprite getSprite() {
		return mSprite;
	}
	
	
	@Override
	public long getElementID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public IElementType getElementType() {
		return ELEMENT_TYPE;
	}

	
	@Override
	public int getWalkable() {
		return mWalkable;
	}
	
	@Override
	public boolean isVisible() {
		return mVisible;
	}
	
	@Override
	public String toString() {
		return String.format("TileElement {%s || %s}", mTile.toString(), mSprite.toString());
	}
	
}
