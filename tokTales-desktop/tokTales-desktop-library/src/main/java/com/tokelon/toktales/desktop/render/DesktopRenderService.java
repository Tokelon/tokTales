package com.tokelon.toktales.desktop.render;

import java.util.Map;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.AbstractEngineService;
import com.tokelon.toktales.core.engine.inject.annotation.services.RenderServiceExtensions;
import com.tokelon.toktales.core.engine.render.IRenderAccess;
import com.tokelon.toktales.core.engine.render.IRenderService;
import com.tokelon.toktales.core.screen.surface.ISurfaceManager;

public class DesktopRenderService extends AbstractEngineService implements IRenderService {


	private final ISurfaceManager surfaceManager;
	private final IRenderAccess renderAccess;
	
	public DesktopRenderService(ISurfaceManager surfaceManager, IRenderAccess renderAccess) {
		this.surfaceManager = surfaceManager;
		this.renderAccess = renderAccess;
	}
	
	@Inject
	public DesktopRenderService(ISurfaceManager surfaceManager, IRenderAccess renderAccess, @RenderServiceExtensions Map<String, IServiceExtension> extensions) {
		super(extensions);
		
		this.surfaceManager = surfaceManager;
		this.renderAccess = renderAccess;
	}
	
	
	@Override
	public ISurfaceManager getSurfaceManager() {
		return surfaceManager;
	}


	@Override
	public IRenderAccess getRenderAccess() {
		return renderAccess;
	}
	
}
