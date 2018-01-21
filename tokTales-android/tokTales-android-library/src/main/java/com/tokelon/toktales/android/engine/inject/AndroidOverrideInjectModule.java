package com.tokelon.toktales.android.engine.inject;

import com.tokelon.toktales.android.states.AndroidGameStateManager;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.game.states.IGameStateControl;

public class AndroidOverrideInjectModule extends AbstractInjectModule {

	@Override
	protected void configure() {
		// Game bindings
		bindInGameScopeAndForNotScoped(IGameStateControl.class, AndroidGameStateManager.class);
	}

}
