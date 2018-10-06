package com.tokelon.toktales.core.util.function;

import com.tokelon.toktales.core.prog.annotation.CustomFunctionalInterface;

/**
 * Represents a supplier of results.
 * <p>
 * There is no requirement that a new or distinct result be returned each time the supplier is invoked. 
 *
 * @param <T> The type of results.
 */
@CustomFunctionalInterface
public interface Supplier<T> {

	
	/** Gets a result.
	 * 
	 * @return A result.
	 */
	public T get();
	
}
