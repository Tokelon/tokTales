package com.tokelon.toktales.tools.inject;

/** Helper class for creating {@link InjectParameters} instances.
 * 
 */
public class InjectParams {
	
	private InjectParams() { }
	
	
	
	/** Creates a new {@link InjectParameters} instance.
	 * 
	 * @param key
	 * @return A new instance for the given key.
	 */
	public static InjectParameters forKey(Class<?> key) {
		return new InjectParametersImpl(key);
	}

}
