package com.tokelon.toktales.test.desktop.game.states.enginestate;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;
import com.tokelon.toktales.desktop.game.states.IDesktopGameStateInputHandler;
import com.tokelon.toktales.test.core.game.states.enginestate.IEngineGamestate;
import com.tokelon.toktales.test.core.game.states.enginestate.IEngineGamestateInputHandler;

public class DesktopEngineGamestateInputHandler implements IEngineGamestateInputHandler, IDesktopGameStateInputHandler {

	
	private final IEngineGamestate gamestate;
	
	@Inject
	public DesktopEngineGamestateInputHandler(@Assisted IEngineGamestate gamestate) {
		this.gamestate = gamestate;
	}
	
	
	// For testing purposes only
	public IEngineGamestate getGamestate() {
		return gamestate;
	}

}
