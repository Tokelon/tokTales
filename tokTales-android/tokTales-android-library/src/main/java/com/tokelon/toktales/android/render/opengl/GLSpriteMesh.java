package com.tokelon.toktales.android.render.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class GLSpriteMesh {

	
	private final int vertexCount;
	
	private final FloatBuffer verticesFloatBuffer;
	private final FloatBuffer textureCoordsFloatBuffer;
	
	public GLSpriteMesh(float[] positions) {

		vertexCount = positions.length / 3;
		
		

		ByteBuffer verticesBuffer = ByteBuffer.allocateDirect(positions.length * 4);
		verticesBuffer.order(ByteOrder.nativeOrder());
		verticesFloatBuffer = verticesBuffer.asFloatBuffer();
		
		verticesFloatBuffer.position(0);
		verticesFloatBuffer.put(positions);
		verticesFloatBuffer.position(0);
		
		
		// Texture coordinate buffer setup
		ByteBuffer texCoordinateBuffer = ByteBuffer.allocateDirect(8 * 4);
		texCoordinateBuffer.order(ByteOrder.nativeOrder());
		textureCoordsFloatBuffer = texCoordinateBuffer.asFloatBuffer();
		textureCoordsFloatBuffer.position(0);
		
	}
	
	
	public FloatBuffer setTextureCoords(float[] textureCoords) {
		textureCoordsFloatBuffer.position(0);
		textureCoordsFloatBuffer.put(textureCoords).position(0);
		
		return textureCoordsFloatBuffer;
	}
	
	
	/** Also sets the returning buffer position to 0.
	 * 
	 * @return
	 */
	public FloatBuffer getPositions() {
		verticesFloatBuffer.position(0);
		return verticesFloatBuffer;
	}
	
	
	public int getVertexCount() {
		return vertexCount;
	}
	
	
}
