package com.tokelon.toktales.test.android.game.state.enginestate;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;
import com.tokelon.toktales.android.game.state.IAndroidGameStateInputHandler;
import com.tokelon.toktales.test.core.game.state.enginestate.IEngineGamestate;
import com.tokelon.toktales.test.core.game.state.enginestate.IEngineGamestateInputHandler;

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
