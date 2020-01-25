package com.tokelon.toktales.core.resources;

import com.tokelon.toktales.core.values.ResourceTypeValues;

public enum ResourceType implements IResourceType {


	UNKNOWN(null), // This will not store the custom ids though
	NONE(""),
	
	SPRITE(ResourceTypeValues.RESOURCE_TYPE_SPRITE),
	SPRITE_SET(ResourceTypeValues.RESOURCE_TYPE_SPRITESET),
	;
	
	
	private final String identifier;
	
	private ResourceType(String identifier) {
		this.identifier = identifier;
	}
	
	
	@Override
	public String getIdentifier() {
		return identifier;
	}
	
	
	public ResourceType getForIdentifier(String id) {
		for(ResourceType t: values()) {
			if(t.identifier.equals(id)) {
				return t;
			}
		}
		
		return UNKNOWN;
	}
	
}