package com.tokelon.toktales.tools.registry;

import java.util.Map;

public class BaseRegistry extends Registry<Object, Object> implements IGeneralRegistry<Object>, IGenericRegistry<Object>, IBasicRegistry {


	private final GeneralRegistry<Object> generalRegistry;
	private final GenericRegistry<Object> genericRegistry;
	
	public BaseRegistry() {
		super();
		
		Map<Object, Object> baseEntries = createEntries(null);
		Map<Object, Object> baseAliases = createAliases(null);
		generalRegistry = new GeneralRegistry<>(null, baseEntries, baseAliases);
		genericRegistry = new GenericRegistry<>(null, baseEntries, baseAliases);
	}

	public BaseRegistry(IRegistry<Object, Object> parent) {
		super(parent);
		
		Map<Object, Object> baseEntries = createEntries(null);
		Map<Object, Object> baseAliases = createAliases(null);
		generalRegistry = new GeneralRegistry<>(parent, baseEntries, baseAliases);
		genericRegistry = new GenericRegistry<>(parent, baseEntries, baseAliases);
	}

	public BaseRegistry(Map<Object, Object> backingEntries, Map<Object, Object> backingAliases) {
		super(backingEntries, backingAliases);
		
		generalRegistry = new GeneralRegistry<>(null, backingEntries, backingAliases);
		genericRegistry = new GenericRegistry<>(null, backingEntries, backingAliases);
	}

	public BaseRegistry(IRegistry<Object, Object> parent, Map<Object, Object> backingEntries, Map<Object, Object> backingAliases) {
		super(parent, backingEntries, backingAliases);
		
		generalRegistry = new GeneralRegistry<>(parent, backingEntries, backingAliases);
		genericRegistry = new GenericRegistry<>(parent, backingEntries, backingAliases);
	}
	
	
	@Override
	public <T> T getValueAs(Object key, Class<T> as) {
		return generalRegistry.getValueAs(key, as);
	}

	@Override
	public boolean containsAs(Object key, Class<?> as) {
		return generalRegistry.containsAs(key, as);
	}

	@Override
	public boolean knowsAs(Object key, Class<?> as) {
		return generalRegistry.knowsAs(key, as);
	}

	@Override
	public <T> T followAs(Object key, Class<T> as) {
		return generalRegistry.followAs(key, as);
	}

	@Override
	public <T> T resolveAs(Object key, Class<T> as) {
		return generalRegistry.resolveAs(key, as);
	}
	

	@Override
	public <T> T getAliasTargetAs(Object alias, Class<T> as) {
		return genericRegistry.getAliasTargetAs(alias, as);
	}

	@Override
	public boolean containsAliasAs(Object alias, Class<?> as) {
		return genericRegistry.containsAliasAs(alias, as);
	}

	@Override
	public boolean knowsAliasAs(Object alias, Class<?> as) {
		return genericRegistry.knowsAliasAs(alias, as);
	}

	@Override
	public <T> T followAliasAs(Object key, Class<T> as) {
		return genericRegistry.followAliasAs(key, as);
	}

	@Override
	public <T> T resolveAliasAs(Object key, Class<T> as) {
		return genericRegistry.resolveAliasAs(key, as);
	}
	
	
	@Override
	public IBasicRegistry copy() {
		return new BaseRegistry(getParent(), createEntries(getEntries()), createAliases(getAliases()));
	}
	
	@Override
	public IBasicRegistry createChildRegistry() {
		return new BaseRegistry(this);
	}
	
}
