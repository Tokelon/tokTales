package com.tokelon.toktales.android.activity.integration;

import com.tokelon.toktales.android.render.opengl.IGLRenderView;
import com.tokelon.toktales.android.render.opengl.program.OpenGLRenderer;
import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.game.IGame;

public class SurfaceViewIntegration implements ISurfaceViewIntegration {
	
	private static final String TAG = "SurfaceViewIntegration";

	
	private IGLRenderView renderView;
	
	private final ILogger logger;
	private final OpenGLRenderer renderer;
	
	public SurfaceViewIntegration(ILogger logger, IEngine engine, IGame game) {
		this.logger = logger;
		renderer = new OpenGLRenderer(logger, engine, game);
	}
	
	
	@Override
	public void integrateRenderView(IGLRenderView renderView) {
		this.renderView = renderView;
		
		renderView.setMainRenderer(renderer);
	}
	
	
	@Override
	public void onActivityPause(IIntegratedActivity activity) {
		if(renderView == null) {
			logger.w(TAG, "No render view set: cannot pause");
		}
		else {
			renderView.onPause();	
		}
	}
	
	@Override
	public void onActivityResume(IIntegratedActivity activity) {
		if(renderView == null) {
			logger.w(TAG, "No render view set: cannot resume");
		}
		else {
			renderView.onResume();	
		}
	}
	
	@Override
	public void onActivityDestroy(IIntegratedActivity activity) {
		renderView = null;
	}
	
}
