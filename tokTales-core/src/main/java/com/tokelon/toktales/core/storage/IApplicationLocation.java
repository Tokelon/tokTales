package com.tokelon.toktales.core.storage;

/** A relative location inside an application storage place (storage folder, assets etc.).
 * <br><br>
 * Ex. "./Maps/"
 */
public interface IApplicationLocation extends ILocation {

	
	/**
	 * 
	 * @return The path WITH a trailing file separator (/ or \).
	 */
	//public String getRelativePath();

	/**
	 * 
	 * @return The path WITHOUT a trailing file separator (/ or \).
	 */
	//public String getRelativeLocation();

	/**
	 * 
	 * @return The last place in the path.
	 */
	//public String getLastPlace();
	
	
	//public IApplicationLocation newLocationByAppend(String place);
	
	//public IApplicationLocation newLocationByCutLast();
	
	/**
	 * 
	 * @return The storage place where this location lies inside.
	 */
	//public IStoragePlace getStoragePlace();
}
