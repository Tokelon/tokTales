package com.tokelon.toktales.core.content.manage.contents;

import com.tokelon.toktales.core.location.IApplicationLocation;

public class ContentKey implements IContentKey {


	private final String name;
	private final IApplicationLocation location;

	public ContentKey(String name, IApplicationLocation location) {
		this.name = name;
		this.location = location;
	}
	
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public IApplicationLocation getLocation() {
		return location;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof IContentKey)) {
			return false;
		}
		IContentKey other = (IContentKey) obj;
		
		if (location == null) {
			if (other.getLocation() != null) {
				return false;
			}
		} else if (!location.equals(other.getLocation())) {
			return false;
		}
		
		if (name == null) {
			if (other.getName() != null) {
				return false;
			}
		} else if (!name.equals(other.getName())) {
			return false;
		}
		
		return true;
	}

}
