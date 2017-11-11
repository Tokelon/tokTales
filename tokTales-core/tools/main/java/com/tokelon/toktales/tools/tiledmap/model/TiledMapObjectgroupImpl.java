package com.tokelon.toktales.tools.tiledmap.model;

import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.tokelon.toktales.tools.tiledmap.TiledMapFormatException;
import com.tokelon.toktales.tools.tiledmap.XMLValuesTiledMap;
import com.tokelon.toktales.tools.tiledmap.marshal.TiledMapMarshalTools;
import com.tokelon.toktales.tools.tiledmap.marshal.TiledMapUnmarshalException;


public class TiledMapObjectgroupImpl implements ITiledMapObjectgroup {

	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_MULT_OBJECTGROUP_NAME)
	private String mAttrName;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_MULT_OBJECTGROUP_COLOR)
	private String mAttrColor;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_MULT_OBJECTGROUP_OPACITY)
	private String mAttrOpacity;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_MULT_OBJECTGROUP_VISIBLE)
	private String mAttrVisible;
	
	
	@XStreamAlias(XMLValuesTiledMap.NODE_NAME_MAP_MULT_OBJECTGROUP_PROPERTIES)
	private TiledMapPropertiesImpl mElemProperties = new TiledMapPropertiesImpl();
	
	@XStreamImplicit(itemFieldName=XMLValuesTiledMap.NODE_NAME_MAP_MULT_OBJECTGROUP_OBJECT)
	private List<TMXObjectImpl> mElemListObjects = new ArrayList<TMXObjectImpl>();
	
	
	
	public TiledMapObjectgroupImpl() {
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
			mVisible = TiledMapMarshalTools.parseVisibleAttr(mAttrVisible, "objectgroup visible");
		}
		
		if(mAttrOpacity != null) {
			mOpacity = TiledMapMarshalTools.parseOpacityAttr(mAttrOpacity, "objectgroup opacity");
		}
	}


	private void prepareMarshal() {
		
		TiledMapMarshalTools.nullPropertiesIfEmpty(mElemProperties);
		
		TiledMapMarshalTools.nullListIfEmpty(mElemListObjects);
	}
	
	
	
	
	@Override
	public int getObjectCount() {
		return mElemListObjects.size();
	}

	@Override
	public ITMXObject getObject(int index) {
		return mElemListObjects.get(index);
	}





	@Override
	public String getName() {
		return mAttrName;
	}

	@Override
	public String getColor() {
		return mAttrColor;
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
	public List<? extends ITMXObject> getObjectList() {
		return mElemListObjects;
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
