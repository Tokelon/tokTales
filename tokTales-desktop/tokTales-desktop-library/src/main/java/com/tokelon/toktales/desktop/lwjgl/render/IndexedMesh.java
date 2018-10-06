package com.tokelon.toktales.desktop.lwjgl.render;

import org.joml.Vector3f;

public class IndexedMesh {
	
	
	private float[] positions;
	
	private int[] indices;
	
	
	public IndexedMesh(float[] positions, int[] indices) {
		
		this.positions = positions;
		this.indices = indices;
	}
	
	
	public void getVertexAt(int index, Vector3f result) {
		result.set(positions[indices[index] * 3], positions[indices[index] * 3] + 1, positions[indices[index] * 3] + 2);
	}
	

}
