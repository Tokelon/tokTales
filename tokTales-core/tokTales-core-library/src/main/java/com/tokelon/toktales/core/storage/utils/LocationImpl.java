package com.tokelon.toktales.core.storage.utils;

import com.tokelon.toktales.core.storage.IApplicationLocation;

public class LocationImpl implements IApplicationLocation {


	private final ILocationPath path;
	
	public LocationImpl(String path) {
		this.path = new MutablePathImpl(path);
	}
	
	public LocationImpl(ILocationPath path) {
		this.path = path;
	}
	
	
	@Override
	public ILocationPath getLocationPath() {
		return path;
	}

}
