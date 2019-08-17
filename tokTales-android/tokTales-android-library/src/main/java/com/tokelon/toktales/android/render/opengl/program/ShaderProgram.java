package com.tokelon.toktales.android.render.opengl.program;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector4f;

import android.opengl.GLES20;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;

public class ShaderProgram {


	private int vertexShaderId;
	private int fragmentShaderId;
	
	private TObjectIntMap<String> uniforms;
	private TObjectIntMap<String> attributes;

	private FloatBuffer uniformMatrixBuffer;

	private final int programId;

	
	public ShaderProgram() throws OpenGLException {
		programId = GLES20.glCreateProgram();
		
		if(programId == 0) {
			throw new OpenGLException("Error creating GL Program: " + programId);
		}

		
		uniforms = new TObjectIntHashMap<>();
		attributes = new TObjectIntHashMap<>();
		
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(16 * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		uniformMatrixBuffer = byteBuffer.asFloatBuffer();
	}
	
	
	public void createVertexShader(String shaderCode) throws OpenGLException {
		vertexShaderId = createShader(shaderCode, GLES20.GL_VERTEX_SHADER); 
	}
	
	public void createFragmentShader(String shaderCode) throws OpenGLException {
		fragmentShaderId = createShader(shaderCode, GLES20.GL_FRAGMENT_SHADER);
	}
	
	
	private int createShader(String shaderCode, int shaderType) throws OpenGLException {
		// Create shader with GLES20.GL_FRAGMENT_SHADER or GLES20.GL_VERTEX_SHADER
		int shaderId = GLES20.glCreateShader(shaderType);
		if(shaderId == 0) {
			throw new OpenGLException("Error creating Shader: " +shaderId);
		}
		
		// Add source code to the shader
		GLES20.glShaderSource(shaderId, shaderCode);
		
		// Compile the source code
		GLES20.glCompileShader(shaderId);
		
		int[] compileStatus = new int[1];
		GLES20.glGetShaderiv(shaderId, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
		if(compileStatus[0] == GLES20.GL_FALSE) {
			throw new OpenGLException("Error compiling Shader code: " + GLES20.glGetShaderInfoLog(shaderId));
		}
		
		GLES20.glAttachShader(programId, shaderId);

		
		return shaderId;
	}
	
	
	public void link() throws OpenGLException {
		GLES20.glLinkProgram(programId);
		
		int[] linkStatus = new int[1];
		GLES20.glGetProgramiv(programId, GLES20.GL_LINK_STATUS, linkStatus, 0);
		if(linkStatus[0] == GLES20.GL_FALSE) {
			throw new OpenGLException("Error linking Program: " + GLES20.glGetProgramInfoLog(programId));
		}


		GLES20.glValidateProgram(programId);

		int[] validateStatus = new int[1];
		GLES20.glGetProgramiv(programId, GLES20.GL_VALIDATE_STATUS, validateStatus, 0);
		if(validateStatus[0] == GLES20.GL_FALSE) {
			System.err.println("Warning validating Shader code: " + GLES20.glGetProgramInfoLog(programId));
		}

	}
	
	
	public void bind() {
		GLES20.glUseProgram(programId);
	}
	
	public void unbind() {
		GLES20.glUseProgram(0);
	}
	
	
	public void createAttribute(String attributeName) throws OpenGLException {
		int attributeLocation = GLES20.glGetAttribLocation(programId, attributeName);
		if(attributeLocation < 0) {
			throw new OpenGLException("Could not find attribute: " + attributeName);
		}
		
		attributes.put(attributeName, attributeLocation);
	}
	
	public void setAttribute(String attributeName, int vertexSize, FloatBuffer value) {
		GLES20.glVertexAttribPointer(attributes.get(attributeName), vertexSize, GLES20.GL_FLOAT, false, 0, value);
	}
	
	public int getAttributeLocation(String attributeName) {
		return attributes.get(attributeName);
	}
	
	
	public void createUniform(String uniformName) throws OpenGLException {
		int uniformLocation = GLES20.glGetUniformLocation(programId, uniformName);
		if(uniformLocation < 0) {
			throw new OpenGLException("Could not find uniform: " + uniformName);
		}
		
		uniforms.put(uniformName, uniformLocation);
	}
	
	public synchronized void setUniform(String uniformName, Matrix4f value) {
		value.get(uniformMatrixBuffer);
		
		GLES20.glUniformMatrix4fv(uniforms.get(uniformName), 1, false, uniformMatrixBuffer);
	}
	
	public void setUniform(String uniformName, int value) {
		GLES20.glUniform1i(uniforms.get(uniformName), value);
	}
	
	public void setUniform(String uniformName, Vector4f value) {
		GLES20.glUniform4f(uniforms.get(uniformName), value.x, value.y, value.z, value.w);
	}
	
	public int getUniformLocation(String uniformName) {
		return uniforms.get(uniformName);
	}
	
	
	public void cleanup() {
		unbind();
		
		if(programId != 0) {
			
			if(vertexShaderId != 0) {
				GLES20.glDetachShader(programId, vertexShaderId);
			}
			
			if(fragmentShaderId != 0) {
				GLES20.glDetachShader(programId, fragmentShaderId);
			}
			
			GLES20.glDeleteProgram(programId);
		}
	}
	
}
