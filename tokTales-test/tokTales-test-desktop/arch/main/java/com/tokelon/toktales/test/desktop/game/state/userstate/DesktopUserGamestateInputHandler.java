package com.tokelon.toktales.test.desktop.game.state.userstate;

import com.tokelon.toktales.desktop.game.state.IDesktopGameStateInputHandler;

public class DesktopUserGamestateInputHandler implements IDesktopGameStateInputHandler {

	
	@SuppressWarnings("unused")
	private final DesktopUserGamestate gamestate; // For testing purposes
	
	public DesktopUserGamestateInputHandler(DesktopUserGamestate gamestate) {
		this.gamestate = gamestate;
	}

}
