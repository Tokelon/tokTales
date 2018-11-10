package com.tokelon.toktales.desktop.lwjgl;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.tokelon.toktales.core.engine.render.ISurface;
import com.tokelon.toktales.core.engine.render.Surface;
import com.tokelon.toktales.core.game.IGame;
import com.tokelon.toktales.core.game.screen.view.AccurateViewport;
import com.tokelon.toktales.desktop.render.IDesktopOpenGLRenderer;

public class LWJGLRenderer implements IDesktopOpenGLRenderer {
	
	
	// TODO: Use window name for surface and make settable
	public static final String DEFAULT_SURFACE_NAME = "LWJGLRenderer_Surface";
	
	private final IGame mGame;
	
	private LWJGLWindow mWindow;

	
	public LWJGLRenderer(IGame game) {
		if(game == null) {
			throw new NullPointerException();
		}
		
		this.mGame = game;
	}
	
	
	public ISurface onWindowCreated(LWJGLWindow window) {
		mWindow = window;
		
		// TODO: Set clear color
		GL11.glClearColor(0.33f, 0.59f, 0.729f, 0.0f);
		
		//GL11.glEnable(GL11.GL_DEPTH_TEST);
		//GL11.glDepthFunc(GL11.GL_LEQUAL);
		//GL11.glDepthMask(true);
		
		//GL11.glEnable(GL11.GL_ALPHA_TEST);
		//GL11.glAlphaFunc(GL11.GL_GREATER, 0.1f);	// DEPRECATED
		
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		
		GL11.glViewport(0, 0, window.getWidth(), window.getHeight());
		

		
		AccurateViewport newMasterViewport = new AccurateViewport();
		newMasterViewport.setSize(window.getWidth(), window.getHeight());
		
		
		float glViewportWidth = window.getWidth();
		float glViewportHeight = window.getHeight();
		
		Matrix4f projMatrix = new Matrix4f().ortho(
				0.0f, (float)glViewportWidth,
				//0.0f - 1.0f, (float)glViewportWidth - 1.0f,
				
				//0.0f - 1.0f, (float)glViewportHeight - 1.0f,	// Normal y axis (up)
				//(float)glViewportHeight - 1.0f, 0.0f - 1.0f,	// Flip y axis
				(float)glViewportHeight, 0.0f,	// Flip y axis
				
				0.0f, 50.0f
				);
		
		ISurface surface = new Surface(DEFAULT_SURFACE_NAME, newMasterViewport, projMatrix);
		
		return surface;
	}

	
	public void runLoop() {
		
		// Maybe do the loop outside of here
		
		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		while (!mWindow.shouldClose()) {
			
			// Draw the actual stuff
			onDrawFrame();
			
			mWindow.swapBuffers();

			// Poll for window events. The key callback above will only be
			// invoked during this call.
			GLFW.glfwPollEvents();
		}
	}
	
	
	@Override
	public void onDrawFrame() {
		mGame.getGameControl().updateGame();
		
		mGame.getGameControl().renderGame();
	}
	
}
