package com.tokelon.toktales.android.render.opengl;

import javax.inject.Inject;

import org.joml.Matrix4f;

import com.tokelon.toktales.android.render.IViewRenderer;
import com.tokelon.toktales.android.render.opengl.gl20.AndroidGL11;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.core.engine.render.IRenderService;
import com.tokelon.toktales.core.engine.render.ISurfaceController;
import com.tokelon.toktales.core.game.screen.view.AccurateViewport;
import com.tokelon.toktales.core.render.ISurfaceManager;
import com.tokelon.toktales.core.render.SurfaceManager;
import com.tokelon.toktales.core.render.opengl.gl20.GLErrorUtils;

public class GLViewRenderer implements IViewRenderer {


	public static final String DEFAULT_SURFACE_NAME = "GLViewRendererSurface";
	
	private final boolean checkGL = true;
	
	
	private final Object surfaceLock = new Object(); // TODO: Remove if not needed

	
	private String surfaceName = DEFAULT_SURFACE_NAME;
	
	
	private final GLErrorUtils glErrorUtils;
	private final ISurfaceManager surfaceManager;
	
	public GLViewRenderer(ILogging logging, IRenderService renderService, ISurfaceController surfaceController) {
		this.glErrorUtils = new GLErrorUtils(logging, new AndroidGL11(), checkGL);
		this.surfaceManager = new SurfaceManager(renderService.getSurfaceHandler(), surfaceController);
	}
	
	
	protected ISurfaceManager getSurfaceManager() {
		return surfaceManager;
	}
	
	
	@Override
	public void setSurfaceName(String name) {
		if(name == null) {
			throw new NullPointerException();
		}
		
		this.surfaceName = name;
	}
	
	
	@Override
	public void onSurfaceCreated() {
		synchronized(surfaceLock) {
			glErrorUtils.assertNoGLErrors();
			
			surfaceManager.createSurface(surfaceName, new AccurateViewport(), new Matrix4f());
			surfaceManager.publishSurface();
			
			glErrorUtils.assertNoGLErrors();
		}
	}

	
	@Override
	public void onSurfaceChanged(int width, int height) {
		synchronized(surfaceLock) {
			glErrorUtils.assertNoGLErrors();

			surfaceManager.updateSurface(width, height);
			
			glErrorUtils.assertNoGLErrors();
		}
		
	}
	
	
	@Override
	public void onSurfaceDestroyed() {
		surfaceManager.recallSurface();
	}

	
	@Override
	public void onDrawFrame() {
		// Nothing yet
	}

	
	
	public static class GLViewRendererFactory implements IViewRendererFactory {
		private final ILogging logging;
		private final IRenderService renderService;

		@Inject
		public GLViewRendererFactory(ILogging logging, IRenderService renderService) {
			this.logging = logging;
			this.renderService = renderService;
		}
		
		@Override
		public IViewRenderer create(ISurfaceController surfaceController) {
			return new GLViewRenderer(logging, renderService, surfaceController);
		}
	}
	
}
