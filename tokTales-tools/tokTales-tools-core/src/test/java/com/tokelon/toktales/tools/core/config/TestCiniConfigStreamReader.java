package com.tokelon.toktales.tools.core.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class TestCiniConfigStreamReader {

	public static void main(String[] args) throws IOException, ConfigFormatException {

		
		//String configFilePath = "C:\\SVN\\tokelon\\trunk\\code\\Tokelon Framework\\res\\configs\\test_config.conf";
		//String configFilePath = "C:\\SVN\\tokelon\\trunk\\code\\Tokelon Framework\\res\\configs\\bad_config_01.ini";
		String configFilePath = "C:\\SVN\\tokelon\\trunk\\code\\Tokelon Framework\\res\\configs\\bad_config_02.cfg";
		
		
		File configFile = new File(configFilePath);
		if(!configFile.canRead()) {
			throw new IOException("Cannot read config file");
		}
		
		InputStream configFileIn = new FileInputStream(configFile);

		
		CiniConfigStreamReader reader = new CiniConfigStreamReader();

		ICiniConfig config = reader.readConfig(configFileIn);
		configFileIn.close();
	
		
		// Print that
		for(String section: config.getSections()) {
			System.out.println(String.format("[%s]", section));
			
			for(String sectionProperty: config.getProperties(section)) {
				System.out.println(String.format("%s=%s", sectionProperty, config.getPropertyValue(section, sectionProperty)));
			}
		}
	}

}
