package com.tokelon.toktales.test.core.game.states.enginestate;

import com.tokelon.toktales.core.engine.render.ISurfaceHandler;
import com.tokelon.toktales.core.game.screen.IStateRender;

public interface IEngineGamestateRender extends IStateRender {

	
	public interface IEngineGamestateRenderFactory {
		
		public IEngineGamestateRender create(ISurfaceHandler surfaceHandler, IEngineGamestate gamestate);
	}
	
}
