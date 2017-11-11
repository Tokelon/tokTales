package com.tokelon.toktales.core.game.states;

import com.tokelon.toktales.core.game.screen.IStateRender;

public interface IGameStateConfigurator<T extends IGameState> {

	// IGameStateCompleter
	// IGameStateTweaker
	
	//public void configureBeforeEngage();
	//public void configureAfterEngage();
	
	public void configureState(T gamestate);
	
	
	public IStateRender createStateRender(T gamestate);
	
	public IGameStateInput createStateInput(T gamestate);
	
	public IGameStateInputHandler createStateInputHandler(T gamestate);
	
	public IControlScheme createStateControlScheme(T gamestate);
	
	public IControlHandler createStateControlHandler(T gamestate);
	
	
	
	public abstract class AbstractGamestateConfigurator<T extends IGameState> implements IGameStateConfigurator<T> {

		@Override
		public IStateRender createStateRender(T gamestate) {
			return null;
		}

		@Override
		public IGameStateInput createStateInput(T gamestate) {
			return null;
		}

		@Override
		public IGameStateInputHandler createStateInputHandler(T gamestate) {
			return null;
		}

		@Override
		public IControlScheme createStateControlScheme(T gamestate) {
			return null;
		}

		@Override
		public IControlHandler createStateControlHandler(T gamestate) {
			return null;
		}
	}
	
}
