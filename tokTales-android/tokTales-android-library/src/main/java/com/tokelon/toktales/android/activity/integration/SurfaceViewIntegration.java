package com.tokelon.toktales.android.activity.integration;

import javax.inject.Inject;

import com.tokelon.toktales.android.render.opengl.IGLRenderView;
import com.tokelon.toktales.android.render.opengl.program.OpenGLRenderer;
import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.core.game.IGame;
import com.tokelon.toktales.core.util.IObjectPool.IObjectPoolFactory;

public class SurfaceViewIntegration implements ISurfaceViewIntegration {


	private IGLRenderView renderView;
	
	private final ILogger logger;
	private final OpenGLRenderer renderer;
	
	@Inject
	public SurfaceViewIntegration(ILogging logging, IEngine engine, IGame game, IObjectPoolFactory eventPoolFactory) {
		this.logger = logging.getLogger(getClass());
		
		renderer = new OpenGLRenderer(logging, engine, game, eventPoolFactory);
	}
	
	
	@Override
	public void setRenderSurfaceName(String surfaceName) {
		renderer.setSurfaceName(surfaceName);
	}
	
	@Override
	public void integrateRenderView(IGLRenderView renderView) {
		this.renderView = renderView;
		
		renderView.setMainRenderer(renderer);
	}
	
	
	@Override
	public void onActivityPause(IIntegratedActivity activity) {
		if(renderView == null) {
			logger.warn("No render view set: cannot pause");
		}
		else {
			logger.info("Render view was paused");
			renderView.onPause();
		}
	}
	
	@Override
	public void onActivityResume(IIntegratedActivity activity) {
		if(renderView == null) {
			logger.warn("No render view set: cannot resume");
		}
		else {
			logger.info("Render view was resumed");
			renderView.onResume();
		}
	}
	
	@Override
	public void onActivityDestroy(IIntegratedActivity activity) {
		renderView = null;
	}
	
}
