package com.tokelon.toktales.test.android.game.states.enginestate;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;
import com.tokelon.toktales.android.states.IAndroidGameStateInputHandler;
import com.tokelon.toktales.test.core.game.states.enginestate.IEngineGamestate;
import com.tokelon.toktales.test.core.game.states.enginestate.IEngineGamestateInputHandler;

public class AndroidEngineGamestateInputHandler implements IEngineGamestateInputHandler, IAndroidGameStateInputHandler {

	private final IEngineGamestate gamestate;
	
	@Inject
	public AndroidEngineGamestateInputHandler(@Assisted IEngineGamestate gamestate) {
		this.gamestate = gamestate;
	}
	
	
	// For testing purposes only
	public IEngineGamestate getGamestate() {
		return gamestate;
	}
	
}
