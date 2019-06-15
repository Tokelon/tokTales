package com.tokelon.toktales.tools.registry;

import java.util.Map;

public class StringRegistry<V> extends Registry<String, V> implements IStringRegistry<V> {


	public StringRegistry() {
		super();
	}

	public StringRegistry(IRegistry<String, V> parent) {
		super(parent);
	}

	public StringRegistry(Map<String, V> backingEntries, Map<String, String> backingAliases) {
		super(backingEntries, backingAliases);
	}

	public StringRegistry(IRegistry<String, V> parent, Map<String, V> backingEntries, Map<String, String> backingAliases) {
		super(parent, backingEntries, backingAliases);
	}

	
	@Override
	public IStringRegistry<V> copy() {
		return new StringRegistry<>(getParent(), createEntries(getEntries()), createAliases(getAliases()));
	}
	
	@Override
	public IStringRegistry<V> createChildRegistry() {
		return new StringRegistry<>(this);
	}
	
}
