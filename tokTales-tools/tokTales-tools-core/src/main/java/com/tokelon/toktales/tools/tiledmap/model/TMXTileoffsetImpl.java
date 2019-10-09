package com.tokelon.toktales.tools.tiledmap.model;

import java.io.ObjectStreamException;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.tokelon.toktales.tools.tiledmap.TiledMapFormatException;
import com.tokelon.toktales.tools.tiledmap.XMLValuesTiledMap;
import com.tokelon.toktales.tools.tiledmap.marshal.TiledMapMarshalTools;
import com.tokelon.toktales.tools.tiledmap.marshal.TiledMapUnmarshalException;

public class TMXTileoffsetImpl implements ITMXTileoffset {

	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_TILESET_TILEOFFSET_X)
	private String mAttrX;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_TILESET_TILEOFFSET_Y)
	private String mAttrY;
	
	
	public TMXTileoffsetImpl() {
		// Default Ctor
	}
	
	
	
	
	@XStreamOmitField private int mX;
	@XStreamOmitField private int mY;

	
	
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

		mX = TiledMapMarshalTools.parseIntAttrNonNegative(mAttrX, "tile offset x");
		mY = TiledMapMarshalTools.parseIntAttrNonNegative(mAttrY, "tile offset y");
	}


	private void prepareMarshal() {
		
		// Nothing
	}

	
	@Override
	public int getX() {
		return mX;
	}

	@Override
	public int getY() {
		return mY;
	}
	
}