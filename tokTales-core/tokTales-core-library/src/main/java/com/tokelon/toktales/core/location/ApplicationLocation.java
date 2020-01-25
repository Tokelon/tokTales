package com.tokelon.toktales.core.location;

public class ApplicationLocation implements IApplicationLocation {


	private final ILocationPath path;
	
	public ApplicationLocation(String path) {
		this.path = new MutableLocationPath(path);
	}
	
	public ApplicationLocation(ILocationPath path) {
		this.path = path;
	}
	
	
	@Override
	public ILocationPath getLocationPath() {
		return path;
	}

}
