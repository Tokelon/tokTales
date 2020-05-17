package com.tokelon.toktales.desktop.lwjgl.ui;

import javax.inject.Inject;

import org.lwjgl.glfw.GLFWFramebufferSizeCallbackI;
import org.lwjgl.opengl.GL;

import com.tokelon.toktales.core.screen.surface.ISurfaceHandler;
import com.tokelon.toktales.core.screen.surface.ISurfaceManager;
import com.tokelon.toktales.core.screen.surface.SurfaceHandler;
import com.tokelon.toktales.desktop.lwjgl.input.IGLFWInputRegistration;
import com.tokelon.toktales.desktop.lwjgl.render.GLSurfaceController;
import com.tokelon.toktales.desktop.render.IWindowRenderer;
import com.tokelon.toktales.desktop.ui.window.IWindow;

public class LWJGLWindowRenderer implements IWindowRenderer {
	// Make abstract including drawFrame and prepareFrame ?


	private boolean hasContext = false;
	private FramebufferSizeCallback framebufferSizeCallback;
	private IWindow window;

	private final IGLFWInputRegistration inputRegistration;
	private final ISurfaceHandler surfaceHandler;

	@Inject
	public LWJGLWindowRenderer(ISurfaceManager surfaceManager, IGLFWInputRegistration inputRegistration) {
		this.surfaceHandler = new SurfaceHandler(surfaceManager, new GLSurfaceController());
		this.inputRegistration = inputRegistration;
	}


	@Override
	public void create(IWindow window) {
		this.window = window;

		this.framebufferSizeCallback = new FramebufferSizeCallback();
		inputRegistration.registerFramebufferSizeCallback(window.getId(), framebufferSizeCallback);

		surfaceHandler.setSurfaceName(window.getTitle());
	}

	@Override
	public void createContext() {
		getWindow().makeContextCurrent();

		GL.createCapabilities();


		// Determine frame buffer size, which can be different from the window size
		int width = window.getFrameBufferWidth();
		int height = window.getFrameBufferHeight();

		surfaceHandler.createSurface(width, height);
		surfaceHandler.publishSurface();

		hasContext = true;
	}

	@Override
	public void destroyContext() {
		hasContext = false;

		surfaceHandler.recallSurface();

		window.detachContext();
	}

	@Override
	public void destroy() {
		inputRegistration.unregisterFramebufferSizeCallback(window.getId(), framebufferSizeCallback);
		this.framebufferSizeCallback = null;

		GL.setCapabilities(null);
	}


	@Override
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
