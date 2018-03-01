package com.tokelon.toktales.test.desktop.game.states.userstate;

import com.tokelon.toktales.desktop.game.states.IDesktopGameStateInputHandler;

public class DesktopUserGamestateInputHandler implements IDesktopGameStateInputHandler {

	
	private final DesktopUserGamestate gamestate;
	
	public DesktopUserGamestateInputHandler(DesktopUserGamestate gamestate) {
		this.gamestate = gamestate;
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
