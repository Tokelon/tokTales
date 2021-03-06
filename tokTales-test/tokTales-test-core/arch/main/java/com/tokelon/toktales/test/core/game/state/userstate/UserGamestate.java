package com.tokelon.toktales.test.core.game.state.userstate;

import com.tokelon.toktales.core.game.state.BaseGamestate;
import com.tokelon.toktales.core.game.state.IControlHandler;
import com.tokelon.toktales.core.game.state.IControlScheme;
import com.tokelon.toktales.core.game.state.IGameStateInputHandler;
import com.tokelon.toktales.core.game.state.render.IGameStateRenderer;
import com.tokelon.toktales.core.game.state.scene.IGameScene;
import com.tokelon.toktales.core.render.texture.DefaultTextureCoordinator;

public class UserGamestate extends BaseGamestate<IGameScene> {


	/* Could also create ctor with needed state objects and pass them in the subclasses
	 */
	
	@Override
	protected void initStateDependencies(
			IGameStateRenderer defaultRender,
			IGameStateInputHandler defaultInputHandler,
			IControlScheme defaultControlScheme,
			IControlHandler defaultControlHandler
	) {
		
		UserGamestateControlHandler controlHandler = new UserGamestateControlHandler(this);
		
		UserGamestateRenderer render = new UserGamestateRenderer(new DefaultTextureCoordinator(getGame().getContentManager().getTextureManager()), this);
		
		
		super.initStateDependencies(render, defaultInputHandler, defaultControlScheme, controlHandler);
	}
	
}
