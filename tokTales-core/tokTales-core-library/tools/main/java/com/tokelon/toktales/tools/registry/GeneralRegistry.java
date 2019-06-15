package com.tokelon.toktales.tools.registry;

import java.util.Map;

public class GeneralRegistry<K> extends Registry<K, Object> implements IGeneralRegistry<K> {


	public GeneralRegistry() {
		super();
	}
	
	public GeneralRegistry(IRegistry<K, Object> parent) {
		super(parent);
	}
	
	public GeneralRegistry(Map<K, Object> backingEntries, Map<K, K> backingAliases) {
		super(backingEntries, backingAliases);
	}
	
	public GeneralRegistry(IRegistry<K, Object> parent, Map<K, Object> backingEntries, Map<K, K> backingAliases) {
		super(parent, backingEntries, backingAliases);
	}
	
	
	@Override
	public <T> T getValueAs(K key, Class<T> as) {
		Object object = getValue(key);
		return as.isInstance(object) ? as.cast(object) : null;
	}

	@Override
	public boolean containsAs(K key, Class<?> as) {
		return as.isInstance(getValue(key));
	}

	@Override
	public boolean knowsAs(K key, Class<?> as) {
		return as.isInstance(resolve(key));
	}

	@Override
	public <T> T followAs(K key, Class<T> as) {
		Object object = follow(key);
		return as.isInstance(object) ? as.cast(object) : null;
	}

	@Override
	public <T> T resolveAs(K key, Class<T> as) {
		Object object = resolve(key);
		return as.isInstance(object) ? as.cast(object) : null;
	}
	
	
	@Override
	public IGeneralRegistry<K> copy() {
		return new GeneralRegistry<K>(getParent(), createEntries(getEntries()), createAliases(getAliases()));
	}
	
	@Override
	public IGeneralRegistry<K> createChildRegistry() {
		return new GeneralRegistry<>(this);
	}

}
