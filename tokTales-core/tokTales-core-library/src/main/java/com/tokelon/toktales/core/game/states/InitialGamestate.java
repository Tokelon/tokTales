package com.tokelon.toktales.core.game.states;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.inject.ForClass;

public class InitialGamestate extends BaseGamestate<IGameScene> {


	@Inject
	public InitialGamestate(@ForClass(InitialGamestate.class) IGameStateInputHandler inputHandler) {
		super(null, inputHandler, null, null);
	}
    
}
