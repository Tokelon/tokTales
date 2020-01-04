package com.tokelon.toktales.android.render.opengl;

import javax.inject.Inject;

import org.joml.Matrix4f;

import com.tokelon.toktales.android.render.IViewRenderer;
import com.tokelon.toktales.android.render.opengl.gl20.AndroidGL11;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.core.engine.render.IRenderService;
import com.tokelon.toktales.core.engine.render.ISurfaceController;
import com.tokelon.toktales.core.engine.render.Surface;
import com.tokelon.toktales.core.game.IGame;
import com.tokelon.toktales.core.game.screen.view.AccurateViewport;
import com.tokelon.toktales.core.game.screen.view.IScreenViewport;
import com.tokelon.toktales.core.render.opengl.gl20.GLErrorUtils;

import android.opengl.GLES20;

public class GLViewRenderer implements IViewRenderer {


	public static final String DEFAULT_SURFACE_NAME = "GLViewRendererSurface";
	
	private final boolean checkGL = true;
	
	
	private final Object surfaceLock = new Object();

	
	private String surfaceName = DEFAULT_SURFACE_NAME;
	
	private Surface currentSurface;
	private IScreenViewport currentMasterViewport;

	
	private final GLErrorUtils glErrorUtils;
	
	private final IGame game;
	private final IRenderService renderService;
	private final ISurfaceController surfaceController;

	public GLViewRenderer(ILogging logging, IGame game, IRenderService renderService, ISurfaceController surfaceController) {
		this.renderService = renderService;
		this.game = game;
		this.surfaceController = surfaceController;
		
		this.glErrorUtils = new GLErrorUtils(logging, new AndroidGL11(), checkGL);
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
			
			// Surface Callbacks
			currentSurface = new Surface(surfaceName, new AccurateViewport(), new Matrix4f());
			renderService.getSurfaceHandler().publishSurface(currentSurface, surfaceController);
			
			glErrorUtils.assertNoGLErrors();
		}
	}

	
	@Override
	public void onSurfaceChanged(int width, int height) {
		synchronized(surfaceLock) {
			glErrorUtils.assertNoGLErrors();

			
			// Default values for the GL Viewport
			int glViewportOffsetHorizontal = 0;
			int glViewportOffsetVertical = 0;
			int glViewportWidth = width;
			int glViewportHeight = height;
			
			
			/* Do GL Stuff */

			int viewportX = glViewportOffsetHorizontal;
			int viewportY = height - glViewportHeight - glViewportOffsetVertical;
			int viewportWidth = glViewportWidth;
			int viewportHeight = glViewportHeight;
			
			GLES20.glViewport(viewportX, viewportY, viewportWidth, viewportHeight);

			
			
			Matrix4f projMatrix = new Matrix4f().ortho(
					0.0f, (float)glViewportWidth,
					(float)glViewportHeight, 0.0f, // Flip y axis
					0.0f, 50.0f // TODO: Change zFar default
			);
			
			
			/* Create new master viewport */
			AccurateViewport newMasterViewport = new AccurateViewport();
			newMasterViewport.setSize(glViewportWidth, glViewportHeight);
			
			currentMasterViewport = newMasterViewport;
			
			
			// Surface Callbacks
			currentSurface.update(currentMasterViewport, projMatrix);
			renderService.getSurfaceHandler().updateSurface(currentSurface);
			
			
			glErrorUtils.assertNoGLErrors();
		}
		
	}
	
	
	@Override
	public void onSurfaceDestroyed() {
		// TODO: Null fields?
		renderService.getSurfaceHandler().recallSurface(currentSurface);
	}

	
	@Override
	public void onDrawFrame() {
		game.getGameControl().updateGame();
		
		game.getGameControl().renderGame();
	}

	
	
	public static class GLViewRendererFactory implements IViewRendererFactory {
		private final ILogging logging;
		private final IGame game;
		private final IRenderService renderService;

		@Inject
		public GLViewRendererFactory(ILogging logging, IGame game, IRenderService renderService) {
			this.logging = logging;
			this.game = game;
			this.renderService = renderService;
		}
		
		@Override
		public IViewRenderer create(ISurfaceController surfaceController) {
			return new GLViewRenderer(logging, game, renderService, surfaceController);
		}
	}
	
}
