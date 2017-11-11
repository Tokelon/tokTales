package com.tokelon.toktales.core.config;

import com.tokelon.toktales.core.prog.IProgramInterface;


public interface IConfigManager extends IProgramInterface {
	
	public static final String MAIN_CONFIG = "config_manager_main_config";
	
	
	public void loadConfig(String configName, IFileConfig config);
	
	//public void loadConfig(String configName, ICiniConfig config);	// Mutable config?
	
	
	
	public IFileConfig getConfig(String configName);
		
}
