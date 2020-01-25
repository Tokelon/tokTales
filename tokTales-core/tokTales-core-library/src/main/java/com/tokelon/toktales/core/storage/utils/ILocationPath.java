package com.tokelon.toktales.core.storage.utils;

/* A path to a location (not a datafile), on a traditional filesystem.
 * It is guaranteed that the path being returned by a method conforms to certain syntactic rules, as denoted by the method documentation. 
 * e.g. getPath() will return the path value ending WITH a delimiter character. 
 *
 * A traditional filesystem is considered a tree structure in which 
 * location paths are directories separated by a delimiter character or file-separator, most often being back-slash or forward-slash.
 */
public interface ILocationPath {
	// TODO: Refactor this interface


	/**
	 * @return The path with a trailing separator.
	 */
	public String getPath();
	
	/**
	 * @return The path without a trailing separator.
	 */
	public String getLocation();
	
	/**
	 * @return The last directory on this path.
	 */
	public String getLocationName();
	

	public String getPathAppendedBy(String value);
	
	public String getLocationAppendedBy(String value);

	public ILocationPath newPathByAppend(ILocationPath path);
	
	public ILocationPath newPathByAppend(String value);
	
	public ILocationPath newPathByParent();
	
}
