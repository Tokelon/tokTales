package com.tokelon.toktales.core.render.opengl.gl20.facade;

import java.nio.FloatBuffer;

import javax.inject.Inject;

import org.joml.Matrix4f;

import com.tokelon.toktales.core.render.opengl.IGLBufferUtils;
import com.tokelon.toktales.core.render.opengl.OpenGLException;
import com.tokelon.toktales.core.render.opengl.gl20.IGL11;
import com.tokelon.toktales.core.render.opengl.gl20.IGL20;
import com.tokelon.toktales.core.render.opengl.gl20.facade.IGLUtils.ShaderType;

public class GLProgram implements IGLProgram {

	
	private final FloatBuffer uniformMatrix4Buffer;
	
	private int programId;
	private IGLShader programVertexShader;
	private IGLShader programFragmentShader;
	
	private final IGL20 gl20;
	private final IGLBufferUtils glBufferUtils;
	
	@Inject
	public GLProgram(IGL20 gl20, IGLBufferUtils glBufferUtils) {
		this.gl20 = gl20;
		this.glBufferUtils = glBufferUtils;
		
		uniformMatrix4Buffer = glBufferUtils.createFloatBuffer(16);
	}
	
	
	@Override
	public void create() throws OpenGLException {
		programId = gl20.glCreateProgram();
		
		if(programId == 0) {
			throw new OpenGLException("Error creating program"); // TODO: Output error or log or something
		}
	}

	@Override
	public void attachShader(IGLShader shader) {
		gl20.glAttachShader(programId, shader.getId()); // TODO: Validate status?
		
		ShaderType shaderType = shader.getShaderType();
		if(shaderType == ShaderType.VERTEX_SHADER) {
			programVertexShader = shader;
		}
		else if(shaderType == ShaderType.FRAGMENT_SHADER) {
			programFragmentShader = shader;
		}
	}

	@Override
	public void link() throws OpenGLException {
		gl20.glLinkProgram(programId);
		
		if(gl20.glGetProgrami(programId, IGL20.GL_LINK_STATUS) == IGL11.GL_FALSE) {
			throw new OpenGLException("Error linking program: " + gl20.glGetProgramInfoLog(programId));
		}
	}

	@Override
	public void validate() throws OpenGLException {
		gl20.glValidateProgram(programId);
		
		if(gl20.glGetProgrami(programId, IGL20.GL_VALIDATE_STATUS) == IGL11.GL_FALSE) {
			throw new OpenGLException("Error validating program: " + gl20.glGetProgramInfoLog(programId));
		}
	}

	@Override
	public void bind() {
		gl20.glUseProgram(programId);
	}

	@Override
	public void unbind() {
		gl20.glUseProgram(0);
	}

	@Override
	public void detachShader(IGLShader shader) {
		gl20.glDetachShader(programId, shader.getId());	// TODO: Validate something?
	}

	@Override
	public void destroy() {
		unbind();
		
		if(programId != 0) {
			if(programVertexShader != null && programVertexShader.getId() != 0) {
				detachShader(programVertexShader);
			}
			if(programFragmentShader != null && programFragmentShader.getId() != 0) {
				detachShader(programFragmentShader);
			}
			
			gl20.glDeleteProgram(programId);
			programId = 0;
		}
	}
	
	
	@Override
	public int getId() {
		return programId;
	}

	
	@Override
	public IGLUniform createUniform(String uniformName) throws OpenGLException {
		int uniformLocation = gl20.glGetUniformLocation(programId, uniformName);
		if(uniformLocation < 0) {
			throw new OpenGLException("Error locating uniform: " + uniformName); // TODO: Get error message
		}
		
		return new GLUniform(uniformName, uniformLocation);
	}

	
	@Override
	public IGLAttribute createAttribute(String attributeName) throws OpenGLException {
		int attributeLocation = gl20.glGetAttribLocation(programId, attributeName);
		if(attributeLocation < 0) {
			throw new OpenGLException("Error locating attribute: " + attributeName); // TODO: Get error message
		}
		
		return new GLAttribute(attributeName, attributeLocation);
	}
	
	
	@Override
	public void setUniformMatrix4fv(IGLUniform glUniform, boolean transpose, Matrix4f value) {
		value.get(uniformMatrix4Buffer);
		gl20.glUniformMatrix4fv(glUniform.getLocation(), transpose, uniformMatrix4Buffer);
	}

}
