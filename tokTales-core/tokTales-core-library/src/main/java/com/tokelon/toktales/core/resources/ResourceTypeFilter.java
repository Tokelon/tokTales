package com.tokelon.toktales.core.resources;

public enum ResourceTypeFilter {
	F_SPRITE(ResourceType.SPRITE_SET, ResourceType.SPRITE),
	;
	
	
	private final IResourceType[] filters;
	
	private ResourceTypeFilter(IResourceType... filters) {
		this.filters = filters;
	}
	
	
	public boolean contains(ResourceType resourceType) {
		for(IResourceType t: filters) {
			if(t == resourceType) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean applies(IResourceType type) {
		return applies(type.getIdentifier());
	}
	
	public boolean applies(String typeID) {
		for(IResourceType f: filters) {
			if(f.getIdentifier().equals(typeID)) {
				return true;
			}
		}
		
		return false;
	}
	
}