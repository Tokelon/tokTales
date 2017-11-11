package com.tokelon.toktales.core.resources;

public enum ResourceTypeFilter {
	F_SPRITE(IResourceType.Type.SPRITE_SET, IResourceType.Type.SPRITE),
	
	;
	
	
	private final IResourceType[] filters;
	
	private ResourceTypeFilter(IResourceType... filters) {
		this.filters = filters;
	}
	
	
	public boolean contains(IResourceType.Type type) {
		for(IResourceType t: filters) {
			if(t == type) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean applies(IResourceType type) {
		return applies(type.getTypeID());
	}
	
	public boolean applies(String typeID) {
		for(IResourceType f: filters) {
			if(f.getTypeID().equals(typeID)) {
				return true;
			}
		}
		
		return false;
	}
	
}