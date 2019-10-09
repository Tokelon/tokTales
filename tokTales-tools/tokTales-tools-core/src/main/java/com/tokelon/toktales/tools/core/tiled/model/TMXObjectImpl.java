package com.tokelon.toktales.tools.core.tiled.model;

import java.io.ObjectStreamException;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.tokelon.toktales.tools.core.tiled.TiledMapFormatException;
import com.tokelon.toktales.tools.core.tiled.XMLValuesTiledMap;
import com.tokelon.toktales.tools.core.tiled.marshal.TiledMapMarshalTools;
import com.tokelon.toktales.tools.core.tiled.marshal.TiledMapUnmarshalException;

public class TMXObjectImpl implements ITMXObject {

	public static final String[] FIELD_ORDER = {
		"mAttrId", "mAttrName", "mAttrType", "mAttrX", "mAttrY", "mAttrWidth", "mAttrHeight", "mAttrRotation", "mAttrGid", "mAttrVisible"
		, "mElemProperties", "mElemEllipse", "mElemPolygon", "mElemPolyline"
		
		//Ommited
		, "FIELD_ORDER", "mID", "mX", "mY", "mWidth", "mHeight", "mRotation", "mGID", "mVisible"
	};
	
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_MULT_OBJECTGROUP_OBJECT_ID)
	private String mAttrId;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_MULT_OBJECTGROUP_OBJECT_NAME)
	private String mAttrName;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_MULT_OBJECTGROUP_OBJECT_TYPE)
	private String mAttrType;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_MULT_OBJECTGROUP_OBJECT_X)
	private String mAttrX;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_MULT_OBJECTGROUP_OBJECT_Y)
	private String mAttrY;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_MULT_OBJECTGROUP_OBJECT_WIDTH)
	private String mAttrWidth;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_MULT_OBJECTGROUP_OBJECT_HEIGHT)
	private String mAttrHeight;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_MULT_OBJECTGROUP_OBJECT_ROTATION)
	private String mAttrRotation;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_MULT_OBJECTGROUP_OBJECT_GID)			// Optional
	private String mAttrGid;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_MULT_OBJECTGROUP_OBJECT_VISIBLE)
	private String mAttrVisible;
	
	
	
	@XStreamAlias(XMLValuesTiledMap.NODE_NAME_MAP_MULT_OBJECTGROUP_OBJECT_PROPERTIES)
	private TiledMapPropertiesImpl mElemProperties = new TiledMapPropertiesImpl();
	
	@XStreamAlias(XMLValuesTiledMap.NODE_NAME_MAP_MULT_OBJECTGROUP_OBJECT_ELLIPSE)
	private TMXEllipseImpl mElemEllipse;

	@XStreamAlias(XMLValuesTiledMap.NODE_NAME_MAP_MULT_OBJECTGROUP_OBJECT_POLYGON)
	private TMXPolygonImpl mElemPolygon;

	@XStreamAlias(XMLValuesTiledMap.NODE_NAME_MAP_MULT_OBJECTGROUP_OBJECT_POLYLINE)
	private TMXPolylineImpl mElemPolyline;

	
	
	public TMXObjectImpl() {
		// Default Ctor
	}

	
	
	@XStreamOmitField private int mID;
	@XStreamOmitField private int mX;
	@XStreamOmitField private int mY;
	@XStreamOmitField private int mWidth = XMLValuesTiledMap.ATTR_DEFAULT_MAP_MULT_OBJECTGROUP_OBJECT_WIDTH;			// Default Value
	@XStreamOmitField private int mHeight = XMLValuesTiledMap.ATTR_DEFAULT_MAP_MULT_OBJECTGROUP_OBJECT_HEIGHT;			// Default Value
	@XStreamOmitField private int mRotation = XMLValuesTiledMap.ATTR_DEFAULT_MAP_MULT_OBJECTGROUP_OBJECT_ROTATION;		// Default Value
	@XStreamOmitField private int mGID;
	@XStreamOmitField private boolean mVisible = XMLValuesTiledMap.ATTR_DEFAULT_MAP_MULT_OBJECTGROUP_OBJECT_VISIBLE;	// Default Value
	
	

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

		mID = TiledMapMarshalTools.parseIntAttrNonNegative(mAttrId, "object id");
		mX = TiledMapMarshalTools.parseIntAttrNonNegative(mAttrX, "object x");
		mY = TiledMapMarshalTools.parseIntAttrNonNegative(mAttrY, "object y");
		
		if(mAttrWidth != null) {
			mWidth = TiledMapMarshalTools.parseIntAttrNonNegative(mAttrWidth, "object width");
		}
		
		if(mAttrHeight != null) {
			mHeight = TiledMapMarshalTools.parseIntAttrNonNegative(mAttrHeight, "object height");
		}
		
		if(mAttrRotation != null) {
			mRotation = TiledMapMarshalTools.parseIntAttrNonNegative(mAttrRotation, "object rotation");
		}
		
		if(mAttrGid != null) {
			mGID = TiledMapMarshalTools.parseIntAttrPositive(mAttrGid, "object gid");
		}
		
		if(mAttrVisible != null) {
			mVisible = TiledMapMarshalTools.parseVisibleAttr(mAttrVisible, "object visible");
		}
		
	}


	private void prepareMarshal() {

		TiledMapMarshalTools.nullPropertiesIfEmpty(mElemProperties);
	}

	
	
	
	@Override
	public int getID() {
		return mID;
	}

	@Override
	public String getName() {
		return mAttrName;
	}

	@Override
	public String getType() {
		return mAttrType;
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
	public int getWidth() {
		return mWidth;
	}

	@Override
	public int getHeight() {
		return mHeight;
	}

	@Override
	public int getRotation() {
		return mRotation;
	}

	@Override
	public boolean hasGID() {
		return mAttrGid != null;
	}
	@Override
	public int getGID() {
		return mGID;
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
	public boolean hasEllipse() {
		return mElemEllipse != null;
	}
	@Override
	public ITMXEllipse getEllipse() {
		return mElemEllipse;
	}

	@Override
	public boolean hasPolygon() {
		return mElemPolygon != null;
	}
	@Override
	public ITMXPolygon getPolygon() {
		return mElemPolygon;
	}

	@Override
	public boolean hasPolyline() {
		return mElemPolyline != null;
	}
	@Override
	public ITMXPolyline getPolyline() {
		return mElemPolyline;
	}
	
}
