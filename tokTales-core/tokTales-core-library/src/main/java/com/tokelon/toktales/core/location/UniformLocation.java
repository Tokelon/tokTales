package com.tokelon.toktales.core.location;

public class UniformLocation implements IUniformLocation {


	public static final String DEFAULT_PREFIX_DELIMITER = "://";

	
	private final String value;
	
	private final LocationPrefix prefix;
	private final String prefixValue;
	
	private final String prefixDelimiter;
	
	private final MutableLocationPath locationPath = new MutableLocationPath();
	

	
	public UniformLocation(String value) {
		this(value, DEFAULT_PREFIX_DELIMITER);
	}
	
	public UniformLocation(String value, String prefixDelimiter) {
		// Maybe use URI?
		if(value == null || prefixDelimiter == null) {
			throw new NullPointerException();
		}
		
		
		this.value = value;
		this.prefixDelimiter = prefixDelimiter;
		
		int locPrefixPos = value.indexOf(prefixDelimiter);
		if(locPrefixPos != -1) {
			prefixValue = value.substring(0, locPrefixPos);
			locationPath.set(value.substring(locPrefixPos + prefixDelimiter.length()));
		}
		else {
			prefixValue = "";
			locationPath.set(value);
		}
		
		prefix = LocationPrefix.prefixFromID(prefixValue);
	}
	
	
	
	public UniformLocation(LocationPrefix prefix, String location) {
		this(prefix, location, DEFAULT_PREFIX_DELIMITER);
	}
	
	public UniformLocation(LocationPrefix prefix, String location, String prefixDelimiter) {
		if(prefix == null || location == null || prefixDelimiter == null) {
			throw new NullPointerException();
		}
	
		this.prefix = prefix;
		this.prefixValue = prefix.getPrefixID();
		this.prefixDelimiter = prefixDelimiter;
		
		locationPath.set(location);
		this.value = prefixValue + prefixDelimiter + location;
	}
	
	

	@Override
	public LocationPrefix getPrefix() {
		return prefix;
	}

	@Override
	public String getPrefixValue() {
		return prefixValue;
	}

	@Override
	public String getOriginalValue() {
		return value;
	}

	@Override
	public String getPrefixDelimiter() {
		return prefixDelimiter;
	}

	@Override
	public ILocationPath getLocationPath() {
		return locationPath;
	}	
	
}
