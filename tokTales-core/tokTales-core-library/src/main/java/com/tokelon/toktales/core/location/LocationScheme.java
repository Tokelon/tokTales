package com.tokelon.toktales.core.location;

import com.tokelon.toktales.core.values.LocationSchemeValues;

public enum LocationScheme implements ILocationScheme {


	UNKNOWN(null),
	NONE(""),
	
	STORAGE(LocationSchemeValues.SCHEME_IDENTIFIER_STORAGE),
	CONTENT(LocationSchemeValues.SCHEME_IDENTIFIER_CONTENT),
	;
	
	
	private final String identifier;
	
	private LocationScheme(String identifier) {
		this.identifier = identifier;
	}
	
	
	@Override
	public String getIdentifier() {
		return identifier;
	}
	
	
	public static LocationScheme getForIdentifier(String id) {
		if(id == null) {
			throw new NullPointerException();
		}
		
		for(LocationScheme p: values()) {
			if(id.equals(p.identifier)) {
				return p;
			}
		}
		
		return UNKNOWN;
	}
	
}