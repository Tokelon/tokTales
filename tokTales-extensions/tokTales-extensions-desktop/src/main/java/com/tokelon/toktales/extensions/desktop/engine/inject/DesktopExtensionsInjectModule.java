package com.tokelon.toktales.extensions.desktop.engine.inject;

import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.game.state.IGameStateInputHandler;
import com.tokelon.toktales.extensions.core.game.state.console.ConsoleGamestate;
import com.tokelon.toktales.extensions.core.game.state.localmap.ILocalMapControlScheme;
import com.tokelon.toktales.extensions.core.game.state.localmap.ILocalMapInputHandler;
import com.tokelon.toktales.extensions.core.game.state.localmap.ILocalMapInputHandler.ILocalMapInputHandlerFactory;
import com.tokelon.toktales.extensions.desktop.game.state.console.DesktopConsoleInputHandler;
import com.tokelon.toktales.extensions.desktop.game.state.localmap.DesktopLocalMapControlScheme;
import com.tokelon.toktales.extensions.desktop.game.state.localmap.DesktopLocalMapInputHandler;
import com.tokelon.toktales.tools.core.sub.inject.scope.For;

public class DesktopExtensionsInjectModule extends AbstractInjectModule {

	
    @Override
    protected void configure() {
    	// LocalMapGamestate
    	install(new FactoryModuleBuilder()
    			.implement(ILocalMapInputHandler.class, DesktopLocalMapInputHandler.class)
    			.build(ILocalMapInputHandlerFactory.class));
    	bind(ILocalMapControlScheme.class).to(DesktopLocalMapControlScheme.class);

    	
    	// ConsoleGamestate
    	bind(IGameStateInputHandler.class).annotatedWith(For.forClass(ConsoleGamestate.class)).to(DesktopConsoleInputHandler.class);
    	
    }

}
