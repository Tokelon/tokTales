package com.tokelon.toktales.desktop.lwjgl;

import org.lwjgl.glfw.GLFW;

import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineLooper;
import com.tokelon.toktales.desktop.render.IWindowRenderer;

public class DefaultLWJGLLooper implements IEngineLooper {


	private final IWindowRenderer renderer;

	public DefaultLWJGLLooper(IWindowRenderer renderer) {
		this.renderer = renderer;
	}
	
	
	@Override
	public void loop() throws EngineException {
		/* Run the rendering loop until the user has attempted to close the window,
		 * or has pressed the ESCAPE key.
		 */
		while (!renderer.getWindow().shouldClose()) {
			renderer.prepareFrame();
			
			renderer.drawFrame();
			
			renderer.commitFrame();

			
			// Poll for window events
			GLFW.glfwPollEvents();
		}
	}

}
