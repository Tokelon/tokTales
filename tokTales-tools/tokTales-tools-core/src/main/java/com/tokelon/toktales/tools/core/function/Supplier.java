package com.tokelon.toktales.tools.core.function;

/** Represents a supplier of results.
 * <p>
 * There is no requirement that a new or distinct result be returned each time the supplier is invoked. 
 *
 * @param <T> The type of results.
 */
public interface Supplier<T> {

	
	/** Gets a result.
	 * 
	 * @return A result.
	 */
	public T get();
	
}
