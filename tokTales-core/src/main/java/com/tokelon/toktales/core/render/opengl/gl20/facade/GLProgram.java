package com.tokelon.toktales.core.render.opengl.gl20.facade;

import java.nio.FloatBuffer;

import javax.inject.Inject;

import org.joml.Matrix4f;

import com.tokelon.toktales.core.render.opengl.IGLBufferUtils;
import com.tokelon.toktales.core.render.opengl.IGLErrorUtils;
import com.tokelon.toktales.core.render.opengl.OpenGLException;
import com.tokelon.toktales.core.render.opengl.gl20.IGL11;
import com.tokelon.toktales.core.render.opengl.gl20.IGL20;
import com.tokelon.toktales.core.render.opengl.gl20.facade.IGLFacade.ShaderType;

public class GLProgram implements IGLProgram {

	
	private final FloatBuffer uniformMatrix4Buffer;
	
	private int programId;
	private IGLShader programVertexShader;
	private IGLShader programFragmentShader;
	
	private final IGLErrorUtils glErrorUtils;
	private final IGLBufferUtils glBufferUtils;
	private final IGL20 gl20;
	
	@Inject
	public GLProgram(IGLErrorUtils glErrorUtils, IGLBufferUtils glBufferUtils, IGL20 gl20) {
		this.glErrorUtils = glErrorUtils;
		this.gl20 = gl20;
		this.glBufferUtils = glBufferUtils;
		
		uniformMatrix4Buffer = glBufferUtils.createFloatBuffer(16);
	}
	
	
	@Override
	public void create() throws OpenGLException {
		glErrorUtils.logGLErrors("before GLProgram create"); // Clear previous errors
		
		
		programId = gl20.glCreateProgram();

		glErrorUtils.assertNoGLErrors(); // Will throw exception if there are errors
		if(programId == 0) {
			throw new OpenGLException("Error creating program");
		}
	}

	@Override
	public void attachShader(IGLShader shader) {
		glErrorUtils.logGLErrors("before GLProgram attachShader"); // Clear previous errors
		gl20.glAttachShader(programId, shader.getId());
		glErrorUtils.logGLErrors("after GLProgram attachShader");
		
		
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
		glErrorUtils.logGLErrors("before GLProgram link");
		gl20.glLinkProgram(programId);
		
		if(gl20.glGetProgrami(programId, IGL20.GL_LINK_STATUS) == IGL11.GL_FALSE) {
			glErrorUtils.logGLErrors("after GLProgram link");
			throw new OpenGLException("Error linking program: " + gl20.glGetProgramInfoLog(programId));
		}
	}

	@Override
	public void validate() throws OpenGLException {
		glErrorUtils.logGLErrors("before GLProgram validate");
		gl20.glValidateProgram(programId);
		
		if(gl20.glGetProgrami(programId, IGL20.GL_VALIDATE_STATUS) == IGL11.GL_FALSE) {
			glErrorUtils.logGLErrors("after GLProgram validate");
			throw new OpenGLException("Error validating program: " + gl20.glGetProgramInfoLog(programId));
		}
	}

	@Override
	public void bind() {
		gl20.glUseProgram(programId);
		glErrorUtils.logGLErrors("GLProgram bind");
	}

	@Override
	public void unbind() {
		gl20.glUseProgram(0);
		glErrorUtils.logGLErrors("GLProgram unbind");
	}

	@Override
	public void detachShader(IGLShader shader) {
		glErrorUtils.logGLErrors("before GLProgram detachShader");
		gl20.glDetachShader(programId, shader.getId());
		glErrorUtils.logGLErrors("after GLProgram detachShader");
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
			
			glErrorUtils.logGLErrors("GLProgram destroy");
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
			glErrorUtils.logGLErrors("GLProgram createUniform");
			throw new OpenGLException("Error locating uniform: " + uniformName);
		}
		
		return new GLUniform(uniformName, uniformLocation);
	}

	
	@Override
	public IGLAttribute createAttribute(String attributeName) throws OpenGLException {
		int attributeLocation = gl20.glGetAttribLocation(programId, attributeName);
		if(attributeLocation < 0) {
			glErrorUtils.logGLErrors("GLProgram createAttribute");
			throw new OpenGLException("Error locating attribute: " + attributeName);
		}
		
		return new GLAttribute(attributeName, attributeLocation);
	}
	
	
	@Override
	public void setUniformMatrix4fv(IGLUniform glUniform, boolean transpose, Matrix4f value) {
		value.get(uniformMatrix4Buffer);
		gl20.glUniformMatrix4fv(glUniform.getLocation(), transpose, uniformMatrix4Buffer);
		glErrorUtils.logGLErrors("GLProgram setUniformMatrix4fv");
	}

}
