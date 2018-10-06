package com.tokelon.toktales.core.storage.utils;

import com.tokelon.toktales.core.storage.IApplicationLocation;

public class DynamicApplicationLocation implements IApplicationLocation {

	private final MutablePathImpl mutablePath = new MutablePathImpl();
	
	public DynamicApplicationLocation() {
		// Nothing
	}
	
	public DynamicApplicationLocation(IConformedPath initialPath) {
		mutablePath.setPath(initialPath);
	}
	

	public IMutablePath editLocationPath() {
		return mutablePath;
	}
	
	@Override
	public IConformedPath getLocationPath() {
		return mutablePath;
	}

}
