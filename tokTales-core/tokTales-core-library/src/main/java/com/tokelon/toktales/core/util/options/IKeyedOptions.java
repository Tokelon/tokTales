package com.tokelon.toktales.core.util.options;

public interface IKeyedOptions<K> {
	//public Map<K, Object> getOptionsMap(); // Add?
	
	/**
	 * @param key
	 * @return The option for the given key, or null if there is none.
	 */
	public Object get(K key);
	
	/**
	 * @param key
	 * @param as
	 * @return The option for the given key in the given type, or null if there is none or the option is incompatible with the type.
	 */
	public <T> T getAs(K key, Class<T> as);

	
	/**
	 * @param key
	 * @param defaultValue
	 * @return The option for the given key, or defaultValue if there is none.
	 */
	public Object getOrDefault(K key, Object defaultValue);
	
	/**
	 * @param key
	 * @param defaultValue
	 * @param as
	 * @return The option for the given key in the given type, or defaultValue if there is none or the option is incompatible to the type.
	 */
	public <T> T getAsOrDefault(K key, T defaultValue, Class<T> as);
	
	/**
	 * @param key
	 * @param defaultValue
	 * @return The option for the given key in the same type as defaultValue, or defaultValue if there is none or the option type is not exactly the same as defaultValue.
	 */
	public <T> T getAsExactOrDefault(K key, T defaultValue);

	
	/**
	 * @param name
	 * @return The option for the given key.
	 * @throws IllegalArgumentException If an option with the given name does not exist.
	 */
	public Object getOrError(K key);
	
	/**
	 * @param key
	 * @param as
	 * @return The option for the given key.
	 * @throws ClassCastException If the option with name does not exist or is does not match the given class.
	 */
	public <T> T getAsOrError(K key, Class<T> as);

	
	/**
	 * @return The number of options.
	 */
	public int count();
	
	
	/**
	 * @param key
	 * @return True if there is an option for the given key, false if not.
	 */
	public boolean has(K key);
	
	/**
	 * @param value
	 * @return True if there is an option for the given value, false if not.
	 */
	public boolean hasValue(Object value);

	
	/** Sets the option for the given key.
	 * 
	 * @param key
	 * @param option
	 * @return The old option for the given key, or null if there was none.
	 */
	public Object set(K key, Object option);
	
	/** Removes the option for the given key.
	 * 
	 * @param key
	 * @return The option that was removed, or null if there was none.
	 */
	public Object remove(K key);
	
	/** Removes all options.
	 */
	public void reset();

}
