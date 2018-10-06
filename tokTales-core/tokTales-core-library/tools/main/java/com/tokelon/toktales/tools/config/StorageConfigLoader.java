package com.tokelon.toktales.tools.config;

import java.io.IOException;
import java.io.InputStream;

import com.tokelon.toktales.core.engine.storage.IStorageService;
import com.tokelon.toktales.core.engine.storage.StorageException;
import com.tokelon.toktales.core.storage.IApplicationLocation;

public class StorageConfigLoader implements IConfigLoader {

	
	private final IStorageService storageService;
	
	
	private InputStream configInputStream;
	private IApplicationLocation configFileLocation;
	private String configFileName;

	private ICiniConfig loadedConfig;
	
	
	public StorageConfigLoader(IStorageService storageService) {
		if(storageService == null) {
			throw new NullPointerException();
		}
		
		this.storageService = storageService;
	}
	
	
	
	
	public void setTarget(IApplicationLocation location, String configFileName) throws StorageException {
		if(location == null || configFileName == null) {
			throw new NullPointerException();
		}
		
		if(configFileName.trim().isEmpty()) {
			throw new IllegalArgumentException("Config file name cannot be empty");
		}
		
		this.configInputStream = storageService.readAppFileOnExternal(location, configFileName);
		this.configFileLocation = location;
		this.configFileName = configFileName;
	}
	
	//public void setTarget(InputStream input, String fileName) {

	
	
	@Override
	public void read() throws ConfigFormatException {
		
		CiniConfigStreamReader reader = new CiniConfigStreamReader();
		
		ICiniConfig readConfig = null;
		try {
			readConfig = reader.readConfig(configInputStream);
			
		}
		finally {
			try {
				configInputStream.close();
			} catch(IOException e) { /* Nothing? */ }
		}
		
		loadedConfig = readConfig;
	}
	
	
	@Override
	public void process() {
		// Nothing yet
	}
	
	
	
	@Override
	public ICiniConfig loadConfig() throws ConfigFormatException {
		read();
		
		process();
		
		return loadedConfig;
	}
	
	
	@Override
	public ICiniConfig getLoadedConfig() {
		return loadedConfig;
	}

	
}
