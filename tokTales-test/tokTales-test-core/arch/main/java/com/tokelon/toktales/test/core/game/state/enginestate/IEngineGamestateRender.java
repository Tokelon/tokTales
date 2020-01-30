package com.tokelon.toktales.test.core.game.state.enginestate;

import com.tokelon.toktales.core.game.state.render.IStateRender;

public interface IEngineGamestateRender extends IStateRender {

	
	public interface IEngineGamestateRenderFactory {
		
		public IEngineGamestateRender create(IEngineGamestate gamestate);
	}
	
}
