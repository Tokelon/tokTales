package com.tokelon.toktales.extensions.android.engine.inject;

import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.game.state.IGameStateInputHandler;
import com.tokelon.toktales.extensions.android.game.state.console.AndroidConsoleInputHandler;
import com.tokelon.toktales.extensions.android.game.state.localmap.AndroidLocalMapControlScheme;
import com.tokelon.toktales.extensions.android.game.state.localmap.AndroidLocalMapInputHandler;
import com.tokelon.toktales.extensions.core.game.state.console.ConsoleGamestate;
import com.tokelon.toktales.extensions.core.game.state.localmap.ILocalMapControlScheme;
import com.tokelon.toktales.extensions.core.game.state.localmap.ILocalMapInputHandler;
import com.tokelon.toktales.extensions.core.game.state.localmap.ILocalMapInputHandler.ILocalMapInputHandlerFactory;
import com.tokelon.toktales.tools.core.sub.inject.scope.For;

public class AndroidExtensionsInjectModule extends AbstractInjectModule {

	
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
