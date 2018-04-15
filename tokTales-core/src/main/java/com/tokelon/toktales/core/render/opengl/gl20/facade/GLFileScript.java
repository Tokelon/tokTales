package com.tokelon.toktales.core.render.opengl.gl20.facade;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class GLFileScript implements IGLScript {

	private static final int BUFFER_SIZE = 4096;
	private static final String CHARSET = "UTF-8";
	

	private String source;
	private boolean isLoaded = false;
	
	private final InputStream inputStream;

	public GLFileScript(InputStream inputStream) {
		if(inputStream == null) {
			throw new NullPointerException();
		}
		
		this.inputStream = inputStream;
	}

	
	/** Reads the data from this script's input stream.
	 * <p>
	 * If the script has been loaded already, does nothing.
	 * 
	 * @throws IOException For any errors while reading the data.
	 */
	public void load() throws IOException {
		if(isLoaded) {
			return;
		}
		
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[BUFFER_SIZE];

		int length;
		while ((length = inputStream.read(buffer)) != -1) {
		    result.write(buffer, 0, length);
		}
		
		source = result.toString(CHARSET);
		
		inputStream.close();
		isLoaded = true;
	}
	
	/**
	 * @return True if this script's data has been loaded, false if not.
	 */
	public boolean isLoaded() {
		return isLoaded;
	}
	
	
	@Override
	public String getSource() {
		return source;
	}
	
	
	/** Creates and loads the given input stream into a script.
	 * 
	 * @param inputStream
	 * @return A new script with the source from the given input stream.
	 * @throws IOException For any errors while reading the data.
	 */
	public static GLFileScript createLoadedScript(InputStream inputStream) throws IOException {
		GLFileScript script = new GLFileScript(inputStream);
		script.load();
		
		return script;
	}

}
