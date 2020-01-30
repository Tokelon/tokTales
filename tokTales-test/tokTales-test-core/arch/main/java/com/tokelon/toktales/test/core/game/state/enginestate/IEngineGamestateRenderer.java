package com.tokelon.toktales.test.core.game.state.enginestate;

import com.tokelon.toktales.core.game.state.render.IGameStateRenderer;

public interface IEngineGamestateRenderer extends IGameStateRenderer {

	
	public interface IEngineGamestateRendererFactory {
		
		public IEngineGamestateRenderer create(IEngineGamestate gamestate);
	}
	
}
