package com.tokelon.toktales.tools.core.sub.inject.scope;

/** Helper class for creating For* (ex. {@link ForClass}) instances of various keys.
 * 
 */
public class For {

	private For() { }
	
	
	// Could extend for various keys
	//public static ForName forName(String name)
	//public static ForInstance forInstance(Object instance);
	
	
	/** Creates a new {@link ForClass} instance.
	 * 
	 * @param clazz
	 * @return A new instance for the given class.
	 */
	public static ForClass forClass(Class<?> clazz) {
		return new ForClassImpl(clazz);
	}

	
}
