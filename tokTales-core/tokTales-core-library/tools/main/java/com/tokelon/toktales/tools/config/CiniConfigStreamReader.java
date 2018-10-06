package com.tokelon.toktales.tools.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CiniConfigStreamReader implements ICiniConfigReader {

	@Override
	public MutableCiniConfig readConfig(InputStream input) throws ConfigFormatException {
		if(input == null) {
			throw new NullPointerException();
		}

		
		MutableCiniConfig ciniConfig = new MutableCiniConfig();
		
		ciniConfig.addSection("");	// Add the empty section
		

		
		InputStreamReader inputStreamReader = new InputStreamReader(input);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

		int lineCount = 0;
		try {
			
			String currentSection = "";		// Initial current section will be the empty
			String line;
			while((line = bufferedReader.readLine()) != null) {
				lineCount++;

				line = line.trim();
				if(line.isEmpty()) {
					continue;		// Ignore empty lines
				}
				
				switch (line.charAt(0)) {
				case '[':
					// Section
					if(line.charAt(line.length()-1) != ']') {
						throw new ConfigFormatException(String.format("Line %d: Sections must have the format [<Section Name>]", lineCount));
					}

					String section = line.substring(1, line.length()-1).trim();		// Also trims the section name
					if(section.isEmpty()) {
						throw new ConfigFormatException(String.format("Line %d: Section name cannot be empty", lineCount));
					}
					
					
					if(ciniConfig.hasSection(section)) {
						throw new ConfigFormatException(String.format("Line %d: Duplicate section \"%s\"", lineCount, section));
					}
					
					ciniConfig.addSection(section);
					currentSection = section;
					
					break;
				case ';':
				case '#':
					// Comment
					continue;
				case '/':
					if(line.charAt(1) == '/') continue;		// Comment

					
				default:
					int eqIndex = line.indexOf('=');
					if(eqIndex == -1) {
						throw new ConfigFormatException(String.format("Line %d: Properties must have the format <Property Name=Property Value", lineCount));
					}
					
					// Also trim these values
					String propName = line.substring(0, eqIndex).trim();
					String propValue = line.substring(eqIndex+1, line.length()).trim();
					
					
					// Property value can be empty!
					// But not the property name
					if(propName.isEmpty()) {
						throw new ConfigFormatException(String.format("Line %d: Property name cannot be empty", lineCount));
					}
					
					
					
					ciniConfig.addProperty(currentSection, propName, propValue);
					
					break;
				}
			}
			
		} catch(IOException ioe) {
			throw new ConfigFormatException(ioe);
		}
		
		// DO NOT close the inputstream at exit

		
		return ciniConfig;
	}

}
