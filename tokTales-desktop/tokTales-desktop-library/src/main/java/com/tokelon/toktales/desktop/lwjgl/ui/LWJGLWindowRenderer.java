package com.tokelon.toktales.desktop.lwjgl.ui;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

import com.tokelon.toktales.core.engine.render.ISurfaceHandler;
import com.tokelon.toktales.core.render.ISurfaceManager;
import com.tokelon.toktales.core.render.SurfaceManager;
import com.tokelon.toktales.desktop.lwjgl.render.GLSurfaceController;
import com.tokelon.toktales.desktop.render.IWindowRenderer;
import com.tokelon.toktales.desktop.ui.window.IWindow;

public class LWJGLWindowRenderer implements IWindowRenderer {
	// Make abstract including drawFrame and prepareFrame ?


	private boolean hasContext = false;
	private WindowSizeCallback windowSizeCallback;
	private IWindow window;
	
	private final ISurfaceManager surfaceManager;
	
	public LWJGLWindowRenderer(ISurfaceHandler surfaceHandler) {
		this.surfaceManager = new SurfaceManager(surfaceHandler, new GLSurfaceController());
	}
	
	
	@Override
	public void create(IWindow window) {
		this.window = window;
		
		windowSizeCallback = new WindowSizeCallback();
		GLFW.glfwSetWindowSizeCallback(window.getId(), windowSizeCallback);
	}
	
	@Override
	public void createContext() {
		getWindow().makeContextCurrent();
		
		GL.createCapabilities();
		
		
		surfaceManager.createSurface(window.getTitle(), window.getWidth(), window.getHeight());
		surfaceManager.publishSurface();
		
		hasContext = true;
	}
	
	@Override
	public void destroyContext() {
		hasContext = false;

		surfaceManager.recallSurface();
		
		// Replace with window.detachContext() ?
		GLFW.glfwMakeContextCurrent(MemoryUtil.NULL);
	}
	
	@Override
	public void destroy() {
		GLFW.glfwSetWindowSizeCallback(getWindow().getId(), null);
	}
	
	
	public void prepareFrame() {
		// Nothing yet
	}
	
	@Override
	public void drawFrame() {
		// Nothing yet
	}
	
	@Override
	public void commitFrame() {
		getWindow().swapBuffers();
	}
	
	
	@Override
	public IWindow getWindow() {
		return window;
	}
	
	

	protected class WindowSizeCallback implements GLFWWindowSizeCallbackI {

		@Override
		public void invoke(long window, int width, int height) {
			if(hasContext) {
				surfaceManager.updateSurface(width, height);
			}
		}
	}
	
}
