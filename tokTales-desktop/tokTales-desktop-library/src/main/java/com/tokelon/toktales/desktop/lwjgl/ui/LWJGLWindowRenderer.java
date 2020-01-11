package com.tokelon.toktales.desktop.lwjgl.ui;

import javax.inject.Inject;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

import com.tokelon.toktales.core.engine.render.ISurfaceManager;
import com.tokelon.toktales.core.render.ISurfaceHandler;
import com.tokelon.toktales.core.render.SurfaceHandler;
import com.tokelon.toktales.desktop.lwjgl.render.GLSurfaceController;
import com.tokelon.toktales.desktop.render.IWindowRenderer;
import com.tokelon.toktales.desktop.ui.window.IWindow;

public class LWJGLWindowRenderer implements IWindowRenderer {
	// Make abstract including drawFrame and prepareFrame ?


	private boolean hasContext = false;
	private WindowSizeCallback windowSizeCallback;
	private IWindow window;
	
	private final ISurfaceHandler surfaceHandler;
	
	@Inject
	public LWJGLWindowRenderer(ISurfaceManager surfaceManager) {
		this.surfaceHandler = new SurfaceHandler(surfaceManager, new GLSurfaceController());
	}
	
	
	@Override
	public void create(IWindow window) {
		this.window = window;
		
		windowSizeCallback = new WindowSizeCallback();
		GLFW.glfwSetWindowSizeCallback(window.getId(), windowSizeCallback);
		
		surfaceHandler.setSurfaceName(window.getTitle());
	}
	
	@Override
	public void createContext() {
		getWindow().makeContextCurrent();
		
		GL.createCapabilities();
		
		
		surfaceHandler.createSurface(window.getWidth(), window.getHeight());
		surfaceHandler.publishSurface();
		
		hasContext = true;
	}
	
	@Override
	public void destroyContext() {
		hasContext = false;

		surfaceHandler.recallSurface();
		
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
				surfaceHandler.updateSurface(width, height);
			}
		}
	}
	
}
