package com.tokelon.toktales.extensions.core.test.engine.inject;

import static org.mockito.Mockito.mock;

import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.game.states.IGameStateInputHandler;
import com.tokelon.toktales.extensions.core.game.states.console.ConsoleGamestate;
import com.tokelon.toktales.extensions.core.game.states.localmap.ILocalMapControlScheme;
import com.tokelon.toktales.extensions.core.game.states.localmap.ILocalMapInputHandler.ILocalMapInputHandlerFactory;
import com.tokelon.toktales.tools.core.sub.inject.scope.For;

/** Core Default Extensions inject module used for testing.
 * <p>
 * Mocks dependencies that are normally provided by platform implementations.
 */
public class CoreDefExtensMockPlatformInjectModule extends AbstractInjectModule {
	
	
	@Override
	protected void configure() {
		// LocalMapGamestate
		bind(ILocalMapInputHandlerFactory.class).toInstance(mock(ILocalMapInputHandlerFactory.class));
    	bind(ILocalMapControlScheme.class).toInstance(mock(ILocalMapControlScheme.class));

    	
    	// ConsoleGamestate
    	bind(IGameStateInputHandler.class).annotatedWith(For.forClass(ConsoleGamestate.class)).toInstance(mock(IGameStateInputHandler.class));
	}

}
