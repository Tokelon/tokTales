package com.tokelon.toktales.core.util;

public interface ICopyable<T extends ICopyable<T>> {

	
	/** Creates a copy of this object.
	 * 
	 * @return A new object with all the properties of this object.
	 */
	public T copy();
	
}
