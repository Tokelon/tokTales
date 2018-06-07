package com.tokelon.toktales.core.util.manage;

/** Curates a number of named objects.
 * 
 * @param <T> The type of objects.
 * 
 * @see ISealedObjectManager
 */
public interface IObjectCurator<T> extends ISealedObjectManager<T> {

	
	/** Curates a number of named objects and enables modification.
	 * 
	 * @param <T> The type of objects.
	 * 
	 * @see IObjectManager
	 */
	public interface IOpenObjectCurator<T> extends ISealedObjectManager<T>, IObjectManager<T> {
		
	}
	
}
