package com.tokelon.toktales.extens.def.desktop.game.states;

import javax.inject.Inject;

import com.tokelon.toktales.core.game.IGame;
import com.tokelon.toktales.core.game.states.IGameStateInputHandler;
import com.tokelon.toktales.desktop.input.IDesktopInputRegistration.IKeyInputCallback;

public class DesktopChunkTestingInputHandler implements IGameStateInputHandler, IKeyInputCallback {

	
	private int keyCount = 0;
	
	private final IGame game;
	
	@Inject
	public DesktopChunkTestingInputHandler(IGame game) {
		this.game = game;
	}

	
	@Override
	public boolean invokeKeyInput(int vk, int action) {
		if(keyCount++ >= 2) {
			keyCount = 0;
			game.getStateControl().changeState("console_state");
			
			return true;
		}
		else {
			return false;
		}
	}

}
