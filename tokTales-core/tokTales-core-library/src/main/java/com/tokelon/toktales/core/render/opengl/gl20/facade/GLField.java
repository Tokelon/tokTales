package com.tokelon.toktales.core.render.opengl.gl20.facade;

public class GLField implements IGLField {

	
	private final String name;
	private final int location;
	
	public GLField(String name, int location) {
		this.name = name;
		this.location = location;
	}
	
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getLocation() {
		return location;
	}

}
