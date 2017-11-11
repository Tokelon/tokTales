package com.tokelon.toktales.android.render.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class GLMeshlike {

	
	private final ShortBuffer indicesShortBuffer;
	
	private final FloatBuffer positionsFloatBuffer;
	
	private final int indexCount;
	private final int positionCount;
	
	public GLMeshlike(short[] indices, int positionCount) {
		this.indexCount = indices.length;
		this.positionCount = positionCount;
		
		ByteBuffer indicesBuffer = ByteBuffer.allocateDirect(indices.length * 2);
		indicesBuffer.order(ByteOrder.nativeOrder());
		indicesShortBuffer = indicesBuffer.asShortBuffer();
		
		indicesShortBuffer.position(0);
		indicesShortBuffer.put(indices);
		indicesShortBuffer.position(0);
		
		
		ByteBuffer positionsBuffer = ByteBuffer.allocateDirect(positionCount * 4);
		positionsBuffer.order(ByteOrder.nativeOrder());
		positionsFloatBuffer = positionsBuffer.asFloatBuffer();
		positionsFloatBuffer.position(0);
	}
	
	
	public FloatBuffer setPositions(float[] positions) {
		positionsFloatBuffer.position(0);
		positionsFloatBuffer.put(positions).position(0);
		
		return positionsFloatBuffer;
	}
	
	public FloatBuffer getPositions() {
		positionsFloatBuffer.position(0);
		return positionsFloatBuffer;
	}
	
	public ShortBuffer getIndices() {
		indicesShortBuffer.position(0);
		return indicesShortBuffer;
	}
	
	
	public int getIndicesCount() {
		return indexCount;
	}
	
	public int getPositionsCount() {
		return positionCount;
	}
	
}
