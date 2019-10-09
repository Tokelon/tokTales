package com.tokelon.toktales.tools.core.tiled.model;

import java.io.ObjectStreamException;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.tokelon.toktales.tools.core.tiled.TiledMapFormatException;
import com.tokelon.toktales.tools.core.tiled.XMLValuesTiledMap;
import com.tokelon.toktales.tools.core.tiled.marshal.TiledMapMarshalTools;
import com.tokelon.toktales.tools.core.tiled.marshal.TiledMapUnmarshalException;

public class TMXImageImpl implements ITMXImage {

	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_TILESET_IMAGE_FORMAT)
	private String mAttrFormat;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_TILESET_IMAGE_SOURCE)
	private String mAttrSource;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_TILESET_IMAGE_TRANS)
	private String mAttrTrans;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_TILESET_IMAGE_WIDTH)
	private String mAttrWidth;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_TILESET_IMAGE_HEIGHT)
	private String mAttrHeight;
	
	
	@XStreamAlias(XMLValuesTiledMap.NODE_NAME_MAP_TILESET_IMAGE_DATA)
	private TMXDataImpl mElemData;		// Why is this class used here? Is this the correct "data" type?
	

	public TMXImageImpl() {
		// Default Ctor
	}


	
	@XStreamOmitField private int mWidth;
	@XStreamOmitField private int mHeight;
	
	

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
		
		// TODO: Fix this
		// Apparently these can be missing ? For imagelayer - image
		if(mAttrWidth != null) {
			mWidth = TiledMapMarshalTools.parseIntAttrPositive(mAttrWidth, "image width");
		}
		if(mAttrHeight != null) {
			mHeight = TiledMapMarshalTools.parseIntAttrPositive(mAttrHeight, "image height");	
		}
		
	}


	private void prepareMarshal() {
		
		// Nothing
	}

	
	
	
	@Override
	public String getFormat() {
		return mAttrFormat;
	}

	@Override
	public String getSource() {
		return mAttrSource;
	}

	@Override
	public String getTrans() {
		return mAttrTrans;
	}

	@Override
	public int getWidth() {
		return mWidth;
	}

	@Override
	public int getHeight() {
		return mHeight;
	}
	
	@Override
	public ITMXData getData() {
		return mElemData;
	}
	
}
