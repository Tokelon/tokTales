package com.tokelon.toktales.core.game.states;

import com.tokelon.toktales.core.game.screen.IStateRender;

public interface IGameStateConfigurator<T extends IGameState> {

	// IGameStateCompleter
	// IGameStateTweaker
	
	//public void configureBeforeEngage();
	//public void configureAfterEngage();
	
	public void configureState(T gamestate);
	
	
	public default IStateRender createStateRender(T gamestate) { return null; }
	
	public default IGameStateInput createStateInput(T gamestate) { return null; }
	
	public default IGameStateInputHandler createStateInputHandler(T gamestate) { return null; }
	
	public default IControlScheme createStateControlScheme(T gamestate) { return null; }
	
	public default IControlHandler createStateControlHandler(T gamestate) { return null; }

	
}