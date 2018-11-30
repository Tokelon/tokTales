package com.tokelon.toktales.test.desktop.game.states.userstate;

import com.tokelon.toktales.desktop.game.states.IDesktopGameStateInputHandler;

public class DesktopUserGamestateInputHandler implements IDesktopGameStateInputHandler {

	
	@SuppressWarnings("unused")
	private final DesktopUserGamestate gamestate; // For testing purposes
	
	public DesktopUserGamestateInputHandler(DesktopUserGamestate gamestate) {
		this.gamestate = gamestate;
	}

}
