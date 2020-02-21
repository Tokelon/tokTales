package com.tokelon.toktales.desktop.lwjgl.ui;

import javax.inject.Inject;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWFramebufferSizeCallbackI;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

import com.tokelon.toktales.core.screen.surface.ISurfaceHandler;
import com.tokelon.toktales.core.screen.surface.ISurfaceManager;
import com.tokelon.toktales.core.screen.surface.SurfaceHandler;
import com.tokelon.toktales.desktop.lwjgl.render.GLSurfaceController;
import com.tokelon.toktales.desktop.render.IWindowRenderer;
import com.tokelon.toktales.desktop.ui.window.IWindow;

public class LWJGLWindowRenderer implements IWindowRenderer {
	// Make abstract including drawFrame and prepareFrame ?


	private boolean hasContext = false;
	private FramebufferSizeCallback framebufferSizeCallback;
	private IWindow window;
	
	private final ISurfaceHandler surfaceHandler;
	
	@Inject
	public LWJGLWindowRenderer(ISurfaceManager surfaceManager) {
		this.surfaceHandler = new SurfaceHandler(surfaceManager, new GLSurfaceController());
	}
	
	
	@Override
	public void create(IWindow window) {
		this.window = window;
		
		framebufferSizeCallback = new FramebufferSizeCallback();
		GLFW.glfwSetFramebufferSizeCallback(window.getId(), framebufferSizeCallback);
		
		surfaceHandler.setSurfaceName(window.getTitle());
	}
	
	@Override
	public void createContext() {
		getWindow().makeContextCurrent();
		
		GL.createCapabilities();
		
		
		// Determine current frame buffer size, which can be different from the window size
		int[] width = new int[1];
		int[] height = new int[1];
		GLFW.glfwGetFramebufferSize(window.getId(), width, height);
		
		surfaceHandler.createSurface(width[0], height[0]);
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
		GLFW.glfwSetFramebufferSizeCallback(getWindow().getId(), null);
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
	
	

	protected class FramebufferSizeCallback implements GLFWFramebufferSizeCallbackI {

		@Override
		public void invoke(long window, int width, int height) {
			if(hasContext && getWindow().getId() == window) {
				surfaceHandler.updateSurface(width, height);
			}
		}
	}
	
}
