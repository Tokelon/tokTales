package com.tokelon.toktales.tools.registry;

import java.util.Map;

public class StringGeneralRegistry extends GeneralRegistry<String> implements IStringGeneralRegistry {


	public StringGeneralRegistry() {
		super();
	}

	public StringGeneralRegistry(IRegistry<String, Object> parent) {
		super(parent);
	}
	
	public StringGeneralRegistry(Map<String, Object> backingEntries, Map<String, String> backingAliases) {
		super(backingEntries, backingAliases);
	}

	public StringGeneralRegistry(IRegistry<String, Object> parent, Map<String, Object> backingEntries, Map<String, String> backingAliases) {
		super(parent, backingEntries, backingAliases);
	}
	
	
	@Override
	public IStringGeneralRegistry copy() {
		return new StringGeneralRegistry(getParent(), createEntries(getEntries()), createAliases(getAliases()));
	}
	
	@Override
	public IStringGeneralRegistry createChildRegistry() {
		return new StringGeneralRegistry(this);
	}
	
}
