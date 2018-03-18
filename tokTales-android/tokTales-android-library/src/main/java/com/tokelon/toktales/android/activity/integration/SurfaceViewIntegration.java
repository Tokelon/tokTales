package com.tokelon.toktales.android.activity.integration;

import com.tokelon.toktales.android.render.opengl.RenderGLSurfaceView;
import com.tokelon.toktales.android.render.opengl.program.OpenGLRenderer;
import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.core.game.IGame;

public class SurfaceViewIntegration implements ISurfaceViewIntegration {
	
	
	private final OpenGLRenderer renderer;
	
	public SurfaceViewIntegration(IGame game, IEngine engine) {
		renderer = new OpenGLRenderer(game, engine);
	}
	
	
	@Override
	public void integrateRenderView(RenderGLSurfaceView renderView) {
		renderView.setMainRenderer(renderer);
	}
	
}
