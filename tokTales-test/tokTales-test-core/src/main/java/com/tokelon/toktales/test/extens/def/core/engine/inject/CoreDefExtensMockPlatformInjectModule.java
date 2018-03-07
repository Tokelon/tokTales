package com.tokelon.toktales.test.extens.def.core.engine.inject;

import static org.mockito.Mockito.mock;

import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.engine.inject.For;
import com.tokelon.toktales.core.game.states.IControlScheme;
import com.tokelon.toktales.core.game.states.IGameStateInputHandler;
import com.tokelon.toktales.extens.def.core.game.states.ConsoleGamestate;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapInputHandler.ILocalMapInputHandlerFactory;
import com.tokelon.toktales.extens.def.core.game.states.localmap.LocalMapGamestate;

/** Core Default Extensions inject module used for testing.
 * <p>
 * Mocks dependencies that are normally provided by platform implementations.
 */
public class CoreDefExtensMockPlatformInjectModule extends AbstractInjectModule {
	
	
	@Override
	protected void configure() {
		// LocalMapGamestate
		bind(ILocalMapInputHandlerFactory.class).toInstance(mock(ILocalMapInputHandlerFactory.class));
    	bind(IControlScheme.class).annotatedWith(For.forClass(LocalMapGamestate.class)).toInstance(mock(IControlScheme.class));

    	
    	// ConsoleGamestate
    	bind(IGameStateInputHandler.class).annotatedWith(For.forClass(ConsoleGamestate.class)).toInstance(mock(IGameStateInputHandler.class));
	}

}
