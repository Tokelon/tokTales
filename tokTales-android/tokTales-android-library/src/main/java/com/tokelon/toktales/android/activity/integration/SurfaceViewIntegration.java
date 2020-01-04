package com.tokelon.toktales.android.activity.integration;

import javax.inject.Inject;

import com.tokelon.toktales.android.render.IRenderViewAdapter;
import com.tokelon.toktales.android.render.IRenderViewAdapter.IRenderViewAdapterFactory;
import com.tokelon.toktales.android.render.opengl.GLSurfaceController;
import com.tokelon.toktales.android.render.opengl.IGLRenderView;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.tools.android.activity.integration.IIntegratedActivity;

public class SurfaceViewIntegration implements ISurfaceViewIntegration {


	private IGLRenderView renderView;
	
	private IRenderViewAdapter adapter;
	
	private final ILogger logger;
	private final IRenderViewAdapterFactory adapterFactory;
	
	@Inject
	public SurfaceViewIntegration(ILogging logging, IRenderViewAdapterFactory adapterFactory) {
		this.logger = logging.getLogger(getClass());
		this.adapterFactory = adapterFactory;
	}
	
	
	@Override
	public void setRenderSurfaceName(String surfaceName) {
		adapter.setSurfaceName(surfaceName); // TODO: Catch adapter null
	}
	
	@Override
	public void integrateRenderView(IGLRenderView renderView) {
		this.renderView = renderView;
		this.adapter = adapterFactory.create(new GLSurfaceController(renderView));
		
		renderView.setRenderViewAdapter(adapter);
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
