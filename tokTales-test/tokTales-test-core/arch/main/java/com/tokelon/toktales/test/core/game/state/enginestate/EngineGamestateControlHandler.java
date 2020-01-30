package com.tokelon.toktales.test.core.game.state.enginestate;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;

public class EngineGamestateControlHandler implements IEngineGamestateControlHandler {

	
	private final IEngineGamestate gamestate;
	
	@Inject
	public EngineGamestateControlHandler(@Assisted IEngineGamestate gamestate) {
		this.gamestate = gamestate;
	}
	
	
	public IEngineGamestate getGamestate() {
		return gamestate;
	}

	
	@Override
	public boolean handleAction(String target, String action, Object... params) {
		// TODO Auto-generated method stub
		return false;
	}

}
