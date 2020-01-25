package com.tokelon.toktales.core.location;

public class UniformLocation implements IUniformLocation {


	public static final String DEFAULT_SCHEME_DELIMITER = "://";

	
	private final String originalValue;
	
	private final ILocationScheme scheme;
	private final String schemeIdentifier;
	
	private final String schemeDelimiter;
	
	private final MutableLocationPath locationPath = new MutableLocationPath();

	
	public UniformLocation(String value) {
		this(value, DEFAULT_SCHEME_DELIMITER);
	}
	
	public UniformLocation(String value, String schemeDelimiter) {
		// Maybe use URI?
		if(value == null || schemeDelimiter == null) {
			throw new NullPointerException();
		}
		
		
		this.originalValue = value;
		this.schemeDelimiter = schemeDelimiter;
		
		int locPrefixPos = value.indexOf(schemeDelimiter);
		if(locPrefixPos != -1) {
			schemeIdentifier = value.substring(0, locPrefixPos);
			locationPath.set(value.substring(locPrefixPos + schemeDelimiter.length()));
		}
		else {
			schemeIdentifier = "";
			locationPath.set(value);
		}
		
		scheme = LocationScheme.getForIdentifier(schemeIdentifier);
	}
	
	
	
	public UniformLocation(ILocationScheme scheme, String location) {
		this(scheme, location, DEFAULT_SCHEME_DELIMITER);
	}
	
	public UniformLocation(ILocationScheme scheme, String location, String schemeDelimiter) {
		if(scheme == null || location == null || schemeDelimiter == null) {
			throw new NullPointerException();
		}
	
		this.scheme = scheme;
		this.schemeIdentifier = scheme.getIdentifier();
		this.schemeDelimiter = schemeDelimiter;
		
		locationPath.set(location);
		this.originalValue = schemeIdentifier + schemeDelimiter + location;
	}
	
	

	@Override
	public ILocationScheme getScheme() {
		return scheme;
	}

	@Override
	public String getSchemeIdentifier() {
		return schemeIdentifier;
	}

	@Override
	public String getOriginalValue() {
		return originalValue;
	}

	@Override
	public String getSchemeDelimiter() {
		return schemeDelimiter;
	}

	@Override
	public ILocationPath getLocationPath() {
		return locationPath;
	}	
	
}
