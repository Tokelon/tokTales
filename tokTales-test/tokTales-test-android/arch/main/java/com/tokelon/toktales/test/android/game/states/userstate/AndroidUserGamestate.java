package com.tokelon.toktales.test.android.game.states.userstate;

import com.tokelon.toktales.core.game.screen.IStateRender;
import com.tokelon.toktales.core.game.states.IControlHandler;
import com.tokelon.toktales.core.game.states.IControlScheme;
import com.tokelon.toktales.core.game.states.IGameStateInputHandler;
import com.tokelon.toktales.test.core.game.states.userstate.UserGamestate;

public class AndroidUserGamestate extends UserGamestate {

	
	@Override
	protected void initStateDependencies(
			IStateRender defaultRender,
			IGameStateInputHandler defaultInputHandler,
			IControlScheme defaultControlScheme,
			IControlHandler defaultControlHandler
	) {

		AndroidUserGamestateInputHandler inputHandler = new AndroidUserGamestateInputHandler(this);
		AndroidUserGamestateControlScheme controlScheme = new AndroidUserGamestateControlScheme();
		
		super.initStateDependencies(defaultRender, inputHandler, controlScheme, defaultControlHandler);
	}
	
}
