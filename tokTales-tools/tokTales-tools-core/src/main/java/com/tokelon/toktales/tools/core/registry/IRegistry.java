package com.tokelon.toktales.tools.core.registry;

import java.util.Map;

public interface IRegistry<K, V> {


	/** Adds a registry entry with the given key and value to this registry.
	 * <p>
	 * Any parent registries are not affected.
	 * 
	 * @param key
	 * @param value
	 * @return The previous value, or null if there was no entry for the given key.
	 * @throws NullPointerException If key is null.
	 */
	public V set(K key, V value);
	
	/** Adds all mappings from the given entries to this registry.
	 * <p>
	 * Any parent registries are not affected.
	 * 
	 * @param entries
	 */
	public void put(Map<K, V> entries);
	
	/** Returns the value for the given key from this registry.
	 * 
	 * @param key
	 * @return The value for the given key, or null if no such key exists.
	 */
	public V getValue(K key);
	
	/** Returns whether this registry contains a registry entry for the given key.
	 * <p>
	 * This operation does not consider aliases in any parent registries.
	 * 
	 * @param key
	 * @return True if a registry entry exists, false if not.
	 */
	public boolean contains(K key);
	
	/** Returns whether this registry contains a registry entry with the given value.
	 * <p>
	 * This operation does not consider aliases in any parent registries.
	 * 
	 * @param value
	 * @return True if a registry entry exists, false if not.
	 */
	public boolean containsValue(V value);

	/** Removes the registry entry for the given key.
	 * <p>
	 * Any parent registries are not affected.
	 * 
	 * @param key
	 * @return The previous value, or null if there was no entry for the given key.
	 */
	public V remove(K key);
	

	/** Returns whether this or any parent registries contain a registry entry for the given key.
	 * 
	 * @param key
	 * @return True if a registry entry could be found, false if not.
	 */
	public boolean knows(K key);
	
	/** Returns whether this or any parent registries contain a registry entry with the given value.
	 * 
	 * @param value
	 * @return True if a registry entry could be found, false if not.
	 */
	public boolean knowsValue(V value);
	
	
	/** Returns the value for the given key, after following any aliases in this registry.
	 * <p>
	 * This operation does not consider aliases in any parent registries.
	 * 
	 * @param key
	 * @return The value for the given key, or null if no registry entry could be found.
	 */
	public V follow(K key);

	/** Returns the value for the given key, after resolving any aliases in this or any parent registries.
	 * <p>
	 * Note that any parent registries will only be searched if, after following this registry's aliases, no registry entry can be found.
	 * 
	 * @param key
	 * @return The value for the given key, or null if no registry entry could be found.
	 */
	public V resolve(K key);
	
	
	/**
	 * @return An unmodifiable mapping of keys to values.
	 */
	public Map<K, V> getEntries();
	
	
	/** Sets the target for the given alias in this registry, overriding the previous target if needed.
	 * <p>
	 * Any parent registries are not affected.
	 * 
	 * @param alias
	 * @param target
	 * @return The previous target, or null if this is a new alias.
	 * @throws IllegalArgumentException If alias equals target.
	 * @throws NullPointerException If alias is null.
	 */
	public K setAlias(K alias, K target);
	
	/** Adds all mappings from the given aliases to this registry.
	 * <p>
	 * Any parent registries are not affected.
	 * 
	 * @param aliases
	 */
	public void putAliases(Map<K, K> aliases);
	
	/** Returns the target for the given alias from this registry.
	 * 
	 * @param alias
	 * @return The target for the alias, or null if no such alias exists.
	 */
	public K getAliasTarget(K alias);
	
	/** Returns whether this registry contains the given alias.
	 * <p>
	 * This operation does not consider aliases in any parent registries.
	 * 
	 * @param alias
	 * @return True if the alias exists, false if not.
	 */
	public boolean containsAlias(K alias);
	
	/** Returns whether this registry contains an alias with the given target.
	 * <p>
	 * This operation does not consider aliases in any parent registries.
	 * 
	 * @param target
	 * @return True if an alias exists, false if not.
	 */
	public boolean containsAliasTarget(K target);
	
	/** Removes the given alias from this registry.
	 * <p>
	 * Any parent registries are not affected.
	 * 
	 * @param alias
	 * @return The previous target for the alias, or null if no such alias existed.
	 */
	public K removeAlias(K alias);

	
	/** Returns whether this or any parent registries contain the given alias.
	 * 
	 * @param alias
	 * @return True if the alias was found, false if not.
	 */
	public boolean knowsAlias(K alias);
	
	/** Returns whether this or any parent registries contain an alias with the given target.
	 * 
	 * @param target
	 * @return True if an alias was found, false if not.
	 */
	public boolean knowsAliasTarget(K target);
	
	
	/** Following the aliases in this registry, starting from the given key, returns the first key for which no alias exists.
	 * <p>
	 * This operation does not consider aliases in any parent registries.
	 * 
	 * @param key
	 * @return The first key for which no alias exists, or key if there is no alias for it.
	 */
	public K followAlias(K key);
	
	/** Following the aliases in this and any parent registries, starting from the given key, returns the first key for which a registry entry exists.
	 * <p>
	 * Note that any parent registries will only be searched if, after following this registry's aliases, no registry entry can be found.
	 * 
	 * @param key
	 * @return The first key for which an entry exists, or key if there is no alias for it. 
	 */
	public K resolveAlias(K key);
	
	
	/**
	 * @return An unmodifiable mapping of aliases to targets.
	 */
	public Map<K, K> getAliases();

	
	/** Returns whether this registry has a parent or not.
	 * 
	 * @return True if this registry has a parent, false if not.
	 */
	public boolean hasParent();
	
	/**
	 * @return The parent for this registry, or null if there is none.
	 */
	public IRegistry<K, V> getParent();
	

	/** Creates a new registry that contains all entries and aliases and has the same parent as this registry. 
	 * 
	 * @return A copy of this registry.
	 */
	public IRegistry<K, V> copy();
	
	/** Creates a new registry that has this registry as parent.
	 * 
	 * @return A child registry of this registry.
	 */
	public IRegistry<K, V> createChildRegistry();
	
}
