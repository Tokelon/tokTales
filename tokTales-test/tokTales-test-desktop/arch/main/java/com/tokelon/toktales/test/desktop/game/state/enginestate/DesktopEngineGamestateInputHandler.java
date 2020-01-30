package com.tokelon.toktales.test.desktop.game.state.enginestate;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;
import com.tokelon.toktales.desktop.game.state.IDesktopGameStateInputHandler;
import com.tokelon.toktales.test.core.game.state.enginestate.IEngineGamestate;
import com.tokelon.toktales.test.core.game.state.enginestate.IEngineGamestateInputHandler;

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
