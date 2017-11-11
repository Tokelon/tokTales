package com.tokelon.toktales.desktop.lwjgl.render;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class GLMesh {
	
	
	private final int vaoId;
	
	private final int posVboId;
	
	private final int idxVboId;
	
	
	
	private final int vertexCount;
	
	public GLMesh(int[] indices) {
		
		vertexCount = indices.length;

		// VAO
		vaoId = glGenVertexArrays();	// Generate VAO
		glBindVertexArray(vaoId);		// Bind VAO to current to use it

		
		// VBO		
		posVboId = glGenBuffers();			// Generate VBO
		glBindBuffer(GL_ARRAY_BUFFER, posVboId);	// Bind VBO to use it

		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);				// Set the organization for VAO
		
		
		
		// VBO IDX
		IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indices.length);
		indicesBuffer.put(indices).flip();
		
		idxVboId = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, idxVboId);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
		
		
		
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);								// Unbind the VBO

		glBindVertexArray(0);	// Unbind VAO
		
	}
	
	
	public void setCoords(FloatBuffer coordsBuffer) {
				
		glBindBuffer(GL_ARRAY_BUFFER, posVboId);	// Bind VBO to use it
		glBufferData(GL_ARRAY_BUFFER, coordsBuffer, GL_STATIC_DRAW);	// Pass VBO Data

		// Is this needed ?
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);				// Set the organization for VAO

		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	
	public int getVaoId() {
		return vaoId;
	}
	
	public int getVertexCount() {
		return vertexCount;
	}
	
	public void bind() {
		glBindVertexArray(vaoId);
		glEnableVertexAttribArray(0);
	}
	
	
	
	public void cleanUp() {
		glDisableVertexAttribArray(0);
		
		// Delete the vbo
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glDeleteBuffers(posVboId);
		glDeleteBuffers(idxVboId);

		// Delete the VAO
		glBindVertexArray(0);
		glDeleteVertexArrays(vaoId);
		
	}
	

}
