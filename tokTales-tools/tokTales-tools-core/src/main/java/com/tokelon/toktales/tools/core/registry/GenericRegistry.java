package com.tokelon.toktales.tools.core.registry;

import java.util.Map;

public class GenericRegistry<V> extends Registry<Object, V> implements IGenericRegistry<V> {


	public GenericRegistry() {
		super();
	}

	public GenericRegistry(IRegistry<Object, V> parent) {
		super(parent);
	}
	
	public GenericRegistry(Map<Object, V> backingEntries, Map<Object, Object> backingAliases) {
		super(backingEntries, backingAliases);
	}

	public GenericRegistry(IRegistry<Object, V> parent, Map<Object, V> backingEntries, Map<Object, Object> backingAliases) {
		super(parent, backingEntries, backingAliases);
	}


	@Override
	public <T> T getAliasTargetAs(Object alias, Class<T> as) {
		Object object = getAliasTarget(alias);
		return as.isInstance(object) ? as.cast(object) : null;
	}

	@Override
	public boolean containsAliasAs(Object alias, Class<?> as) {
		return as.isInstance(getAliasTarget(alias));
	}

	@Override
	public boolean knowsAliasAs(Object alias, Class<?> as) {
		return as.isInstance(resolveAlias(alias));
	}

	@Override
	public <T> T followAliasAs(Object key, Class<T> as) {
		Object object = followAlias(key);
		return as.isInstance(object) ? as.cast(object) : null;
	}

	@Override
	public <T> T resolveAliasAs(Object key, Class<T> as) {
		Object object = resolveAlias(key);
		return as.isInstance(object) ? as.cast(object) : null;
	}
	
	
	@Override
	public IGenericRegistry<V> copy() {
		return new GenericRegistry<>(getParent(), createEntries(getEntries()), createAliases(getAliases()));
	}
	
	@Override
	public IGenericRegistry<V> createChildRegistry() {
		return new GenericRegistry<>(this);
	}
	
}
