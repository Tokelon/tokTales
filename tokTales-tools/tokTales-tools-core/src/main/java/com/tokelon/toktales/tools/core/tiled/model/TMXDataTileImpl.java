package com.tokelon.toktales.tools.core.tiled.model;

import java.io.ObjectStreamException;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.tokelon.toktales.tools.core.tiled.TiledMapFormatException;
import com.tokelon.toktales.tools.core.tiled.XMLValuesTiledMap;
import com.tokelon.toktales.tools.core.tiled.marshal.TiledMapMarshalTools;
import com.tokelon.toktales.tools.core.tiled.marshal.TiledMapUnmarshalException;

public class TMXDataTileImpl implements ITMXDataTile {

	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_MULT_DATA_TILE_GID)
	private String mAttrGid;
	
	
	public TMXDataTileImpl() {
		// Default Ctor
	}
	
	

	@XStreamOmitField private int mGID;
	
	
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

		mGID = TiledMapMarshalTools.parseIntAttrNonNegative(mAttrGid, "data tile gid");
	
	}


	private void prepareMarshal() {
		
		// Nothing?
	}


	
	@Override
	public int getValue() {
		return mGID;
	}
	
}
