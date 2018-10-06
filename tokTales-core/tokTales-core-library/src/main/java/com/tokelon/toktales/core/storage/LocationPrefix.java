package com.tokelon.toktales.core.storage;

import com.tokelon.toktales.core.values.LocationPrefixValues;

public enum LocationPrefix {
	
	CUSTOM(null),
	NONE(""),
	
	INTERNAL(LocationPrefixValues.PREFIX_LOCATION_INTERNAL),
	EXTERNAL(LocationPrefixValues.PREFIX_LOCATION_EXTERNAL),
	ASSET(LocationPrefixValues.PREFIX_LOCATION_ASSET),
	
	INT_SPRITES(LocationPrefixValues.PREFIX_LOCATION_INT_SPRITES),
	;
	
	
	private final String prefixID;
	
	private LocationPrefix(String prefixID) {
		this.prefixID = prefixID;
	}
	
	public String getPrefixID() {
		return prefixID;
	}
	
	
	public static LocationPrefix prefixFromID(String id) {
		if(id == null) {
			throw new NullPointerException();
		}
		
		for(LocationPrefix p: values()) {
			if(id.equals(p.prefixID)) {
				return p;
			}
		}
		
		return CUSTOM;
	}
}