package com.tokelon.toktales.android.render.opengl;

import javax.inject.Inject;

import com.tokelon.toktales.android.render.IViewRenderer;
import com.tokelon.toktales.core.engine.IEngineDriver;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.core.engine.render.IRenderService;
import com.tokelon.toktales.core.engine.render.ISurfaceController;

public class GLGameViewRenderer extends GLViewRenderer {


	private final IEngineDriver engineDriver;

	public GLGameViewRenderer(ILogging logging, IEngineDriver engineDriver, IRenderService renderService, ISurfaceController surfaceController) {
		super(logging, renderService, surfaceController);
		
		this.engineDriver = engineDriver;
	}

	
	@Override
	public void onDrawFrame() {
		super.onDrawFrame();

		engineDriver.render();
	}
	
	
	
	public static class GLGameViewRendererFactory implements IViewRendererFactory {
		private final ILogging logging;
		private final IEngineDriver engineDriver;
		private final IRenderService renderService;

		@Inject
		public GLGameViewRendererFactory(ILogging logging, IEngineDriver engineDriver, IRenderService renderService) {
			this.logging = logging;
			this.engineDriver = engineDriver;
			this.renderService = renderService;
		}
		
		@Override
		public IViewRenderer create(ISurfaceController surfaceController) {
			return new GLGameViewRenderer(logging, engineDriver, renderService, surfaceController);
		}
	}
	
}
