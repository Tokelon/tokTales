package com.tokelon.toktales.tools.tiled.model;

import java.io.ObjectStreamException;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.tokelon.toktales.tools.tiled.TiledMapFormatException;
import com.tokelon.toktales.tools.tiled.XMLValuesTiledMap;
import com.tokelon.toktales.tools.tiled.marshal.TiledMapMarshalTools;
import com.tokelon.toktales.tools.tiled.marshal.TiledMapUnmarshalException;

public class TiledMapLayerImpl implements ITiledMapLayer {

	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_LAYER_NAME)
	private String mAttrName;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_LAYER_WIDTH)		// TODO: Possibly remove these two
	private String mAttrWidth;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_LAYER_HEIGHT)		// See note in XMLValuesTiledMap
	private String mAttrHeight;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_LAYER_OPACITY)
	private String mAttrOpacity;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_LAYER_VISIBLE)
	private String mAttrVisible;
	
	
	@XStreamAlias(XMLValuesTiledMap.NODE_NAME_MAP_LAYER_PROPERTIES)
	private TiledMapPropertiesImpl mElemProperties = new TiledMapPropertiesImpl();
	
	@XStreamAlias(XMLValuesTiledMap.NODE_NAME_MAP_LAYER_DATA)
	private TMXDataImpl mElemData;
	
	
	public TiledMapLayerImpl() {
		// Default Ctor
	}
	
	
	
	@XStreamOmitField private int mLevel = -1;
	
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

		if(mAttrVisible != null) {
			mVisible = TiledMapMarshalTools.parseVisibleAttr(mAttrVisible, "layer visible");
		}
		
		if(mAttrOpacity != null) {
			mOpacity = TiledMapMarshalTools.parseOpacityAttr(mAttrOpacity, "layer opacity");
		}
		
		
		mElemData.publicProcessUnmarshal();		// Call process for data
	}


	private void prepareMarshal() {
		
		TiledMapMarshalTools.nullPropertiesIfEmpty(mElemProperties);
		
		
		mElemData.publicPrepareMarshal();		// Call prepare for data
	}



	
	
	

	@Override
	public ITMXData getData() {
		return mElemData;
	}


	@Override
	public int getGIDForIndex(int index) {
		return mElemData.getValueForIndex(index);
	}



	@Override
	public String getName() {
		return mAttrName;
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
	public ITiledMapProperties getProperties() {
		return mElemProperties;
	}
	
	
	@Override
	public int getLevel() {
		return mLevel;
	}
	
	@Override
	public void setLevel(int level) {
		this.mLevel = level;
	}
	
}
