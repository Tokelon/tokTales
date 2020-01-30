package com.tokelon.toktales.extensions.core.game.state.localmap;

import com.tokelon.toktales.core.game.state.IGameStateInputHandler;

public interface ILocalMapInputHandler extends IGameStateInputHandler {

	
	public interface ILocalMapInputHandlerFactory {
		
		public ILocalMapInputHandler create(ILocalMapGamestate gamestate);
	}
	
}
