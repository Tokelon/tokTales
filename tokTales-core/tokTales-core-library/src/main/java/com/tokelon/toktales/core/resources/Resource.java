package com.tokelon.toktales.core.resources;

import com.tokelon.toktales.core.location.IUniformLocation;
import com.tokelon.toktales.core.location.LocationScheme;
import com.tokelon.toktales.core.location.UniformLocation;

public class Resource implements IResource {
	// Add ResourceBuilder?


	private final int hashcode;
	
	private final IResourceType type;
	private final String name;
	private final IUniformLocation location;
	
	public Resource(String name, String location, LocationScheme locationScheme) {
		this(name, new UniformLocation(locationScheme, location), ResourceType.UNKNOWN);
	}
	
	public Resource(String name, IUniformLocation location) {
		this(name, location, ResourceType.UNKNOWN);
	}

	public Resource(String name, IUniformLocation location, IResourceType type) {
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
	public IUniformLocation getLocation() {
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
		if(!(obj instanceof Resource)) { // TODO: Test against IResource?
			return false;
		}
		Resource r = (Resource)obj;
		
		if(name.equals(r.name) && location.equals(r.location) && type.equals(r.type)) {
			return true;
		}
		
		return false;
	}
	
}
