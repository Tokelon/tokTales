package com.tokelon.toktales.core.render.opengl.gl20.facade;

import com.tokelon.toktales.core.render.opengl.gl20.IGL20;

public interface IGLUtils {
	

	public enum ShaderType {
		VERTEX_SHADER,
		FRAGMENT_SHADER
	}
	
	
	public static int mapShaderType(ShaderType type) {
		switch (type) {
		case VERTEX_SHADER:
			return IGL20.GL_VERTEX_SHADER;
		case FRAGMENT_SHADER:
			return IGL20.GL_FRAGMENT_SHADER;
		default:
			assert false : "No such shader type";
			return -1;
		}
	}
	
}
