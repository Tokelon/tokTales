package com.tokelon.toktales.test.android.game.states.userstate;

import com.tokelon.toktales.android.states.IAndroidGameStateInputHandler;

public class AndroidUserGamestateInputHandler implements IAndroidGameStateInputHandler {

	
	@SuppressWarnings("unused")
	private final AndroidUserGamestate gamestate; // For testing purposes
	
	public AndroidUserGamestateInputHandler(AndroidUserGamestate gamestate) {
		this.gamestate = gamestate;
	}
	
}
