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
	
	
	public IEngineGamestate getGamestate() {
		return gamestate;
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
