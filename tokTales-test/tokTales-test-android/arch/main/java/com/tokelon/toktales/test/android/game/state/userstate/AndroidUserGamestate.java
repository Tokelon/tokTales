package com.tokelon.toktales.test.android.game.state.userstate;

import com.tokelon.toktales.core.game.state.IControlHandler;
import com.tokelon.toktales.core.game.state.IControlScheme;
import com.tokelon.toktales.core.game.state.IGameStateInputHandler;
import com.tokelon.toktales.core.game.state.render.IGameStateRenderer;
import com.tokelon.toktales.test.core.game.state.userstate.UserGamestate;

public class AndroidUserGamestate extends UserGamestate {

	
	@Override
	protected void initStateDependencies(
			IGameStateRenderer defaultRender,
			IGameStateInputHandler defaultInputHandler,
			IControlScheme defaultControlScheme,
			IControlHandler defaultControlHandler
	) {

		AndroidUserGamestateInputHandler inputHandler = new AndroidUserGamestateInputHandler(this);
		AndroidUserGamestateControlScheme controlScheme = new AndroidUserGamestateControlScheme();
		
		super.initStateDependencies(defaultRender, inputHandler, controlScheme, defaultControlHandler);
	}
	
}
