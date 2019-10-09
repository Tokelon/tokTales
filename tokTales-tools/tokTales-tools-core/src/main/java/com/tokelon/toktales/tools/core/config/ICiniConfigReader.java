package com.tokelon.toktales.tools.core.config;

import java.io.InputStream;

/** 
 * Configuration can contain:<br>
 * - Sections: Defined like this "[*Section Name*]" Ex. [MapConfiguration]<br>
 * - Properties: Defined like this "*Property Name*=*Property Value*" Ex. timeToLive=12<br>
 * <br>
 * All values are being trimmed (remove blanks before and after) prior to storing.<br>
 * <br>
 * Empty lines and comments are being ignored.<br>
 * Comments are all lines that start with ";", "#" or "//".<br>
 * <br>
 * Properties above all Sections (if any) will be assigned to the empty section, accessible through the empty string "".
 * This Section is being added automatically whether it contains properties or not. It can be removed like any other Section.<br>
 * <br>
 * Sections and Property names must be non-empty.<br>
 * Property values can be left empty. Ex. parentPath= <br>
 * Sections can only be defined once throughout the config. Property names can only be defined once throughout their Section.
 *  
 */
public interface ICiniConfigReader {

	
	/** Parses the given input stream and builds a ICiniConfig.<br><br>
	 * 
	 * Note: The InputStream will not be closed after exiting this method.
	 * 
	 * @param input The InputStream to read the config from.
	 * @return The config containing all values read in the correct order.
	 * @throws ConfigFormatException If a format error occurs that goes against one of the rules defined in this interface. 
	 */
	public ICiniConfig readConfig(InputStream input) throws ConfigFormatException;
	
}
