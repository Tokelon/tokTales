package com.tokelon.toktales.desktop.lwjgl;

import org.lwjgl.glfw.GLFW;

import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.IEngineLooper;
import com.tokelon.toktales.desktop.render.IWindowRenderer;

public class DefaultLWJGLLooper implements IEngineLooper {


	private final IWindowRenderer renderer;

	public DefaultLWJGLLooper(IWindowRenderer renderer) {
		this.renderer = renderer;
	}
	
	
	@Override
	public void loop(IEngineContext engineContext) throws EngineException {
		/* Run the rendering loop until the user has attempted to close the window,
		 * or has pressed the ESCAPE key.
		 */
		while (!renderer.getWindow().shouldClose()) {
			// Update
			engineContext.getGame().getGameControl().updateGame();

			
			// Render
			renderer.prepareFrame();
			
			renderer.drawFrame();
			
			renderer.commitFrame();

			
			// Input - poll for window events
			GLFW.glfwPollEvents();
		}
	}

}
