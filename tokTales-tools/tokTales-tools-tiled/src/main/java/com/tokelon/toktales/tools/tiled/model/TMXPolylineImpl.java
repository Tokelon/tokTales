package com.tokelon.toktales.tools.tiled.model;

import java.io.ObjectStreamException;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.tokelon.toktales.tools.tiled.TiledMapFormatException;
import com.tokelon.toktales.tools.tiled.XMLValuesTiledMap;
import com.tokelon.toktales.tools.tiled.marshal.TiledMapMarshalTools;
import com.tokelon.toktales.tools.tiled.marshal.TiledMapUnmarshalException;

public class TMXPolylineImpl implements ITMXPolyline {

	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_MULT_OBJECTGROUP_OBJECT_POLYLINE_POINTS)
	private String mAttrPoints;
	
		
	public TMXPolylineImpl() {
		// Default Ctor
	}
	

	@XStreamOmitField private float[] mPointsX;
	@XStreamOmitField private float[] mPointsY;



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
		
		float[][] points = TiledMapMarshalTools.parsePointsAttr(mAttrPoints, "polyline points");
		
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
