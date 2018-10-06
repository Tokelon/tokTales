package com.tokelon.toktales.core.resources;

import com.tokelon.toktales.core.storage.IStructuredLocation;
import com.tokelon.toktales.core.storage.utils.StructuredLocation;



public class Resource implements IResource {
	
	
	private final int hashcode;
	
	private final IResourceType type;
	private final String name;
	private final IStructuredLocation location;
	
	
	public Resource(IResourceType type, String name, IStructuredLocation location) {
		if(type == null || name == null || location == null) {
			throw new NullPointerException();
		}
		
		this.type = type;
		this.name = name;
		this.location = location;
		
		hashcode = 37 + 17*name.hashCode() + 17*location.hashCode() + 17*type.hashCode();
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
	public boolean equals(Object obj) {		// TODO: Test equals
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
	
	
	
	public static class ResourceFactory {
		
		private String rName;
		private IResourceType rType;
		private IStructuredLocation rLocation;
		
		public Resource build() {
			return new Resource(rType, rName, rLocation);
		}
		
		public ResourceFactory setName(String name) {
			rName = name;
			return this;
		}
	
		public ResourceFactory setType(IResourceType resourceType) {
			rType = resourceType;
			return this;
		}
		public ResourceFactory setType(String type) {
			rType = new ResourceType(type);
			return this;
		}
		
		public ResourceFactory setLocation(IStructuredLocation structuredLocation) {
			rLocation = structuredLocation;
			return this;
		}
		
		public ResourceFactory setStructuredLocation(String location) {
			rLocation = new StructuredLocation(location);
			return this;
		}
		
	}
	
	
	private static class ResourceType implements IResourceType {

		private final String id;
		
		public ResourceType(String id) {
			this.id = id;
		}
		
		@Override
		public String getTypeID() {
			return id;
		}
		
		
		@Override
		public int hashCode() {
			return id.hashCode();
		}
		
		@Override
		public boolean equals(Object obj) {
			if(obj == this) {
				return true;
			}
			if(!(obj instanceof IResourceType)) {	// TODO: Check if using the interface is correct
				return false;
			}
			IResourceType rt = (IResourceType)obj;
			
			return id.equals(rt.getTypeID());
		}
		
	}
	
}
