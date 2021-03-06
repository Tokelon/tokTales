package com.tokelon.toktales.core.location;

/* A location that has additional information attached to it, apart from the regular path, with a defined structure for string representation.<br><br>
 * This structure has a prefix (might be empty), a prefix delimiter (separator) and a location value.<br>
 * e.g. "prefix://path" where "://" the prefix delimiter, "prefix" the prefix value, "path" the path value.
 */
public interface IUniformLocation extends IApplicationLocation {


	/** 
	 * @return The prefix for this location.
	 */
	public ILocationScheme getScheme();
	
	/**
	 * @return The string value of this location's prefix.
	 */
	public String getSchemeIdentifier();
	
	/**
	 * @return The string representation of the complete location.
	 */
	public String getOriginalValue();
	
	/** 
	 * @return The string value of the this locations's prefix delimiters.
	 */
	public String getSchemeDelimiter();
	
}
