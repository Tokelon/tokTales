package com.tokelon.toktales.core.content.manage.resources;

import com.tokelon.toktales.core.resources.IResource;

public class ResourceKey implements IResourceKey {

	
	private final IResource resource;

	public ResourceKey(IResource resource) {
		this.resource = resource;
	}

	
	@Override
	public IResource getResource() {
		return resource;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((resource == null) ? 0 : resource.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof IResourceKey)) {
			return false;
		}
		IResourceKey other = (IResourceKey) obj;
		
		if (resource == null) {
			if (other.getResource() != null) {
				return false;
			}
		} else if (!resource.equals(other.getResource())) {
			return false;
		}
		
		return true;
	}

}
