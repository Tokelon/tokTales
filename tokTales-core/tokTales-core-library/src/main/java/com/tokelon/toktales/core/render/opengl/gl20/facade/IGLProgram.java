package com.tokelon.toktales.core.render.opengl.gl20.facade;

import org.joml.Matrix4f;

import com.tokelon.toktales.core.render.opengl.OpenGLException;

public interface IGLProgram {
	// TODO: Clean up facade files (OpenGL CompatWrapper Facade)

	
	public void create() throws OpenGLException;
	
	public void attachShader(IGLShader shader);

	public void link() throws OpenGLException;
	
	public void validate() throws OpenGLException;
	
	public void bind();
	
	public void unbind();
	
	public void detachShader(IGLShader shader);
	
	public void destroy();
	

	public int getId();
	
	//public List<IGLShader> getShaders();

	
	
	public IGLUniform createUniform(String uniformName) throws OpenGLException;
	//public void createUniform(String uniformName) throws OpenGLException;

	public IGLAttribute createAttribute(String attributeName) throws OpenGLException;
	//public void createAttribute(String attributeName) throws OpenGLException;

	
	// TODO:
//	public void setUniform1i(IGLUniform uniform, int v0);
//	public void setUniform4f(IGLUniform uniform, Vector4f value);
//	public void setUniform4f(IGLUniform uniform, float v0, float v1, float v2, float v3);
	public void setUniformMatrix4fv(IGLUniform glUniform, boolean transpose, Matrix4f value);
	

	//public void setAttributePointer(IGLAttribute attribute, ...);
	
}
