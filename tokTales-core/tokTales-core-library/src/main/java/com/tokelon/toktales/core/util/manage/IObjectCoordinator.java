package com.tokelon.toktales.core.util.manage;

/** Coordinates a number of keyed objects.
*
* @param <T> The type of objects.
* @param <K> The type of keys.
* 
* @see ISealedObjectOrganizer
*/
public interface IObjectCoordinator<T, K> extends ISealedObjectOrganizer<T, K> {

	
	/** Coordinates a number of keyed objects and enables modification.
	 *
	 * @param <T> The type of objects.
	 * @param <K> The type of keys.
	 * 
	 * @see IObjectOrganizer
	 */
	public interface IOpenObjectCoordinator<T, K> extends IObjectCoordinator<T, K>, IObjectOrganizer<T, K> {
		
	}
	
}
