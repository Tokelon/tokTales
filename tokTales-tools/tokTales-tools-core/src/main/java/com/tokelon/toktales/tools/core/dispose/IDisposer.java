package com.tokelon.toktales.tools.core.dispose;

import com.tokelon.toktales.tools.core.annotations.compatibility.CompatFunctionalInterface;

@CompatFunctionalInterface
public interface IDisposer<T> {

	
	/** Disposes of managed resources in the given object.
	 * 
	 * @param object
	 */
	public void dispose(T object);
	
}
