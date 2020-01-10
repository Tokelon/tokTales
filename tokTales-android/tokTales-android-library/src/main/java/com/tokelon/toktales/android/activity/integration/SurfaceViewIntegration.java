package com.tokelon.toktales.android.activity.integration;

import javax.inject.Inject;

import com.tokelon.toktales.android.render.IRenderView;
import com.tokelon.toktales.android.render.IRenderViewAdapter;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.tools.android.activity.integration.IIntegratedActivity;

public class SurfaceViewIntegration implements ISurfaceViewIntegration {


	private String surfaceName = null;
	
	private IRenderView renderView;
	
	private final ILogger logger;
	private final IRenderViewAdapter renderViewAdapter;
	
	@Inject
	public SurfaceViewIntegration(ILogging logging, IRenderViewAdapter renderViewAdapter) {
		this.logger = logging.getLogger(getClass());
		this.renderViewAdapter = renderViewAdapter;
	}
	
	
	@Override
	public void setRenderSurfaceName(String surfaceName) {
		this.surfaceName = surfaceName;
	}
	
	@Override
	public void integrateRenderView(IRenderView renderView) {
		this.renderView = renderView;
		
		if(surfaceName != null) {
			renderView.setViewName(surfaceName);
		}
		
		renderView.setRenderViewAdapter(renderViewAdapter);
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
