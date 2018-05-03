package com.tokelon.toktales.core.render.opengl.gl20.facade;

import com.tokelon.toktales.core.render.opengl.OpenGLException;
import com.tokelon.toktales.core.render.opengl.gl20.facade.IGLFacade.ShaderType;

public interface IGLShader {

	
	public void create(ShaderType type) throws OpenGLException;
	
	public void setSource(IGLScript source);
	
	public void compile() throws OpenGLException;
	
	public void destroy();
	
	
	public int getId();
	public IGLScript getSource();
	public ShaderType getShaderType();
	
}
