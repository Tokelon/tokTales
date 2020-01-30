package com.tokelon.toktales.core.engine.inject;

import com.tokelon.toktales.core.game.state.IGameStateInputHandler;
import com.tokelon.toktales.tools.core.sub.inject.scope.ForClass;

public class CoreFallbacksInjectModule extends AbstractInjectModule {

	
	@Override
	protected void configure() {
		// Inexact annotated bindings
		bind(IGameStateInputHandler.class).annotatedWith(ForClass.class).to(IGameStateInputHandler.EmptyGameStateInputHandler.class);
		
	}

}
