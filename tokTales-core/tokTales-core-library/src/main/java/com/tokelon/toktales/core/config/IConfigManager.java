package com.tokelon.toktales.core.config;

public interface IConfigManager {
	
	public static final String MAIN_CONFIG = "config_manager_main_config";
	
	
	public void loadConfig(String configName, IFileConfig config);
	
	//public void loadConfig(String configName, ICiniConfig config);	// Mutable config?
	
	
	
	public IFileConfig getConfig(String configName);
		
}
