package com.tokelon.toktales.core.storage.utils;

import com.tokelon.toktales.core.storage.IApplicationLocation;

public class LocationImpl implements IApplicationLocation {

	
	private final MutablePathImpl mPath;
	
	public LocationImpl(String path) {
		mPath = new MutablePathImpl(path);
	}
	
	
	@Override
	public MutablePathImpl getLocationPath() {
		return mPath;
	}

}
