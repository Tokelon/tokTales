package com.tokelon.toktales.test.desktop.game.states.userstate;

import com.tokelon.toktales.core.game.screen.IStateRender;
import com.tokelon.toktales.core.game.states.IControlHandler;
import com.tokelon.toktales.core.game.states.IControlScheme;
import com.tokelon.toktales.core.game.states.IGameScene;
import com.tokelon.toktales.core.game.states.IGameSceneControl.IModifiableGameSceneControl;
import com.tokelon.toktales.core.game.states.IGameStateInputHandler;
import com.tokelon.toktales.test.core.game.states.userstate.UserGamestate;

public class DesktopUserGamestate extends UserGamestate {
	
	
	@Override
	protected void initStateDependencies(
			IModifiableGameSceneControl<? extends IGameScene> defaultSceneControl,
			IStateRender defaultRender,
			IGameStateInputHandler defaultInputHandler,
			IControlScheme defaultControlScheme,
			IControlHandler defaultControlHandler
	) {

		DesktopUserGamestateInputHandler inputHandler = new DesktopUserGamestateInputHandler(this);
		DesktopUserGamestateControlScheme controlScheme = new DesktopUserGamestateControlScheme();
		
		super.initStateDependencies(defaultSceneControl, defaultRender, inputHandler, controlScheme, defaultControlHandler);
	}
	
}
