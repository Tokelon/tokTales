package com.tokelon.toktales.test.core.game.states.userstate;

import com.tokelon.toktales.core.engine.render.ISurfaceHandler;
import com.tokelon.toktales.core.game.screen.IStateRender;
import com.tokelon.toktales.core.game.states.BaseGamestate;
import com.tokelon.toktales.core.game.states.IControlHandler;
import com.tokelon.toktales.core.game.states.IControlScheme;
import com.tokelon.toktales.core.game.states.IGameStateInputHandler;

public class UserGamestate extends BaseGamestate {


	/* Could also create ctor with needed state objects and pass them in the subclasses
	 */
	
	@Override
	protected void initStateDependencies(
			IStateRender defaultRender,
			IGameStateInputHandler defaultInputHandler,
			IControlScheme defaultControlScheme,
			IControlHandler defaultControlHandler
	) {
		
		UserGamestateControlHandler controlHandler = new UserGamestateControlHandler(this);
		
		ISurfaceHandler surfaceHandler = getEngineContext().getEngine().getRenderService().getSurfaceHandler();
		UserGamestateRender render = new UserGamestateRender(surfaceHandler, this);
		
		
		super.initStateDependencies(render, defaultInputHandler, defaultControlScheme, controlHandler);
	}
	
}
