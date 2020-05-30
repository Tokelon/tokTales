package com.tokelon.toktales.desktop.lwjgl;

import org.lwjgl.glfw.GLFW;

import com.tokelon.toktales.core.engine.IEngineInputProcessor;

/** LWJGL implementation of {@link IEngineInputProcessor}.
 */
public class LWJGLInputProcessor implements IEngineInputProcessor {


	@Override
	public void process() {
		// Poll for window events
		GLFW.glfwPollEvents();
	}

}
