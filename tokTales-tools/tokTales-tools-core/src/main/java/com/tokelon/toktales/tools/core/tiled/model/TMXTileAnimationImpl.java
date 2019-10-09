package com.tokelon.toktales.tools.core.tiled.model;

import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.tokelon.toktales.tools.core.tiled.TiledMapFormatException;
import com.tokelon.toktales.tools.core.tiled.XMLValuesTiledMap;
import com.tokelon.toktales.tools.core.tiled.marshal.TiledMapMarshalTools;
import com.tokelon.toktales.tools.core.tiled.marshal.TiledMapUnmarshalException;

public class TMXTileAnimationImpl implements ITMXTileAnimation {


	@XStreamImplicit(itemFieldName=XMLValuesTiledMap.NODE_NAME_MAP_TILESET_TILE_ANIMATION_FRAME)
	private List<TMXTileAnimationFrameImpl> mElemListFrames = new ArrayList<TMXTileAnimationFrameImpl>();
	
	
	public TMXTileAnimationImpl() {
		// Default Ctor
	}
	
	
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
		// Nothing
	}


	private void prepareMarshal() {
		TiledMapMarshalTools.nullListIfEmpty(mElemListFrames);
	}
	
	
	@Override
	public List<? extends ITMXTileAnimationFrame> getFrames() {
		return mElemListFrames;
	}
	
}
