package com.tokelon.toktales.core.render.opengl.gl20.facade;

public class GLStringScript implements IGLScript {

	
	private final String source;

	/**
	 * @param source
	 * @throws NullPointerException If source is null.
	 */
	public GLStringScript(String source) {
		if(source == null) {
			throw new NullPointerException();
		}
		
		this.source = source;
	}
	
	
	@Override
	public String getSource() {
		return source;
	}

}
