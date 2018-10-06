package com.tokelon.toktales.core.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.tokelon.toktales.core.prog.annotation.ThreadSafe;

@ThreadSafe
public class ConfigManager implements IConfigManager {
	
	private final Map<String, IFileConfig> configsMap;

	public ConfigManager() {
		configsMap = Collections.synchronizedMap(new HashMap<String, IFileConfig>());
	}
	
	
	@Override
	public void loadConfig(String configName, IFileConfig config) {
		if(configName == null || config == null) {
			throw new NullPointerException();
		}
		
		IFileConfig previousConfig = configsMap.put(configName, config);
		
		
		if(previousConfig != null) {
			previousConfig.passListeners(config);
			config.refreshAllValues();
		}
		
	}

	@Override
	public IFileConfig getConfig(String configName) {
		if(configName == null) {
			throw new NullPointerException();
		}
		
		// TODO: Probably throw exception if no config with this configName had been loaded
		
		return configsMap.get(configName);
	}

}
