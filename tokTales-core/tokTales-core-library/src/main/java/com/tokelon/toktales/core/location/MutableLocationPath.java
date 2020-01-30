package com.tokelon.toktales.core.location;

import java.io.File;

/** Reference implementation for {@link IMutableLocationPath}.
 */
public class MutableLocationPath implements IMutableLocationPath {


	private final String fileSep;
	
	private String currentLocation;

	
	public MutableLocationPath() {
		this.fileSep = File.separator;
		this.currentLocation = "";
	}
	
	
	public MutableLocationPath(ILocationPath initialPath) {
		this(initialPath, File.separator);
	}
	
	public MutableLocationPath(ILocationPath initialPath, String fileSeparator) {
		if(fileSeparator == null) {
			throw new NullPointerException();
		}
		
		this.fileSep = fileSeparator;
		set(initialPath);
	}
	
	
	public MutableLocationPath(String initialValue) {
		this(initialValue, File.separator);
	}
	
	public MutableLocationPath(String initialValue, String fileSeparator) {
		if(fileSeparator == null) {
			throw new NullPointerException();
		}

		this.fileSep = fileSeparator;
		set(initialValue);
	}

	
	
	private String removeLastSeparatorIfExists(String val) {
		if(val.endsWith(fileSep)) {
			return val.substring(0, val.length() - fileSep.length());
		}
		else {
			return val;
		}
	}

	
	@Override
	public MutableLocationPath set(ILocationPath path) {
		if(path == null) {
			throw new NullPointerException();
		}
		
		currentLocation = removeLastSeparatorIfExists(path.getLocation());
		return this;
	}

	@Override
	public MutableLocationPath set(String value) {
		if(value == null) {
			throw new NullPointerException();
		}
		
		currentLocation = removeLastSeparatorIfExists(value);
		return this;
	}

	@Override
	public MutableLocationPath setToChild(ILocationPath path) {
		if(path == null) {
			throw new NullPointerException();
		}
		
		set(getPath() + path.getLocation());
		return this;
	}

	@Override
	public MutableLocationPath setToChild(String value) {
		if(value == null) {
			throw new NullPointerException();
		}
		
		set(getPath() + value);
		return this;
	}

	@Override
	public MutableLocationPath setToParent() {
		currentLocation = getParentLocation();
		return this;
	}
	

	
	@Override
	public String getPath() {
		return currentLocation + fileSep;
	}

	@Override
	public String getLocation() {
		return currentLocation;
	}

	@Override
	public String getLocationName() {
		int lastIndexOfFileSep = currentLocation.lastIndexOf(fileSep);
		if(lastIndexOfFileSep == -1) {
			return currentLocation;
		}
		else {
			return currentLocation.substring(lastIndexOfFileSep + fileSep.length());
		}
	}
	
	
	@Override
	public String getChildPath(String value) {
		return getChildLocation(value) + fileSep;
	}

	@Override
	public String getChildLocation(String value) {
		return getPath() + removeLastSeparatorIfExists(value);
	}
	
	@Override
	public String getParentPath() {
		return getParentLocation() + fileSep;
	}
	
	@Override
	public String getParentLocation() {
		int lastIndexOfFileSep = currentLocation.lastIndexOf(fileSep);
		if(lastIndexOfFileSep == -1) {
			return currentLocation;
		}
		else {
			return currentLocation.substring(0, lastIndexOfFileSep);
		}
	}
	

	@Override
	public ILocationPath getChild(ILocationPath path) {
		return new MutableLocationPath(currentLocation).setToChild(path);
	}

	@Override
	public ILocationPath getChild(String value) {
		return new MutableLocationPath(currentLocation).setToChild(value);
	}

	@Override
	public ILocationPath getParent() {
		return new MutableLocationPath(currentLocation).setToParent();
	}
	
}
