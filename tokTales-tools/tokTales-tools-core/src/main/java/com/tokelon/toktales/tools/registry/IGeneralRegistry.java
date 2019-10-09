package com.tokelon.toktales.tools.registry;

public interface IGeneralRegistry<K> extends IRegistry<K, Object> {


	/** Returns the result of {@link IRegistry#getValue(Object)} and attempts to cast it to the given type.
	 * 
	 * @param key
	 * @param as
	 * @return The result in the given type, or null if the result is not compatible with the given type.
	 */
	public <T> T getValueAs(K key, Class<T> as);
	
	/** Returns whether this registry contains a registry entry for the given key and it's value is compatible with the given type.
	 * <p>
	 * This operation does not consider aliases in any parent registries.
	 * 
	 * @param key
	 * @param as
	 * @return True if a registry entry exists and it's value is compatible with the given type, false if not.
	 */
	public boolean containsAs(K key, Class<?> as);
	
	/** Returns whether this or any parent registries contain a registry entry for the given key and it's value is compatible with the given type.
	 * 
	 * @param key
	 * @param as
	 * @return True if a registry entry was found and it's value is compatible with the given type, false if not.
	 */
	public boolean knowsAs(K key, Class<?> as);
	
	/** Returns the result of {@link IRegistry#follow(Object)} and attempts to cast it to the given type.
	 * 
	 * @param key
	 * @param as
	 * @return The result in the given type, or null if the result is not compatible with the given type.
	 */
	public <T> T followAs(K key, Class<T> as);
	
	/** Returns the result of {@link IRegistry#resolve(Object)} and attempts to cast it to the given type.
	 * 
	 * @param key
	 * @param as
	 * @return The result in the given type, or null if the result is not compatible with the given type.
	 */
	public <T> T resolveAs(K key, Class<T> as);
	
	
	@Override
	public IGeneralRegistry<K> copy();

	@Override
	public IGeneralRegistry<K> createChildRegistry();
	
}
