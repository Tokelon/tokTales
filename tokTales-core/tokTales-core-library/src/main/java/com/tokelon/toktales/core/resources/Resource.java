package com.tokelon.toktales.core.resources;

import com.tokelon.toktales.core.resources.IResourceType.Type;
import com.tokelon.toktales.core.storage.IStructuredLocation;
import com.tokelon.toktales.core.storage.LocationPrefix;
import com.tokelon.toktales.core.storage.utils.StructuredLocation;

public class Resource implements IResource {
	// Add ResourceBuilder?


	private final int hashcode;
	
	private final IResourceType type;
	private final String name;
	private final IStructuredLocation location;
	
	public Resource(String name, String location, LocationPrefix locationPrefix) {
		this(name, new StructuredLocation(locationPrefix, location), Type.UNKNOWN);
	}
	
	public Resource(String name, IStructuredLocation location) {
		this(name, location, Type.UNKNOWN);
	}

	public Resource(String name, IStructuredLocation location, IResourceType type) {
		if(type == null || name == null || location == null) {
			throw new NullPointerException();
		}
		
		this.type = type;
		this.name = name;
		this.location = location;
		
		hashcode = 37 + 17 * name.hashCode() + 17 * location.hashCode() + 17 * type.hashCode();
	}
	


	@Override
	public IResourceType getType() {
		return type;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public IStructuredLocation getLocation() {
		return location;
	}

	
	
	@Override
	public int hashCode() {
		return hashcode;
	}
	
	@Override
	public boolean equals(Object obj) { // TODO: Test equals
		if(obj == this) {
			return true;
		}
		if(!(obj instanceof Resource)) {
			return false;
		}
		Resource r = (Resource)obj;
		
		if(name.equals(r.name) && location.equals(r.location) && type.equals(r.type)) {
			return true;
		}
		
		return false;
	}
	
}
