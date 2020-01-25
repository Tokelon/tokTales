package com.tokelon.toktales.core.location;

import java.io.File;

/** Reference implementation for {@link IMutableLocationPath}.
 */
public class MutableLocationPath implements IMutableLocationPath {


	private final String fileSep;
	
	private String currentLocation;

	
	public MutableLocationPath() {
		this.fileSep = File.separator;
		this.currentLocation = ""; // TODO: Should this be the empty string or null ?
	}
	
	
	public MutableLocationPath(ILocationPath initialPath) {
		this(initialPath, File.separator);
	}
	
	public MutableLocationPath(ILocationPath initialPath, String fileSeparator) {
		if(fileSeparator == null || fileSeparator.isEmpty()) {
			throw new IllegalArgumentException("File separator cannot be null or empty: " + fileSeparator);
		}
		this.fileSep = fileSeparator;
		
		set(initialPath);
	}
	
	
	public MutableLocationPath(String initialValue) {
		this(initialValue, File.separator);
	}
	
	public MutableLocationPath(String initialValue, String fileSeparator) {
		if(fileSeparator == null || fileSeparator.isEmpty()) {
			throw new IllegalArgumentException("File separator cannot be null or empty: " + fileSeparator);
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
		currentLocation = path.getLocation();
		return this;
	}

	@Override
	public MutableLocationPath set(String value) {
		currentLocation = removeLastSeparatorIfExists(value);
		return this;
	}

	@Override
	public MutableLocationPath setToChild(ILocationPath path) {
		if(currentLocation == null) {
			currentLocation = path.getLocation();
		}
		else {
			currentLocation = currentLocation + fileSep + path.getLocation();
		}
		
		return this;
	}

	@Override
	public MutableLocationPath setToChild(String value) {
		if(currentLocation == null) {
			set(value);
		}
		else {
			set(currentLocation + fileSep + value);
		}
		
		return this;
	}

	@Override
	public MutableLocationPath setToParent() {
		currentLocation = currentLocation.substring(0, currentLocation.lastIndexOf(fileSep));
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
		return currentLocation.substring(currentLocation.lastIndexOf(fileSep) + fileSep.length());
	}
	
	
	@Override
	public String getChildPath(String value) {
		return getPath() + removeLastSeparatorIfExists(value) + fileSep;
	}

	@Override
	public String getChildLocation(String value) {
		return getPath() + removeLastSeparatorIfExists(value);
	}
	
	@Override
	public String getParentPath() {
		return currentLocation.substring(0, currentLocation.lastIndexOf(fileSep)) + fileSep;
	}
	
	@Override
	public String getParentLocation() {
		return currentLocation.substring(0, currentLocation.lastIndexOf(fileSep));
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
