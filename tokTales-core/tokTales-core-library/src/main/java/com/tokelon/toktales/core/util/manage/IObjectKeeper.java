package com.tokelon.toktales.core.util.manage;

/** Keeps a number of objects.
 *
 * @param <T> The type of objects.
 * 
 * @see ISealedObjectHolder
 */
public interface IObjectKeeper<T> extends ISealedObjectHolder<T> {

	
	/** Keeps a number of objects and enables modification.
	 *
	 * @param <T> The type of objects.
	 * 
	 * @see IObjectHolder
	 */
	public interface IOpenObjectKeeper<T> extends IObjectKeeper<T>, IObjectHolder<T> {

	}
	
}
