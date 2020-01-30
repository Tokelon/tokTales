package com.tokelon.toktales.test.desktop.game.state.userstate;

import com.tokelon.toktales.core.game.state.IControlHandler;
import com.tokelon.toktales.core.game.state.IControlScheme;
import com.tokelon.toktales.core.game.state.IGameStateInputHandler;
import com.tokelon.toktales.core.game.state.render.IGameStateRenderer;
import com.tokelon.toktales.test.core.game.state.userstate.UserGamestate;

public class DesktopUserGamestate extends UserGamestate {
	
	
	@Override
	protected void initStateDependencies(
			IGameStateRenderer defaultRender,
			IGameStateInputHandler defaultInputHandler,
			IControlScheme defaultControlScheme,
			IControlHandler defaultControlHandler
	) {

		DesktopUserGamestateInputHandler inputHandler = new DesktopUserGamestateInputHandler(this);
		DesktopUserGamestateControlScheme controlScheme = new DesktopUserGamestateControlScheme();
		
		super.initStateDependencies(defaultRender, inputHandler, controlScheme, defaultControlHandler);
	}
	
}
