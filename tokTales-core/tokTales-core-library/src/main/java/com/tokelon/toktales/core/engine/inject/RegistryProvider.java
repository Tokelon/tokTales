package com.tokelon.toktales.core.engine.inject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Provider;

import com.tokelon.toktales.tools.registry.BasicRegistry;
import com.tokelon.toktales.tools.registry.IBasicRegistry;

public class RegistryProvider implements Provider<IBasicRegistry> {


	private final Map<Object, Object> entries;
	private final Map<Object, Object> aliases;
	
	public RegistryProvider(Map<Object, Object> entries, Map<Object, Object> aliases) {
		this.entries = entries;
		this.aliases = aliases;
	}
	
	
	@Override
	public IBasicRegistry get() {
		return new BasicRegistry(new ConcurrentHashMap<>(entries), new ConcurrentHashMap<>(aliases));
	}

}
