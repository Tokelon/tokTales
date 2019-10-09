package com.tokelon.toktales.tools.core.tiled.model;

import java.io.ObjectStreamException;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.tokelon.toktales.tools.core.tiled.TiledMapFormatException;
import com.tokelon.toktales.tools.core.tiled.XMLValuesTiledMap;
import com.tokelon.toktales.tools.core.tiled.marshal.TiledMapMarshalTools;
import com.tokelon.toktales.tools.core.tiled.marshal.TiledMapUnmarshalException;

public class TMXPolygonImpl implements ITMXPolygon {

	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_MULT_OBJECTGROUP_OBJECT_POLYGON_POINTS)
	private String mAttrPoints;
	
	
	public TMXPolygonImpl() {
		// Default Ctor
	}
	
	
	@XStreamOmitField private float[] mPointsX;
	@XStreamOmitField private float[] mPointsY;

	

	private Object readResolve() throws ObjectStreamException {
		
		try {
			processUnmarshal();
		} catch (TiledMapFormatException e) {
			throw new TiledMapUnmarshalException(e);
		}
		
		return this;
	}

	private Object writeReplace() throws ObjectStreamException {
		
		prepareMarshal();
		
		return this;
	}
	
	
	private void processUnmarshal() throws TiledMapFormatException {
		
		float[][] points = TiledMapMarshalTools.parsePointsAttr(mAttrPoints, "polygon points");
		
		mPointsX = points[0];
		mPointsY = points[1];
	}

	private void prepareMarshal() {
		
	}
	


	@Override
	public int getPointCount() {
		return mPointsX.length;
	}
	
	
	@Override
	public float[] getPointsX() {
		return mPointsX;
	}
	
	@Override
	public float[] getPointsY() {
		return mPointsY;
	}
	
	
}
