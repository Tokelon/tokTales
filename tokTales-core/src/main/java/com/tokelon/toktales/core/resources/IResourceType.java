package com.tokelon.toktales.core.resources;

import com.tokelon.toktales.core.values.ResourceTypeValues;

public interface IResourceType {

	public String getTypeID();
	
	
	public enum Type implements IResourceType {
		UNKNOWN(null),	// This will not store the custom id though
		
		SPRITE(ResourceTypeValues.RESOURCE_TYPE_SPRITE),
		SPRITE_SET(ResourceTypeValues.RESOURCE_TYPE_SPRITESET),
		
		;
		
		
		private final String typeID;
		
		private Type(String typeID) {
			this.typeID = typeID;
		}
		
		
		@Override
		public String getTypeID() {
			return typeID;
		}
		
		
		public Type typeFromID(String id) {
			for(Type t: values()) {
				if(t.typeID.equals(id)) {
					return t;
				}
			}
			
			return UNKNOWN;
		}
		
	}
	
}
