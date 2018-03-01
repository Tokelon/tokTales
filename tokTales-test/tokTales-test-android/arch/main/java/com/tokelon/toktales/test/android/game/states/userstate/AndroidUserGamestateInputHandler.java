package com.tokelon.toktales.test.android.game.states.userstate;

import com.tokelon.toktales.android.states.IAndroidGameStateInputHandler;

public class AndroidUserGamestateInputHandler implements IAndroidGameStateInputHandler {

	
	private final AndroidUserGamestate gamestate;
	
	public AndroidUserGamestateInputHandler(AndroidUserGamestate gamestate) {
		this.gamestate = gamestate;
	}
	
	
	@Override
	public boolean invokeScreenButton(int vb, int action) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean invokeScreenPress(double xpos, double ypos) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean invokeScreenPointer(int pointerId, int action, double xpos, double ypos) {
		// TODO Auto-generated method stub
		return false;
	}

}
