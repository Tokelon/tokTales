package com.tokelon.toktales.core.content.manage.resources;

public class ResourceScannerKey implements IResourceScannerKey {

	
	private final String resourceName;

	public ResourceScannerKey(String resourceName) {
		this.resourceName = resourceName;
	}
	
	
	@Override
	public String getResourceName() {
		return resourceName;
	}

}
