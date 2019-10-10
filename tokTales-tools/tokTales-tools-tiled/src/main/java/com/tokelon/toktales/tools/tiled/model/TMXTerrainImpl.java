package com.tokelon.toktales.tools.tiled.model;

import java.io.ObjectStreamException;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.tokelon.toktales.tools.tiled.TiledMapFormatException;
import com.tokelon.toktales.tools.tiled.XMLValuesTiledMap;
import com.tokelon.toktales.tools.tiled.marshal.TiledMapMarshalTools;
import com.tokelon.toktales.tools.tiled.marshal.TiledMapUnmarshalException;

public class TMXTerrainImpl implements ITMXTerrain {

	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_TILESET_TERRAINTYPES_TERRAIN_NAME)
	private String mAttrName;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_TILESET_TERRAINTYPES_TERRAIN_TILE)
	private String mAttrTile;	// Local tile id inside tileset
	
	
	@XStreamAlias(XMLValuesTiledMap.NODE_NAME_MAP_TILESET_TERRAINTYPES_TERRAIN_PROPERTIES)
	private TiledMapPropertiesImpl mElemProperties = new TiledMapPropertiesImpl();
	
	
	public TMXTerrainImpl() {
		// Default Ctor
	}
	
	
	
	
	
	@XStreamOmitField private int mTile;
	

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

		mTile = TiledMapMarshalTools.parseIntAttrNonNegative(mAttrTile, "terrain tile");
	}


	private void prepareMarshal() {
		
		TiledMapMarshalTools.nullPropertiesIfEmpty(mElemProperties);
	}

	
	
	@Override
	public String getName() {
		return mAttrName;
	}

	@Override
	public int getTile() {
		return mTile;
	}

	@Override
	public ITiledMapProperties getProperties() {
		return mElemProperties;
	}

}
