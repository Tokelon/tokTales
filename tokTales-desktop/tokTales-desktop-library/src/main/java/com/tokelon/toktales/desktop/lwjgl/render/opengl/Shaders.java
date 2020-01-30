package com.tokelon.toktales.desktop.lwjgl.render.opengl;

public class Shaders {

	private Shaders() {}
	
	
	public static final String VS_Exp =
			"#version 330\n" +
			"layout (location=0) in vec3 pos;\n" +
			"layout (location=1) in vec3 inColour;\n" +
			"out vec3 exColour;\n" +
			"uniform mat4 projectionMatrix;\n" +
			"void main() {\n" +
			"  gl_Position = projectionMatrix * vec4(pos, 1.0);\n" +
			"  exColour = inColour;\n" +
			"}\n";
	
	public static final String FS_Exp =
			"#version 330\n" +
			"in vec3 exColour;\n" +
			"out vec4 fragColor;\n" +
			"void main() {\n" +
			"  fragColor = vec4(exColour, 1.0);\n" + //vec4(0.0, 0.5, 0.5, 1.0);\n" +
			"}\n";
	

	public static final String VS_Simple =
			"#version 330\n" +
			"layout (location=0) in vec3 pos;\n" +
			"void main() {\n" +
			"  gl_Position = vec4(pos, 1.0);\n" +
			"}\n";
	
	public static final String FS_Simple =
			"#version 330\n" +
			"out vec4 fragColor;\n" +
			"void main() {\n" +
			"  fragColor = vec4(0.0, 0.5, 0.5, 1.0);\n" +
			"}\n";
	
}
