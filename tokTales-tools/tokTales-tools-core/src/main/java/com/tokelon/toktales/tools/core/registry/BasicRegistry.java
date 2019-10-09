package com.tokelon.toktales.tools.core.registry;

import java.util.Map;

public class BasicRegistry extends GeneralRegistry<Object> implements IBasicRegistry {


	public BasicRegistry() {
		super();
	}

	public BasicRegistry(IRegistry<Object, Object> parent) {
		super(parent);
	}
	
	public BasicRegistry(Map<Object, Object> backingEntries, Map<Object, Object> backingAliases) {
		super(backingEntries, backingAliases);
	}

	public BasicRegistry(IRegistry<Object, Object> parent, Map<Object, Object> backingEntries, Map<Object, Object> backingAliases) {
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
	public IBasicRegistry copy() {
		return new BasicRegistry(getParent(), createEntries(getEntries()), createAliases(getAliases()));
	}
	
	@Override
	public IBasicRegistry createChildRegistry() {
		return new BasicRegistry(this);
	}
	
}
