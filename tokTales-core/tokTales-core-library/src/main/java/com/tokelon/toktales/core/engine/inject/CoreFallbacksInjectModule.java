package com.tokelon.toktales.core.engine.inject;

import com.tokelon.toktales.core.game.states.IGameStateInputHandler;

public class CoreFallbacksInjectModule extends AbstractInjectModule {

	
	@Override
	protected void configure() {
		// Inexact annotated bindings
		bind(IGameStateInputHandler.class).annotatedWith(ForClass.class).to(IGameStateInputHandler.EmptyGameStateInputHandler.class);
		
	}

}
