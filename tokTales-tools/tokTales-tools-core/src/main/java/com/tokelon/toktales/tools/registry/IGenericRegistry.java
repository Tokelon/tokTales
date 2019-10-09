package com.tokelon.toktales.tools.registry;

public interface IGenericRegistry<V> extends IRegistry<Object, V> {


	/** Returns the result of {@link IRegistry#getAliasTarget(Object)} and attempts to cast it to the given type.
	 * 
	 * @param alias
	 * @param as
	 * @return The result in the given type, or null if the result is not compatible with the given type.
	 */
	public <T> T getAliasTargetAs(Object alias, Class<T> as);
	
	/** Returns whether this registry contains the given alias and it's target is compatible with the given type.
	 * <p>
	 * This operation does not consider aliases in any parent registries.
	 * 
	 * @param alias
	 * @param as
	 * @return True if the alias exists and it's target is compatible with the given type, false if not.
	 */
	public boolean containsAliasAs(Object alias, Class<?> as);

	/** Returns whether this or any parent registries know the given alias and it's target is compatible with the given type.
	 * 
	 * @param alias
	 * @param as
	 * @return True if the alias was found and it's target is compatible with the given type, false if not.
	 */
	public boolean knowsAliasAs(Object alias, Class<?> as);
	
	/** Returns the result of {@link IRegistry#followAlias(Object)} and attempts to cast it to the given type.
	 * 
	 * @param key
	 * @param as
	 * @return The result in the given type, or null if the result is not compatible with the given type.
	 */
	public <T> T followAliasAs(Object key, Class<T> as);
	
	/** Returns the result of {@link IRegistry#resolveAlias(Object)} and attempts to cast it to the given type.
	 * 
	 * @param key
	 * @param as
	 * @return The result in the given type, or null if the result is not compatible with the given type.
	 */
	public <T> T resolveAliasAs(Object key, Class<T> as);
	
	
	@Override
	public IGenericRegistry<V> copy();
	
	@Override
	public IGenericRegistry<V> createChildRegistry();
	
}
