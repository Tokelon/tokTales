package com.tokelon.toktales.tools.tiled.model;

import java.io.ObjectStreamException;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.tokelon.toktales.tools.tiled.TiledMapFormatException;
import com.tokelon.toktales.tools.tiled.XMLValuesTiledMap;
import com.tokelon.toktales.tools.tiled.marshal.TiledMapMarshalTools;
import com.tokelon.toktales.tools.tiled.marshal.TiledMapUnmarshalException;

public class TiledMapImagelayerImpl implements ITiledMapImagelayer {

	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_IMAGELAYER_NAME)
	private String mAttrName;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_IMAGELAYER_X)
	private String mAttrX;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_IMAGELAYER_Y)
	private String mAttrY;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_IMAGELAYER_OPACITY)
	private String mAttrOpacity;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_IMAGELAYER_VISIBLE)
	private String mAttrVisible;
	
	
	
	@XStreamAlias(XMLValuesTiledMap.NODE_NAME_MAP_IMAGELAYER_PROPERTIES)
	private TiledMapPropertiesImpl mElemProperties = new TiledMapPropertiesImpl();
	
	@XStreamAlias(XMLValuesTiledMap.NODE_NAME_MAP_IMAGELAYER_IMAGE)
	private TMXImageImpl mElemImage;
	
	
	public TiledMapImagelayerImpl() {
		// Default Ctor
	}
	
	
	@XStreamOmitField private int mLevel = -1;
	
	@XStreamOmitField private int mX;
	@XStreamOmitField private int mY;
	
	@XStreamOmitField private float mOpacity = XMLValuesTiledMap.ATTR_DEFAULT_MAP_IMAGELAYER_OPACITY;		// Default value
	@XStreamOmitField private boolean mVisible = XMLValuesTiledMap.ATTR_DEFAULT_MAP_IMAGELAYER_VISIBLE;		// Default value

	
	
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
		
		// TODO: Fix
		// Apparently these can be missing ?
		if(mAttrX != null) {
			mX = TiledMapMarshalTools.parseIntAttrNonNegative(mAttrX, "imagelayer x");
		}
		if(mAttrY != null) {
			mY = TiledMapMarshalTools.parseIntAttrNonNegative(mAttrY, "imagelayer y");
		}
		
		
		if(mAttrVisible != null) {
			mVisible = TiledMapMarshalTools.parseVisibleAttr(mAttrVisible, "imagelayer visible");
		}
		
		if(mAttrOpacity != null) {
			mOpacity = TiledMapMarshalTools.parseOpacityAttr(mAttrOpacity, "imagelayer opacity");
		}
		
	}


	private void prepareMarshal() {
		
		TiledMapMarshalTools.nullPropertiesIfEmpty(mElemProperties);
	}

	

	

	@Override
	public String getName() {
		return mAttrName;
	}
	
	@Override
	public int getX() {
		return mX;
	}
	
	@Override
	public int getY() {
		return mY;
	}
	
	@Override
	public float getOpacity() {
		return mOpacity;
	}
	
	@Override
	public boolean isVisible() {
		return mVisible;
	}
	
	
	@Override
	public int getLevel() {
		return mLevel;
	}
	
	@Override
	public void setLevel(int level) {
		this.mLevel = level;
	}


	@Override
	public ITiledMapProperties getProperties() {
		return mElemProperties;
	}

	@Override
	public ITMXImage getImage() {
		return mElemImage;
	}
	
}
