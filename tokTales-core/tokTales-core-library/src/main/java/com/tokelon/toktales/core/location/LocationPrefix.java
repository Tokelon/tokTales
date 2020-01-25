package com.tokelon.toktales.core.location;

import com.tokelon.toktales.core.values.LocationPrefixValues;

public enum LocationPrefix {


	UNKNOWN(null),
	NONE(""),
	
	STORAGE(LocationPrefixValues.PREFIX_LOCATION_STORAGE),
	CONTENT(LocationPrefixValues.PREFIX_LOCATION_CONTENT),
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
		
		return UNKNOWN;
	}
	
}