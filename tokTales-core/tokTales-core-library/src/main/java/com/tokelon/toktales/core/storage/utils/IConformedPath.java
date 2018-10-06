package com.tokelon.toktales.core.storage.utils;

/** A PATH to a LOCATION (not a datafile!), on a traditional filesystem (tree structure in which location paths are directories separated by a delimiter character or file-separator, most often being back-slash or forward-slash).<br><br>
 *  It is guaranteed that the path being returned by a method conforms to certain syntactic rules, as denoted by the method documentation. 
 * 	Ex. getPath() will return the path value ending WITH a delimiter character. 
 *
 */
public interface IConformedPath {

	// TODO: Refactor this interface
	// Maybe rename to ILocationPath, since it's only being used for locations (directories, not files)
	
	/**
	 * 
	 * @return The path WITH a trailing file separator (/ or \).
	 */
	public String getPath();
	
	/**
	 * 
	 * @return The path WITHOUT a trailing file separator (/ or \).
	 */
	public String getLocation();
	
	/**
	 * 
	 * @return The last directory of this location.
	 */
	public String getLastPlace();	// Rename to getLocationName()
	
}
