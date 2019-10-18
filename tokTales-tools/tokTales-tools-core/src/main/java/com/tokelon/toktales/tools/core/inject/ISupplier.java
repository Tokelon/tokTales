package com.tokelon.toktales.tools.core.inject;

/** Supplies an instance of {@code T}.<br>
 * Similar to {@link IProvider}, however indicates that the returned object will be a specific instance.
 * <p>
 * There is no requirement that a new or distinct result be returned each time the supplier is invoked. 
 *
 * @param <T> The type of result.
 */
public interface ISupplier<T> {


	/** Supplies a specific instance of {@code T}.
	 * 
	 * @return The result.
	 */
	public T get();
	
}
