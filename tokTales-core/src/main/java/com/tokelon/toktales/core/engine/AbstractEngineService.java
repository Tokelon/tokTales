package com.tokelon.toktales.core.engine;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractEngineService implements IEngineService {

	private final Map<String, IServiceExtension> extensionsMap;
	
	public AbstractEngineService() {
		this.extensionsMap = Collections.synchronizedMap(new HashMap<String, IServiceExtension>());
	}
	
	
	@Override
	public void addExtension(String name, IServiceExtension extension) {
		if(name == null || extension == null) {
			throw new IllegalArgumentException();
		}
		
		if(extensionsMap.containsKey(name)) {
			throw new ServiceException("There already exists an extension with name: " +name);
		}

		extensionsMap.put(name, extension);
		
		extension.attachService(this);
	}

	
	@Override
	public boolean removeExtension(String name) {
		return extensionsMap.remove(name) != null;
	}

	@Override
	public boolean hasExtension(String name) {
		return extensionsMap.containsKey(name);
	}

	@Override
	public IServiceExtension getExtension(String name) {
		return extensionsMap.get(name);
	}


	@Override
	public <T> T getExtensionAs(String name, Class<T> type) {
		if(name == null || type == null) {
			throw new IllegalArgumentException();
		}
		
		IServiceExtension extension = extensionsMap.get(name);
		if(type.isInstance(extension)) {
			return type.cast(extension);
		}
		else {
			throw new ServiceException(String.format("Invalid type '%s' for extension '%s'", type, name));
		}
	}

	@Override
	public <T> T getExtensionAsIf(String name, Class<T> type) {
		if(name == null || type == null) {
			throw new IllegalArgumentException();
		}
		
		IServiceExtension extension = extensionsMap.get(name);
		if(type.isInstance(extension)) {
			return type.cast(extension);
		}
		else {
			return null;
		}
	}


}
