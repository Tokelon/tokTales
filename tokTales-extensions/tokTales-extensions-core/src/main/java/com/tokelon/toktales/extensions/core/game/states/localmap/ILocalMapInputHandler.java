package com.tokelon.toktales.extensions.core.game.states.localmap;

import com.tokelon.toktales.core.game.states.IGameStateInputHandler;

public interface ILocalMapInputHandler extends IGameStateInputHandler {

	
	public interface ILocalMapInputHandlerFactory {
		
		public ILocalMapInputHandler create(ILocalMapGamestate gamestate);
	}
	
}