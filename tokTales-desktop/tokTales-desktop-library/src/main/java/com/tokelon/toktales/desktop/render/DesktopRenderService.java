package com.tokelon.toktales.desktop.render;

import java.util.Map;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.AbstractEngineService;
import com.tokelon.toktales.core.engine.inject.annotation.services.RenderServiceExtensions;
import com.tokelon.toktales.core.engine.render.IRenderAccess;
import com.tokelon.toktales.core.engine.render.IRenderService;
import com.tokelon.toktales.core.engine.render.ISurfaceHandler;

public class DesktopRenderService extends AbstractEngineService implements IRenderService {


	private final ISurfaceHandler surfaceHandler;
	private final IRenderAccess renderAccess;
	
	public DesktopRenderService(ISurfaceHandler surfaceHandler, IRenderAccess renderAccess) {
		this.surfaceHandler = surfaceHandler;
		this.renderAccess = renderAccess;
	}
	
	@Inject
	public DesktopRenderService(ISurfaceHandler surfaceHandler, IRenderAccess renderAccess, @RenderServiceExtensions Map<String, IServiceExtension> extensions) {
		super(extensions);
		
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
