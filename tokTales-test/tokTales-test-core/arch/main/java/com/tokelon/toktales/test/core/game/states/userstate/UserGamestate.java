package com.tokelon.toktales.test.core.game.states.userstate;

import com.tokelon.toktales.core.game.screen.IStateRender;
import com.tokelon.toktales.core.game.states.BaseGamestate;
import com.tokelon.toktales.core.game.states.IControlHandler;
import com.tokelon.toktales.core.game.states.IControlScheme;
import com.tokelon.toktales.core.game.states.IGameScene;
import com.tokelon.toktales.core.game.states.IGameSceneControl.IModifiableGameSceneControl;
import com.tokelon.toktales.core.game.states.IGameStateInputHandler;
import com.tokelon.toktales.core.render.DefaultTextureCoordinator;

public class UserGamestate extends BaseGamestate {


	/* Could also create ctor with needed state objects and pass them in the subclasses
	 */
	
	@Override
	protected void initStateDependencies(
			IModifiableGameSceneControl<? extends IGameScene> defaultSceneControl,
			IStateRender defaultRender,
			IGameStateInputHandler defaultInputHandler,
			IControlScheme defaultControlScheme,
			IControlHandler defaultControlHandler
	) {
		
		UserGamestateControlHandler controlHandler = new UserGamestateControlHandler(this);
		
		UserGamestateRender render = new UserGamestateRender(new DefaultTextureCoordinator(getGame().getContentManager().getTextureManager()), this);
		
		
		super.initStateDependencies(defaultSceneControl, render, defaultInputHandler, defaultControlScheme, controlHandler);
	}
	
}
