package com.tokelon.toktales.tools.core.tiled.model;

public interface ITMXObject {

	
	public int getID();
	
	/**
	 * @return The object name or null.
	 */
	public String getName();
	/**
	 * @return The object type or null.
	 */
	public String getType();
	
	public int getX();
	public int getY();
	
	public int getWidth();
	public int getHeight();
	public int getRotation();
	
	
	public boolean hasGID();
	public int getGID();
	
	public boolean isVisible();
	
	
	public ITiledMapProperties getProperties();
	
	public boolean hasEllipse();
	public ITMXEllipse getEllipse();
	
	public boolean hasPolygon();
	public ITMXPolygon getPolygon();
	
	public boolean hasPolyline();
	public ITMXPolyline getPolyline();
	
}
