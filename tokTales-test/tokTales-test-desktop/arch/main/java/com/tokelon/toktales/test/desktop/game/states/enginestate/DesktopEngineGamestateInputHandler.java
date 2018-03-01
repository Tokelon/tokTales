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
	
	
	public IEngineGamestate getGamestate() {
		return gamestate;
	}
	
	
	@Override
	public boolean invokeMouseButton(int vb, int action) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean invokeCursorMove(double xpos, double ypos) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean invokeKeyInput(int vk, int action) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean invokeCharInput(int codepoint) {
		// TODO Auto-generated method stub
		return false;
	}

}
