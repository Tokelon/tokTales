package com.tokelon.toktales.test.android.game.state.userstate;

import com.tokelon.toktales.android.game.state.IAndroidGameStateInputHandler;

public class AndroidUserGamestateInputHandler implements IAndroidGameStateInputHandler {

	
	@SuppressWarnings("unused")
	private final AndroidUserGamestate gamestate; // For testing purposes
	
	public AndroidUserGamestateInputHandler(AndroidUserGamestate gamestate) {
		this.gamestate = gamestate;
	}
	
}
