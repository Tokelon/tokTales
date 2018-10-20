package com.tokelon.toktales.desktop.lwjgl;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class ShaderProgram {

	
	private final Map<String, Integer> uniforms;
	
	private final FloatBuffer uniformMatrixBuffer;
	
	private final int programId;
	
	private int vertexShaderId;
	
	private int fragmentShaderId;
	
	
	public ShaderProgram() throws LWJGLException {
		
		programId = GL20.glCreateProgram();
		
		if(programId == 0) {
			throw new LWJGLException("Error creating GL Program: " +programId);
		}
		
		uniforms = new HashMap<String, Integer>();
		uniformMatrixBuffer = BufferUtils.createFloatBuffer(16);
	}
	
	
	public void createVertexShader(String shaderCode) throws LWJGLException {
		vertexShaderId = createShader(shaderCode, GL20.GL_VERTEX_SHADER);
	}
	
	public void createFragmentShader(String shaderCode) throws LWJGLException {
		fragmentShaderId = createShader(shaderCode, GL20.GL_FRAGMENT_SHADER);
	}
	
	
	private int createShader(String shaderCode, int shaderType) throws LWJGLException {
		
		int shaderId = GL20.glCreateShader(shaderType);
		if(shaderId == 0) {
			throw new LWJGLException("Error creating Shader: " +shaderId);
		}
		
		GL20.glShaderSource(shaderId, shaderCode);
		GL20.glCompileShader(shaderId);
		
		if(GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			throw new LWJGLException("Error compiling Shader code: " + GL20.glGetShaderInfoLog(shaderId));
		}
		
		GL20.glAttachShader(programId, shaderId);
		
		return shaderId;
	}
	
	
	public void link() throws LWJGLException {
		
		GL20.glLinkProgram(programId);
		if(GL20.glGetProgrami(programId, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
			throw new LWJGLException("Error linking Program: " + GL20.glGetProgramInfoLog(programId));
		}
		
		GL20.glValidateProgram(programId);
		if(GL20.glGetProgrami(programId, GL20.GL_VALIDATE_STATUS) == GL11.GL_FALSE) {
			System.err.println("Warning validating Shader code: " + GL20.glGetProgramInfoLog(programId));
		}
		
	}
	
	
	public void bind() {
		GL20.glUseProgram(programId);
	}
	
	public void unbind() {
		GL20.glUseProgram(0);
	}
	
	
	
	public void createUniform(String uniformName) throws LWJGLException {
		
		int uniformLocation = GL20.glGetUniformLocation(programId, uniformName);
		if(uniformLocation < 0) {
			throw new LWJGLException("Could not find uniform: " + uniformName);
		}
		
		uniforms.put(uniformName, uniformLocation);
	}
	
	public synchronized void setUniform(String uniformName, Matrix4f value) {
		// Dump the matrix into a float buffer
		
		//FloatBuffer uniformMatrixBuffer = BufferUtils.createFloatBuffer(16);
		
		value.get(uniformMatrixBuffer);
		
		GL20.glUniformMatrix4fv(uniforms.get(uniformName), false, uniformMatrixBuffer);
	}
	
	public void setUniform(String uniformName, int value) {
		GL20.glUniform1i(uniforms.get(uniformName), value);
	}
	
	public void setUniform(String uniformName, Vector4f value) {
		GL20.glUniform4f(uniforms.get(uniformName), value.x, value.y, value.z, value.w);
	}
	
	
	public void cleanup() {
		
		unbind();
		
		if(programId != 0) {
			
			if(vertexShaderId != 0) {
				GL20.glDetachShader(programId, vertexShaderId);
				// Delete shader ?
			}
			
			if(fragmentShaderId != 0) {
				GL20.glDetachShader(programId, fragmentShaderId);
			}
			
			GL20.glDeleteProgram(programId);
		}
	}
	
	
}