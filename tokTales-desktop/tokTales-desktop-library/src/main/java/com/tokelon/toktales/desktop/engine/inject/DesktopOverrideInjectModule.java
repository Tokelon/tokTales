package com.tokelon.toktales.desktop.engine.inject;

import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.game.state.IGameStateControl;
import com.tokelon.toktales.desktop.game.state.DesktopGameStateControl;

public class DesktopOverrideInjectModule extends AbstractInjectModule {


	@Override
	protected void configure() {
		// Game bindings
		bindInGameScopeAndForNotScoped(IGameStateControl.class, DesktopGameStateControl.class);
	}

}
