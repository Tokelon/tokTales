package com.tokelon.toktales.core.game.state;

import javax.inject.Inject;

import com.tokelon.toktales.tools.core.sub.inject.scope.ForClass;

public class InitialGamestate extends BaseGamestate<IGameScene> {


	@Inject
	public InitialGamestate(@ForClass(InitialGamestate.class) IGameStateInputHandler inputHandler) {
		super(null, inputHandler, null, null);
	}
    
}
