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
			return null;
		}
	}
	
	@Override
	public <T> T getExtensionAsOrFail(String name, Class<T> type) {
		T extension = getExtensionAs(name, type);
		if(extension == null) {
			throw new ServiceException(String.format("Invalid type '%s' for extension '%s'", type, name));
		}
		
		return extension;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends IServiceExtension> T getExtensionByType(Class<T> type) {
		T result = null;
		
		synchronized (extensionsMap) {
			for(IServiceExtension ext: extensionsMap.values()) {
				
				if(type.isInstance(ext)) {
					result = (T) ext;
					break;
				}
			}
		}
		
		return result;
	}
	
	@Override
	public <T extends IServiceExtension> T getExtensionByTypeOrFail(Class<T> type) {
		T result = getExtensionByType(type);
		
		if(result == null) {
			throw new ServiceException(String.format("No extension found for type '%s'", type));
		}
		
		return result;
	}


}
