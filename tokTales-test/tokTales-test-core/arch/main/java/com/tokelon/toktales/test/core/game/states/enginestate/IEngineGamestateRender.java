package com.tokelon.toktales.test.core.game.states.enginestate;

import com.tokelon.toktales.core.game.states.render.IStateRender;

public interface IEngineGamestateRender extends IStateRender {

	
	public interface IEngineGamestateRenderFactory {
		
		public IEngineGamestateRender create(IEngineGamestate gamestate);
	}
	
}
