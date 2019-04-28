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

}
