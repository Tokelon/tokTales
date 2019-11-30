package com.tokelon.toktales.desktop.lwjgl.ui;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.render.ISurface;
import com.tokelon.toktales.core.engine.render.Surface;
import com.tokelon.toktales.core.game.IGame;
import com.tokelon.toktales.core.game.screen.view.AccurateViewport;
import com.tokelon.toktales.desktop.render.IWindowRenderer;
import com.tokelon.toktales.desktop.ui.window.IWindow;

public class LWJGLWindowRenderer implements IWindowRenderer {


	private ISurface surface;

	private IWindow window;
	
	private IGame game;
	
	public LWJGLWindowRenderer(IEngineContext engineContext) {
		this.game = engineContext.getGame();
	}
	
	
	@Override
	public ISurface create(IWindow window) {
		this.window = window;
		
		window.makeContextCurrent();
		
		GL.createCapabilities();
		
		
		this.surface = createSurface(window.getTitle(), window.getWidth(), window.getHeight());
		return surface;
	}

	protected ISurface createSurface(String name, int windowWidth, int windowHeight) {
		AccurateViewport newMasterViewport = new AccurateViewport();
		newMasterViewport.setSize(windowWidth, windowHeight);
		
		
		float glViewportWidth = windowWidth;
		float glViewportHeight = windowHeight;
		
		Matrix4f projMatrix = new Matrix4f().ortho(
				0.0f, (float)glViewportWidth,
				//0.0f - 1.0f, (float)glViewportWidth - 1.0f,
				
				//0.0f - 1.0f, (float)glViewportHeight - 1.0f,	// Normal y axis (up)
				//(float)glViewportHeight - 1.0f, 0.0f - 1.0f,	// Flip y axis
				(float)glViewportHeight, 0.0f,	// Flip y axis
				
				0.0f, 50.0f
				);
		
		ISurface surface = new Surface(name, newMasterViewport, projMatrix);
		
		return surface;
	}
	
	
	@Override
	public void destroy() {
		// TODO: Something?
	}
	
	
	public void prepareFrame() {
		window.makeContextCurrent();

		
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
	}
	
	@Override
	public void drawFrame() {
		game.getGameControl().updateGame();
		
		game.getGameControl().renderGame();
	}
	
	@Override
	public void commitFrame() {
		window.swapBuffers();		
	}
	
	
	@Override
	public IWindow getWindow() {
		return window;
	}
	
}
