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


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((resourceName == null) ? 0 : resourceName.hashCode());
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
		if (!(obj instanceof IResourceScannerKey)) {
			return false;
		}
		IResourceScannerKey other = (IResourceScannerKey) obj;
		
		if (resourceName == null) {
			if (other.getResourceName() != null) {
				return false;
			}
		} else if (!resourceName.equals(other.getResourceName())) {
			return false;
		}
		
		return true;
	}

}
