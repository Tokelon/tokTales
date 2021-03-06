package com.tokelon.toktales.tools.tiled.model;

import java.io.ObjectStreamException;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.tokelon.toktales.tools.tiled.TiledMapFormatException;
import com.tokelon.toktales.tools.tiled.XMLValuesTiledMap;
import com.tokelon.toktales.tools.tiled.marshal.TiledMapMarshalTools;
import com.tokelon.toktales.tools.tiled.marshal.TiledMapUnmarshalException;


public class TMXTileImpl implements ITMXTile {

	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_TILESET_TILE_ID)
	private String mAttrId;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_TILESET_TILE_TERRAIN)			// Optional
	private String mAttrTerrain;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_TILESET_TILE_PROBABILITY)		// Optional
	private String mAttrProbability;
	
	
	
	@XStreamAlias(XMLValuesTiledMap.NODE_NAME_MAP_TILESET_TILE_PROPERTIES)
	private TiledMapPropertiesImpl mElemProperties;
	
	@XStreamAlias(XMLValuesTiledMap.NODE_NAME_MAP_TILESET_TILE_IMAGE)
	private TMXImageImpl mElemImage;
	
	@XStreamAlias(XMLValuesTiledMap.NODE_NAME_MAP_TILESET_TILE_OBJECTGROUP)
	private TiledMapObjectgroupImpl mElemObjectgroup;

	@XStreamAlias(XMLValuesTiledMap.NODE_NAME_MAP_TILESET_TILE_ANIMATION)
	private TMXTileAnimationImpl mElemAnimation;
	
	public TMXTileImpl() {
		// Default Ctor
	}
	
	
	
	
	@XStreamOmitField private int mID;
	
	@XStreamOmitField private ITiledMapTileset parentTileset;

	
	private Object readResolve() throws ObjectStreamException {
		// Not called for base classes!
		
		try {
			processUnmarshal();
		} catch (TiledMapFormatException e) {
			throw new TiledMapUnmarshalException(e);
		}
		
		return this;
	}

	private Object writeReplace() throws ObjectStreamException {
		// Not called for base classes?
		
		prepareMarshal();
		
		return this;
	}
	
	
	
	private void processUnmarshal() throws TiledMapFormatException {

		mID = TiledMapMarshalTools.parseIntAttrNonNegative(mAttrId, "tile id");
	
	}


	private void prepareMarshal() {
		// Nothing
	}

	

	
	@Override
	public int getID() {
		return mID;
	}

	@Override
	public String getTerrainValues() {
		return mAttrTerrain;
	}

	@Override
	public String getProbability() {
		return mAttrProbability;
	}

	@Override
	public boolean hasProperties() {
		return mElemProperties != null;
	}
	@Override
	public ITiledMapProperties getProperties() {
		return mElemProperties;
	}

	
	@Override
	public boolean hasImage() {
		return mElemImage != null;
	}

	@Override
	public ITMXImage getImage() {
		return mElemImage;
	}

	@Override
	public ITiledMapObjectgroup getObjectgroup() {
		return mElemObjectgroup;
	}

	@Override
	public boolean hasAnimation() {
		return mElemAnimation != null;
	}
	
	@Override
	public ITMXTileAnimation getAnimation() {
		return mElemAnimation;
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
		return String.format("TMXTile (id): %d", mID);
	}

}
