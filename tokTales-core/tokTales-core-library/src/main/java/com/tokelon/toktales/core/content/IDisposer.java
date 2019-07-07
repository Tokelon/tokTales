package com.tokelon.toktales.core.content;

import java9.lang.FunctionalInterface;

@FunctionalInterface
public interface IDisposer<T> {

	
	/** Disposes of managed resources in the given object.
	 * 
	 * @param object
	 */
	public void dispose(T object);
	
}
