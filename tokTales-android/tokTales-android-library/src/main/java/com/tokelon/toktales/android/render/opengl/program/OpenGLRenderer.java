package com.tokelon.toktales.android.render.opengl.program;

import javax.inject.Inject;

import org.joml.Matrix4f;

import com.tokelon.toktales.android.game.screen.UIRenderer;
import com.tokelon.toktales.android.input.AndroidInputDriver;
import com.tokelon.toktales.android.input.IAndroidInputDriver;
import com.tokelon.toktales.android.render.IRenderViewAdapter;
import com.tokelon.toktales.android.render.opengl.gl20.AndroidGL11;
import com.tokelon.toktales.android.render.tools.IUIControl;
import com.tokelon.toktales.android.render.tools.IUIControl.IUIControlFactory;
import com.tokelon.toktales.android.render.tools.IUIOverlay;
import com.tokelon.toktales.android.render.tools.IUIOverlayProvider;
import com.tokelon.toktales.android.render.tools.UIConfiguration;
import com.tokelon.toktales.android.render.tools.UIOverlay;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.core.engine.render.IRenderService;
import com.tokelon.toktales.core.engine.render.ISurfaceController;
import com.tokelon.toktales.core.engine.render.Surface;
import com.tokelon.toktales.core.game.IGame;
import com.tokelon.toktales.core.game.screen.view.AccurateViewport;
import com.tokelon.toktales.core.game.screen.view.DefaultViewTransformer;
import com.tokelon.toktales.core.game.screen.view.IScreenViewport;
import com.tokelon.toktales.core.render.opengl.gl20.GLErrorUtils;
import com.tokelon.toktales.tools.core.objects.options.NamedOptionsImpl;

import android.graphics.Color;
import android.opengl.GLES20;
import android.view.MotionEvent;

public class OpenGLRenderer implements IRenderViewAdapter, IUIOverlayProvider {
	// TODO: Refactor all this!
	// TODO: Maybe pack all the GL viewport functionality into a separate class like GLViewport or MasterViewport


	public static final String DEFAULT_SURFACE_NAME = "OpenGLRenderer_Surface";
	
	private static final int CLEAR_COLOR = Color.BLACK;
	

	private final Object surfaceLock = new Object();
	
	private final NamedOptionsImpl drawOptions = new NamedOptionsImpl();

	
	private boolean checkGL = true;

	private boolean shouldProvideUI = true;

	private String surfaceName = DEFAULT_SURFACE_NAME;
	
	
	private AccurateViewport uiControlViewport;
	private AccurateViewport uiOverlayViewport;

	
	private Surface currentSurface;
	private IScreenViewport currentMasterViewport;
	private UIOverlay currentUIOverlay;
	


	private final GLErrorUtils glErrorUtils;
	private final UIRenderer uiRenderer;
	private final IAndroidInputDriver androidInputDriver;
	
	private final IGame game;
	private final IRenderService renderService;
	private final IUIControl uiControl;
	private final ISurfaceController surfaceController;

