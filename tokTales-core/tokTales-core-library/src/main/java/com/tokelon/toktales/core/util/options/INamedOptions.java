package com.tokelon.toktales.core.util.options;

public interface INamedOptions {

	public void set(String name, Object option);
	

	public Object get(String name);
	
	/**
	 * 
	 * @param name
	 * @return
	 * @throws IllegalArgumentException If an option with the given name does not exist.
	 */
	public Object getOrError(String name);
	
	public <T> T getOrDefault(String name, T defaultValue);	// Rename to getOrDefault() ??
	
	/**
	 * 
	 * @param name
	 * @param as
	 * @return
	 * @throws ClassCastException If the option with name does not exist or is does not match the given class. 
	 */
	public <T> T getAs(String name, Class<T> as);
	
	
	public boolean has(String name);
	
	public void remove(String name);
	
}
