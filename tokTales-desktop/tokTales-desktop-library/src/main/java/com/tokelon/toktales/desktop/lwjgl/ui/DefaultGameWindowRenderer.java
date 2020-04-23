package com.tokelon.toktales.desktop.lwjgl.ui;

import javax.inject.Inject;

import org.lwjgl.opengl.GL11;

import com.tokelon.toktales.core.engine.IEngineDriver;
import com.tokelon.toktales.core.screen.surface.ISurfaceManager;

public class DefaultGameWindowRenderer extends GameWindowRenderer {


	@Inject
	public DefaultGameWindowRenderer(IEngineDriver engineDriver, ISurfaceManager surfaceManager) {
		super(engineDriver, surfaceManager);
	}
	
	
	@Override
	public void prepareFrame() {
		super.prepareFrame();
		
		//GL11.glEnable(GL11.GL_DEPTH_TEST);
		//GL11.glDepthFunc(GL11.GL_LEQUAL);
		//GL11.glDepthMask(true);
		
		//GL11.glEnable(GL11.GL_ALPHA_TEST);
		//GL11.glAlphaFunc(GL11.GL_GREATER, 0.1f); // DEPRECATED

		
		//GL11.glClearColor(0.33f, 0.59f, 0.729f, 0.0f); // Light blue
		GL11.glClearColor(0f, 0f, 0f, 0f); // Black
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);


		GL11.glViewport(0, 0, getWindow().getFrameBufferWidth(), getWindow().getFrameBufferHeight());
	}

}
