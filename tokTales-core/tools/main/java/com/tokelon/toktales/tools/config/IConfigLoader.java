package com.tokelon.toktales.tools.config;

public interface IConfigLoader {

	//public void initialize();
	
	
	public void read() throws ConfigFormatException;
	
	public void process();
	
	public ICiniConfig getLoadedConfig();
	
	
	public ICiniConfig loadConfig() throws ConfigFormatException;
	
}
