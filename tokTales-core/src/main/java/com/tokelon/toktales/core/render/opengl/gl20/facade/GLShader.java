package com.tokelon.toktales.core.render.opengl.gl20.facade;

import javax.inject.Inject;

import com.tokelon.toktales.core.render.opengl.OpenGLException;
import com.tokelon.toktales.core.render.opengl.gl20.IGL11;
import com.tokelon.toktales.core.render.opengl.gl20.IGL20;
import com.tokelon.toktales.core.render.opengl.gl20.facade.IGLUtils.ShaderType;

public class GLShader implements IGLShader {

	
	private int shaderId;
	private ShaderType shaderType;
	private IGLScript shaderSource;
	
	private final IGL20 gl20;
	
	@Inject
	public GLShader(IGL20 gl20) {
		this.gl20 = gl20;
	}
	
	
	@Override
	public void create(ShaderType type) throws OpenGLException {
		shaderType = type;
		
		shaderId = gl20.glCreateShader(IGLUtils.mapShaderType(type));
		
		if(shaderId == 0) {
			throw new OpenGLException("Error creating shader"); // TODO: Add error if possible
		}
	}

	@Override
	public void setSource(IGLScript source) {
		shaderSource = source;
		
		gl20.glShaderSource(shaderId, source.getSource());
	}

	@Override
	public void compile() throws OpenGLException {
		gl20.glCompileShader(shaderId);
		
		if(gl20.glGetShaderi(shaderId, IGL20.GL_COMPILE_STATUS) == IGL11.GL_FALSE) {
			throw new OpenGLException("Error compiling shader: " + gl20.glGetShaderInfoLog(shaderId));
		}
	}
	
	@Override
	public void destroy() {
		gl20.glDeleteShader(shaderId);
		shaderId = 0;
	}

	
	@Override
	public int getId() {
		return shaderId;
	}
	
	@Override
	public IGLScript getSource() {
		return shaderSource;
	}

	@Override
	public ShaderType getShaderType() {
		return shaderType;
	}

}
