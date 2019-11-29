package com.tokelon.toktales.desktop.engine.inject;

import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.game.states.IGameStateControl;
import com.tokelon.toktales.desktop.game.states.DesktopGameStateControl;

public class DesktopOverrideInjectModule extends AbstractInjectModule {


	@Override
	protected void configure() {
		// Game bindings
		bindInGameScopeAndForNotScoped(IGameStateControl.class, DesktopGameStateControl.class);
	}

}
