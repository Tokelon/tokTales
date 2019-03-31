package com.tokelon.toktales.core.util.manage;

/** Coordinates a number of keyed objects.
*
* @param <T> The type of objects.
* @param <K> The type of keys.
* 
* @see ISealedObjectOrganizer
*/
public interface IObjectCoordinator<K, T> extends ISealedObjectOrganizer<K, T> {

	
	/** Coordinates a number of keyed objects and enables modification.
	 *
	 * @param <T> The type of objects.
	 * @param <K> The type of keys.
	 * 
	 * @see IObjectOrganizer
	 */
	public interface IOpenObjectCoordinator<K, T> extends IObjectCoordinator<K, T>, IObjectOrganizer<K, T> {
		
	}
	
}
