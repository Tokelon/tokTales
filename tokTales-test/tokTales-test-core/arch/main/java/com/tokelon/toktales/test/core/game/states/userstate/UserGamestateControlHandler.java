package com.tokelon.toktales.test.core.game.states.userstate;

import com.tokelon.toktales.core.game.states.IControlHandler;

public class UserGamestateControlHandler implements IControlHandler {

	
	private final UserGamestate gamestate;
	
	public UserGamestateControlHandler(UserGamestate gamestate) {
		this.gamestate = gamestate;
	}
	
	
	@Override
	public boolean handleAction(String target, String action, Object... params) {
		// TODO Auto-generated method stub
		return false;
	}

}
