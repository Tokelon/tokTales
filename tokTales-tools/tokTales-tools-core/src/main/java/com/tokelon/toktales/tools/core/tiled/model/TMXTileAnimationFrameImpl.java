package com.tokelon.toktales.tools.core.tiled.model;

import java.io.ObjectStreamException;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.tokelon.toktales.tools.core.tiled.TiledMapFormatException;
import com.tokelon.toktales.tools.core.tiled.XMLValuesTiledMap;
import com.tokelon.toktales.tools.core.tiled.marshal.TiledMapMarshalTools;
import com.tokelon.toktales.tools.core.tiled.marshal.TiledMapUnmarshalException;

public class TMXTileAnimationFrameImpl implements ITMXTileAnimationFrame {

	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.NODE_NAME_MAP_TILESET_TILE_ANIMATION_FRAME_TILE_ID)
	private String mAttrTileId;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.NODE_NAME_MAP_TILESET_TILE_ANIMATION_FRAME_DURATION)
	private String mAttrDuration;
	
	
	public TMXTileAnimationFrameImpl() {
		// Default Ctor
	}
	
	
	@XStreamOmitField private int mTileId;
	@XStreamOmitField private int mDuration;

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
		mTileId = TiledMapMarshalTools.parseIntAttrNonNegative(mAttrTileId, "frame tile id");
		mDuration = TiledMapMarshalTools.parseIntAttrNonNegative(mAttrDuration, "frame duration");
	}


	private void prepareMarshal() {
		// Nothing
	}
	
	@Override
	public int getTileId() {
		return mTileId;
	}

	@Override
	public int getDuration() {
		return mDuration;
	}

}
