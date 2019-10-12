package com.tokelon.toktales.tools.core.inject;

public interface IProvider<T> {


	/** Provides an instance of {@code T}.
	 * 
	 * @return
	 * @throws InjectionException If provision fails.
	 */
	T get();

}
