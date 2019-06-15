package com.tokelon.toktales.tools.registry;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Registry<K, V> implements IRegistry<K, V> {
	/* 
	 * 1. Can an object be both a key and an alias? -> Yes
	 * 2. Does an alias override a key? When an object is both a key and an alias? -> Yes
	 * 3. Can you have an alias for an alias? If yes - what is the max level? -> Yes, unlimited
	 * 4. If you can have all that - how do we deal with circle references? -> We don't
	 */

	private final Map<K, V> registryEntries;
	private final Map<K, V> unmodifiableRegistryEntries;
	
	private final Map<K, K> registryAliases;
	private final Map<K, K> unmodifiableRegistryAliases;
	
	private final IRegistry<K, V> parent;
	
	public Registry() {
		this.parent = null;
		
		this.registryEntries = createEntries(null);
		this.unmodifiableRegistryEntries = Collections.unmodifiableMap(registryEntries);
		this.registryAliases = createAliases(null);
		this.unmodifiableRegistryAliases = Collections.unmodifiableMap(registryAliases);
	}

	public Registry(IRegistry<K, V> parent) {
		this.parent = parent;
		
		this.registryEntries = createEntries(null);
		this.unmodifiableRegistryEntries = Collections.unmodifiableMap(registryEntries);
		this.registryAliases = createAliases(null);
		this.unmodifiableRegistryAliases = Collections.unmodifiableMap(registryAliases);
	}
	
	public Registry(Map<K, V> backingEntries, Map<K, K> backingAliases) {
		this.parent = null;
		
		this.registryEntries = backingEntries;
		this.unmodifiableRegistryEntries = Collections.unmodifiableMap(registryEntries);
		this.registryAliases = backingAliases;
		this.unmodifiableRegistryAliases = Collections.unmodifiableMap(registryAliases);
	}
	
	public Registry(IRegistry<K, V> parent, Map<K, V> backingEntries, Map<K, K> backingAliases) {
		this.parent = parent;
		
		this.registryEntries = backingEntries;
		this.unmodifiableRegistryEntries = Collections.unmodifiableMap(registryEntries);
		this.registryAliases = backingAliases;
		this.unmodifiableRegistryAliases = Collections.unmodifiableMap(registryAliases);
	}

	
	/** Creates a new map of entries and adds initialEntries if not null.
	 * 
	 * @param initialEntries The initial entries or null.
	 * @return A new map of entries.
	 */
	protected Map<K, V> createEntries(Map<K, V> initialEntries) {
		if(initialEntries == null) {
			return new HashMap<>();
		}
		else {
			return new HashMap<>(initialEntries);
		}
	}
	
	/** Creates a new map of aliases and adds initialAliases if not null.
	 * 
	 * @param initialAliases The initial aliases or null.
	 * @return A new map of aliases.
	 */
	protected Map<K, K> createAliases(Map<K, K> initialAliases) {
		if(initialAliases == null) {
			return new HashMap<>();
		}
		else {
			return new HashMap<>(initialAliases);
		}
	}
	
	
	/**
	 * @return The backing entries map.
	 */
	protected Map<K, V> getBackingEntries() {
		return registryEntries;
	}

	/**
	 * @return The backing aliases map.
	 */
	protected Map<K, K> getBackingAliases() {
		return registryAliases;
	}
	
	
	@Override
	public V set(K key, V value) {
		if(key == null) {
			throw new NullPointerException("key must not be null");
		}
		
		return getBackingEntries().put(key, value);
	}
	
	@Override
	public void put(Map<K, V> entries) {
		getBackingEntries().putAll(entries);
	}

	@Override
	public V getValue(K key) {
		return getBackingEntries().get(key);
	}
	
	@Override
	public boolean contains(K key) {
		return getBackingEntries().containsKey(key);
	}
	
	@Override
	public boolean containsValue(V value) {
		return getBackingEntries().containsValue(value);
	}

	@Override
	public V remove(K key) {
		return getBackingEntries().remove(key);
	}


	@Override
	public boolean knows(K key) {
		boolean result = getBackingEntries().containsKey(key);
		
		if(!result && hasParent()) {
			result = getParent().knows(key);
		}
		
		return result;
	}
	
	@Override
	public boolean knowsValue(V value) {
		boolean result = getBackingEntries().containsValue(value);
		
		if(!result && hasParent()) {
			result = getParent().knowsValue(value);
		}
		
		return result;
	}
	
	
	@Override
	public V follow(K key) {
		K targetKey = followAlias(key);
		return targetKey == null ? null : getBackingEntries().get(targetKey);
	}

	@Override
	public V resolve(K key) {
		K targetKey = followAlias(key);
		boolean hasResult = getBackingEntries().containsKey(targetKey);
		
		if(!hasResult && hasParent()) {
			return getParent().resolve(targetKey);
		}

		return targetKey == null ? null : getBackingEntries().get(targetKey);
	}
	
	
	@Override
	public Map<K, V> getEntries() {
		return unmodifiableRegistryEntries;
	}

	
	@Override
	public K setAlias(K alias, K target) {
		if(alias == null) {
			throw new NullPointerException("alias must not be null");
		}
		if(alias.equals(target)) {
			throw new IllegalArgumentException("alias and target must not be equal");
		}
		
		return getBackingAliases().put(alias, target);
	}
	
	@Override
	public void putAliases(Map<K, K> aliases) {
		getBackingAliases().putAll(aliases);
	}

	@Override
	public K getAliasTarget(K alias) {
		K result = getBackingAliases().get(alias);
		
		if(result == null && hasParent()) {
			result = getParent().getAliasTarget(alias);
		}
		
		return result;
	}

	@Override
	public boolean containsAlias(K alias) {
		return getBackingAliases().containsKey(alias);
	}
	
	@Override
	public boolean containsAliasTarget(K target) {
		return getBackingAliases().containsValue(target);
	}
	
	@Override
	public K removeAlias(K alias) {
		return getBackingAliases().remove(alias);
	}


	@Override
	public boolean knowsAlias(K alias) {
		boolean result = getBackingAliases().containsKey(alias);
		
		if(!result && hasParent()) {
			result = getParent().knowsAlias(alias);
		}
		
		return result;
	}

	@Override
	public boolean knowsAliasTarget(K target) {
		boolean result = getBackingAliases().containsValue(target);
		
		if(!result && hasParent()) {
			result = getParent().knowsAliasTarget(target);
		}
		
		return result;
	}
	

	@Override
	public K followAlias(K key) {
		K currentAlias = key;
		K nextAlias;
		while((nextAlias = getBackingAliases().get(currentAlias)) != null) {
			currentAlias = nextAlias;
		}
		
		return currentAlias;
	}
	
	@Override
	public K resolveAlias(K key) {
		K targetKey = followAlias(key);
		boolean hasResult = getBackingEntries().containsKey(targetKey);
		
		if(!hasResult && hasParent()) {
			targetKey = getParent().resolveAlias(targetKey);
		}
		
		return targetKey;
	}
	
	
	@Override
	public Map<K, K> getAliases() {
		return unmodifiableRegistryAliases;
	}
	

	@Override
	public boolean hasParent() {
		return parent != null;
	}
	
	@Override
	public IRegistry<K, V> getParent() {
		return parent;
	}
	

	@Override
	public IRegistry<K, V> copy() {
		return new Registry<>(getParent(), createEntries(getEntries()), createAliases(getAliases()));
	}

	@Override
	public IRegistry<K, V> createChildRegistry() {
		return new Registry<>(this);
	}

}
