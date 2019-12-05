package com.tokelon.toktales.desktop.lwjgl;

import org.lwjgl.glfw.GLFW;

import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.desktop.engine.DefaultEngineLooper;
import com.tokelon.toktales.desktop.render.IWindowRenderer;

public class DefaultLWJGLLooper extends DefaultEngineLooper {


	public DefaultLWJGLLooper(IWindowRenderer renderer) {
		super(renderer);
	}
	

	@Override
	protected void processInput(IEngineContext engineContext) {
		// Poll for window events
		GLFW.glfwPollEvents();
	}

}
