package com.tokelon.toktales.android.render.opengl;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.AbstractEngineService;
import com.tokelon.toktales.core.engine.render.IRenderAccess;
import com.tokelon.toktales.core.engine.render.IRenderService;
import com.tokelon.toktales.core.engine.render.ISurfaceHandler;

public class AndroidRenderService extends AbstractEngineService implements IRenderService {

	
	private final ISurfaceHandler surfaceHandler;
	private final IRenderAccess renderAccess;
	
	@Inject
	public AndroidRenderService(ISurfaceHandler surfaceHandler, IRenderAccess renderAccess) {
		this.surfaceHandler = surfaceHandler;
		this.renderAccess = renderAccess;
	}
	
	
	@Override
	public ISurfaceHandler getSurfaceHandler() {
		return surfaceHandler;
	}


	@Override
	public IRenderAccess getRenderAccess() {
		return renderAccess;
	}
	
}
