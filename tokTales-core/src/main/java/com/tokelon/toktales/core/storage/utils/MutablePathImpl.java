package com.tokelon.toktales.core.storage.utils;

/** Reference implementation for {@link IMutablePath}.
 * 
 */
public class MutablePathImpl implements IMutablePath {

	private final String fileSep;
	
	private String currentLocation;

	
	public MutablePathImpl() {
		this.fileSep = SUt.FSEP;
		this.currentLocation = "";		// TODO: Should this be the empty string or null ?
	}
	
	
	public MutablePathImpl(IConformedPath initialPath) {
		this(initialPath, SUt.FSEP);
	}
	
	public MutablePathImpl(IConformedPath initialPath, String fileSeparator) {
		if(fileSeparator == null || fileSeparator.isEmpty()) {
			throw new IllegalArgumentException("Bad file separator: " +fileSeparator);
		}
		this.fileSep = fileSeparator;
		
		setPath(initialPath);
	}
	
	
	public MutablePathImpl(String initialValue) {
		this(initialValue, SUt.FSEP);
	}
	
	public MutablePathImpl(String initialValue, String fileSeparator) {
		if(fileSeparator == null || fileSeparator.isEmpty()) {
			throw new IllegalArgumentException("Bad file separator: " +fileSeparator);
		}
		this.fileSep = fileSeparator;
		
		setPath(initialValue);
	}

	
	
	private String removeLastIfThere(String val) {
		if(val.endsWith(fileSep)) {
			return val.substring(0, val.length() - fileSep.length());
		}
		else {
			return val;
		}
	}

	
	@Override
	public MutablePathImpl setPath(IConformedPath path) {
		currentLocation = path.getLocation();
		return this;
	}

	@Override
	public MutablePathImpl setPath(String value) {
		currentLocation = removeLastIfThere(value);
		return this;
	}

	@Override
	public MutablePathImpl append(IConformedPath path) {
		if(currentLocation == null) {
			currentLocation = path.getLocation();
		}
		else {
			currentLocation = currentLocation + fileSep + path.getLocation();
		}
		
		return this;
	}

	@Override
	public MutablePathImpl append(String value) {
		if(currentLocation == null) {
			setPath(value);
		}
		else {
			setPath(currentLocation + fileSep + value);
		}
		
		return this;
	}

	@Override
	public MutablePathImpl cutLastPlace() {
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
	public String getLastPlace() {
		return currentLocation.substring(currentLocation.lastIndexOf(fileSep) + fileSep.length());
	}
	
	
	@Override
	public String getPathAppendedBy(String value) {
		return getPath() + removeLastIfThere(value) + fileSep;
	}

	@Override
	public String getLocationAppendedBy(String value) {
		return getPath() + removeLastIfThere(value);
	}

	@Override
	public IExtendedPath newPathByAppend(IConformedPath path) {
		return new MutablePathImpl(currentLocation).append(path);
	}

	@Override
	public IExtendedPath newPathByAppend(String value) {
		return new MutablePathImpl(currentLocation).append(value);
	}

	@Override
	public IExtendedPath newPathByCutLastPlace() {
		return new MutablePathImpl(currentLocation).cutLastPlace();
	}

	
}