	public OpenGLRenderer(ILogging logging, IGame game, IRenderService renderService, IUIControlFactory uiControlFactory, ISurfaceController surfaceController) {
		this.renderService = renderService;
		this.game = game;
		this.uiControl = uiControlFactory.create(this);
		this.surfaceController = surfaceController;

		
		this.androidInputDriver = new AndroidInputDriver(uiControl);
		this.uiRenderer = new UIRenderer(logging, this);
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
			
			// Call context created for our UI renderer
			uiRenderer.contextCreated();
			
			
			// Surface Callbacks
			currentSurface = new Surface(surfaceName, new AccurateViewport(), new Matrix4f());
			renderService.getSurfaceHandler().publishSurface(currentSurface, surfaceController);
			
			
			
			/* Do GL Stuff */
			
			GLES20.glClearColor(Color.red(CLEAR_COLOR) / 255.0f, Color.green(CLEAR_COLOR) / 255.0f, Color.blue(CLEAR_COLOR) / 255.0f, Color.alpha(CLEAR_COLOR) / 255.0f);
			
			// Disable depth testing
			//GLES20.glDisable(GLES20.GL_DEPTH_TEST);		// Use depth ?
			//GLES20.glDepthMask(false);
			//GLES20.glEnable( GLES20.GL_DEPTH_TEST );
			//GLES20.glDepthFunc( GLES20.GL_LEQUAL );
			//GLES20.glDepthMask(true);
			
			// Disable backface culling
			//GLES20.glDisable(GLES20.GL_CULL_FACE);
			
			GLES20.glEnable(GLES20.GL_BLEND);
			GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
			

			
			
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
			
			
			// Debug values for GL Viewport
			/* /
			glViewportWidth = (int) (width * 0.8f);
			glViewportHeight = (int) (height * 0.8f);
			glViewportOffsetHorizontal = (int) (width * 0.1f);
			glViewportOffsetVertical = (int) (height * 0.1f);
			/* */


			
			/* Do GL Stuff */
			
			
			//GLES20.glViewport(newViewport.getLeft(), newViewport.getTop(), newViewport.getRight(), newViewport.getBottom());

			int viewportX = glViewportOffsetHorizontal;
			int viewportY = height - glViewportHeight - glViewportOffsetVertical;
			int viewportWidth = glViewportWidth;
			int viewportHeight = glViewportHeight;
			
			GLES20.glViewport(viewportX, viewportY, viewportWidth, viewportHeight);

			
	
			/* Setup projection matrix
			 * Only the person who sets the GL Viewport knows how to setup the projection matrix
			 * (Essentially knows what the aspect ration is) 
			 */
			
			//Matrix.setIdentityM(glMatrixProjection, 0);
			
			// The -1 value is being subtracted so that the rendering starts at position 0 (and the border is at -1)
			/*
			Matrix.orthoM(glMatrixProjection, 0, 
					(float)newViewport.getLeft() - 1.0f, (float)newViewport.getRight() - 1.0f,
					(float)newViewport.getBottom() - 1.0f, (float)newViewport.getTop() - 1.0f,
					0.0f, 50.0f
			);
			*/

			/*
			float[] glMatrixProjection = new float[16];
			// We can just use hardcoded 0 for the min values
			Matrix.orthoM(glMatrixProjection, 0, 
					0.0f - 1.0f, (float)glViewportWidth - 1.0f,
					//0.0f - 1.0f, (float)glViewportHeight - 1.0f,	// Normal y axis (up)
					(float)glViewportHeight - 1.0f, 0.0f - 1.0f,	// Flip y axis
					0.0f, 50.0f
			);
			*/
			
			/*
			Matrix4f projMatrix = new Matrix4f().ortho(
					0.0f - 1.0f, (float)glViewportWidth - 1.0f,
					//0.0f - 1.0f, (float)glViewportHeight - 1.0f,	// Normal y axis (up)
					(float)glViewportHeight - 1.0f, 0.0f - 1.0f,	// Flip y axis
					0.0f, 50.0f
					);
			*/
			
			Matrix4f projMatrix = new Matrix4f().ortho(
					0.0f, (float)glViewportWidth,
					(float)glViewportHeight, 0.0f,	// Flip y axis
					0.0f, 50.0f
					);
			
			
			/* Create new master viewport */
			AccurateViewport newMasterViewport = new AccurateViewport();
			newMasterViewport.setSize(glViewportWidth, glViewportHeight);
			
			currentMasterViewport = newMasterViewport;
			
			
			
			/* Debug values for Master Viewport /
			newMasterViewport.setSize(glViewportWidth / 2, glViewportHeight / 2);
			newMasterViewport.setOffset(glViewportWidth / 4, glViewportHeight / 4);
			/* */

			
			
			
			// UIControl viewport (independent of master viewport)
			uiControlViewport = new AccurateViewport();
			uiControlViewport.setSize(glViewportWidth, glViewportHeight);
			// Set the inverted viewport offset values because the uiControl coordinates are 0 based
			// So we want to do the opposite translation
			uiControlViewport.setOffset(-glViewportOffsetHorizontal, -glViewportOffsetVertical);

			
			// UIOverlay viewport
			uiOverlayViewport = new AccurateViewport();
			uiOverlayViewport.setSize(glViewportWidth, glViewportHeight);


			
			/* Create UI overlay */
			UIConfiguration uiConfig = new UIConfiguration();
			// Pass the OVERLAY viewport
			currentUIOverlay = UIOverlay.createFromViewportAndConfiguration(uiOverlayViewport, uiConfig);

	
			// Pass the CONTROL viewport
			uiControl.setViewport(uiControlViewport);

			
			// Call context changed for our UI renderer
			DefaultViewTransformer viewTransformer = new DefaultViewTransformer(null, uiOverlayViewport);	// Ok to pass null here?
			uiRenderer.contextChanged(viewTransformer, projMatrix);

			
			
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
		
		
		if(shouldProvideUI) {
			uiRenderer.prepare(0);	// Does nothing
			
			uiRenderer.drawFull(drawOptions);
		}
	}

	
	
	@Override
	public boolean onTouch(MotionEvent motionEvent) {
		if(!shouldProvideUI) {
			return false;
		}
		
		// This should normally be done here and not when the surface changes (for synchronization reasons)
		// if(new viewport) mUIControl.set(newViewport)
		
		
		return uiControl.onTouch(motionEvent);
	}


	@Override
	public void onResume() {
		uiControl.startControl();
	}

	@Override
	public void onPause() {
		uiControl.stopControl();
	}

	
	
	@Override
	public boolean hasUIOverlay() {
		return currentUIOverlay != null;
	}
	
	@Override
	public IUIOverlay getUIOverlay() {
		return currentUIOverlay;
	}
	
	
	public static class OpenGLRendererFactory implements IRenderViewAdapterFactory {
		private final ILogging logging;
		private final IGame game;
		private final IRenderService renderService;
		private final IUIControlFactory uiControlFactory;

		@Inject
		public OpenGLRendererFactory(ILogging logging, IGame game, IRenderService renderService, IUIControlFactory uiControlFactory) {
			this.logging = logging;
			this.game = game;
			this.renderService = renderService;
			this.uiControlFactory = uiControlFactory;
		}
		
		@Override
		public IRenderViewAdapter create(ISurfaceController surfaceController) {
			return new OpenGLRenderer(logging, game, renderService, uiControlFactory, surfaceController);
		}
	}
	
}
