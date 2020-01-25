package com.tokelon.toktales.core.location;

/* A path to a location (not a datafile), on a traditional filesystem.
 * It is guaranteed that the path being returned by a method conforms to certain syntactic rules, as denoted by the method documentation. 
 * e.g. getPath() will return the path value ending WITH a delimiter character. 
 *
 * A traditional filesystem is considered a tree structure in which 
 * location paths are directories separated by a delimiter character or file-separator, most often being back-slash or forward-slash.
 */
public interface ILocationPath {


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
	

	public String getChildPath(String value);
	
	public String getChildLocation(String value);
	
	public String getParentPath();
	
	public String getParentLocation();
	

	public ILocationPath getChild(ILocationPath path);
	
	public ILocationPath getChild(String value);
	
	public ILocationPath getParent();
	
}
