package com.tokelon.toktales.desktop.lwjgl.ui;

import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.game.IGame;

public class GameWindowRenderer extends LWJGLWindowRenderer {


	private IGame game;

	public GameWindowRenderer(IEngineContext engineContext) {
		super(engineContext.getEngine().getRenderService().getSurfaceHandler());
		
		this.game = engineContext.getGame();
	}
	
	
	@Override
	public void drawFrame() {
		super.drawFrame();
		
		game.getGameControl().renderGame();
	}
	
}
