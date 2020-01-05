package com.tokelon.toktales.android.render.opengl;

import javax.inject.Inject;

import com.tokelon.toktales.android.render.IViewRenderer;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.core.engine.render.IRenderService;
import com.tokelon.toktales.core.engine.render.ISurfaceController;
import com.tokelon.toktales.core.game.IGame;

public class GLGameViewRenderer extends GLViewRenderer {


	private final IGame game;

	public GLGameViewRenderer(ILogging logging, IGame game, IRenderService renderService, ISurfaceController surfaceController) {
		super(logging, renderService, surfaceController);
		
		this.game = game;
	}

	
	@Override
	public void onDrawFrame() {
		super.onDrawFrame();
		
		game.getGameControl().renderGame();
	}
	
	
	
	public static class GLGameViewRendererFactory implements IViewRendererFactory {
		private final ILogging logging;
		private final IGame game;
		private final IRenderService renderService;

		@Inject
		public GLGameViewRendererFactory(ILogging logging, IGame game, IRenderService renderService) {
			this.logging = logging;
			this.game = game;
			this.renderService = renderService;
		}
		
		@Override
		public IViewRenderer create(ISurfaceController surfaceController) {
			return new GLGameViewRenderer(logging, game, renderService, surfaceController);
		}
	}
	
}
