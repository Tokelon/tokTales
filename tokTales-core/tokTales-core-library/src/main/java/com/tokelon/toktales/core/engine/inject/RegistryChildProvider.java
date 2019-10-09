package com.tokelon.toktales.core.engine.inject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Provider;

import com.tokelon.toktales.tools.core.registry.BasicRegistry;
import com.tokelon.toktales.tools.core.registry.IBasicRegistry;

public class RegistryChildProvider implements Provider<IBasicRegistry> {


	private final IBasicRegistry parentRegistry;
	private final Map<Object, Object> entries;
	private final Map<Object, Object> aliases;
	
	public RegistryChildProvider(IBasicRegistry parentRegistry, Map<Object, Object> entries, Map<Object, Object> aliases) {
		this.parentRegistry = parentRegistry;
		this.entries = entries;
		this.aliases = aliases;
	}
	
	
	@Override
	public IBasicRegistry get() {
		return new BasicRegistry(parentRegistry, new ConcurrentHashMap<>(entries), new ConcurrentHashMap<>(aliases));
	}

}
