package com.tokelon.toktales.android.activity.integration;

import javax.inject.Inject;

import com.tokelon.toktales.android.render.opengl.GLSurfaceController;
import com.tokelon.toktales.android.render.opengl.IGLRenderView;
import com.tokelon.toktales.android.render.opengl.program.IOpenGLRenderer;
import com.tokelon.toktales.android.render.opengl.program.IOpenGLRenderer.IOpenGLRendererFactory;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.tools.android.activity.integration.IIntegratedActivity;

public class SurfaceViewIntegration implements ISurfaceViewIntegration {


	private IGLRenderView renderView;
	
	private IOpenGLRenderer renderer;
	
	
	private final ILogger logger;
	private final ILogging logging;
	private final IOpenGLRendererFactory rendererFactory;
	
	@Inject
	public SurfaceViewIntegration(ILogging logging, IOpenGLRendererFactory rendererFactory) {
		this.logger = logging.getLogger(getClass());
		this.logging = logging;
		this.rendererFactory = rendererFactory;
	}
	
	
	@Override
	public void setRenderSurfaceName(String surfaceName) {
		renderer.setSurfaceName(surfaceName); // TODO: Catch renderer null
	}
	
	@Override
	public void integrateRenderView(IGLRenderView renderView) {
		this.renderView = renderView;
		this.renderer = rendererFactory.create(new GLSurfaceController(renderView));
		
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
