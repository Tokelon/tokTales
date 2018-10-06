package com.tokelon.toktales.tools.tiledmap.model;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.tokelon.toktales.tools.tiledmap.XMLValuesTiledMap;


public class TiledMapPropertiesImpl implements ITiledMapProperties {
	
	
	@XStreamImplicit(itemFieldName=XMLValuesTiledMap.NODE_NAME_MAP_MULT_PROPERTIES_PROPERTY) //, type=TMXPropertyImpl.class)
	private List<TMXPropertyImpl> mElementListProperties = new ArrayList<TMXPropertyImpl>();
	
	
	public TiledMapPropertiesImpl() {
		// Default C-tor
	}

	
	
	@Override
	public boolean hasProperty(String propName) {
		if(propName == null) {
			throw new NullPointerException();
		}
		
		
		for(ITMXProperty p: mElementListProperties) {
			if(propName.equals(p.getName())) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public String getPropertyValue(String propName) {
		if(propName == null) {
			throw new NullPointerException();
		}
		
		for(ITMXProperty p: mElementListProperties) {
			if(propName.equals(p.getName())) {
				return p.getValue();
			}
		}
		
		return null;
	}

	@Override
	public ITMXProperty getProperty(String propName) {
		if(propName == null) {
			throw new NullPointerException();
		}

		for(ITMXProperty p: mElementListProperties) {
			if(propName.equals(p.getName())) {
				return p;
			}
		}
		
		return null;
	}
	
	@Override
	public int getPropertyCount() {
		return mElementListProperties.size();
	}
	
	@Override
	public List<? extends ITMXProperty> getPropertyList() {
		return mElementListProperties;
	}
	
}
