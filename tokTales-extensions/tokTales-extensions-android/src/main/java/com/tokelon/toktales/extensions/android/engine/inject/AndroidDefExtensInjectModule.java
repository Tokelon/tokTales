package com.tokelon.toktales.extensions.android.engine.inject;

import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.game.states.IGameStateInputHandler;
import com.tokelon.toktales.extensions.android.states.console.AndroidConsoleInputHandler;
import com.tokelon.toktales.extensions.android.states.localmap.AndroidLocalMapControlScheme;
import com.tokelon.toktales.extensions.android.states.localmap.AndroidLocalMapInputHandler;
import com.tokelon.toktales.extensions.core.game.states.console.ConsoleGamestate;
import com.tokelon.toktales.extensions.core.game.states.localmap.ILocalMapControlScheme;
import com.tokelon.toktales.extensions.core.game.states.localmap.ILocalMapInputHandler;
import com.tokelon.toktales.extensions.core.game.states.localmap.ILocalMapInputHandler.ILocalMapInputHandlerFactory;
import com.tokelon.toktales.tools.core.sub.inject.scope.For;

public class AndroidDefExtensInjectModule extends AbstractInjectModule {

	
    @Override
    protected void configure() {
    	// LocalMapGamestate
    	install(new FactoryModuleBuilder()
    			.implement(ILocalMapInputHandler.class, AndroidLocalMapInputHandler.class)
    			.build(ILocalMapInputHandlerFactory.class));
    	bind(ILocalMapControlScheme.class).to(AndroidLocalMapControlScheme.class);
    	
    	
    	// ConsoleGamestate
    	bind(IGameStateInputHandler.class).annotatedWith(For.forClass(ConsoleGamestate.class)).to(AndroidConsoleInputHandler.class);
    	
    }

}
