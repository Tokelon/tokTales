package com.tokelon.toktales.tools.script;

import java.io.InputStream;

public interface IResourceFinder {
	
	
	/**
	 * 
	 * @param filename
	 * @return InputStream, or null if not found.
	 */
	public InputStream findResource(String filename);

}
