package com.tokelon.toktales.tools.core.inject;

import java.lang.reflect.Type;

public interface IInjector {


	/** Injects dependencies into the fields and methods the given instance.
	 *
	 * @param instance
	 */
	void injectMembers(Object instance);


	/** Returns an instance for the given type.
	 * 
	 * @param type
	 * @throws InjectionException if there is no provider for the given type or an runtime error occurs.
	 */
	<T> T getInstance(IKey<T> key);

	/** Returns an instance for the given class.
	 *
	 * @param type
	 * @throws InjectionException if there is no provider for the given class or an runtime error occurs.
	 */
	<T> T getInstance(Class<T> type);

	/** Returns an instance for the given type.
	 * 
	 * @param type
	 * @throws InjectionException if there is no provider for the given type or an runtime error occurs.
	 */
	Object getInstance(Type type);
	

	/** Returns a provider for the given key.
	 * 
	 * @throws InjectionException if this injector cannot find or create the provider.
	 */
	<T> IProvider<T> getProvider(IKey<T> key);
	
	/** Returns a provider for the given class.
	 * 
	 * @throws InjectionException if this injector cannot find or create the provider.
	 */
	<T> IProvider<T> getProvider(Class<T> type);

	/** Returns a provider for the given type.
	 * 
	 * @throws InjectionException if this injector cannot find or create the provider.
	 */
	IProvider<?> getProvider(Type type);
	
}
