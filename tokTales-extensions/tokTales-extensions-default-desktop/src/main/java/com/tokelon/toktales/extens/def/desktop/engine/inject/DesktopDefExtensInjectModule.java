package com.tokelon.toktales.extens.def.desktop.engine.inject;

import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.engine.inject.For;
import com.tokelon.toktales.core.game.states.IGameStateInputHandler;
import com.tokelon.toktales.extens.def.core.game.states.console.ConsoleGamestate;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapControlScheme;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapInputHandler;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapInputHandler.ILocalMapInputHandlerFactory;
import com.tokelon.toktales.extens.def.desktop.game.states.console.DesktopConsoleInputHandler;
import com.tokelon.toktales.extens.def.desktop.game.states.localmap.DesktopLocalMapControlScheme;
import com.tokelon.toktales.extens.def.desktop.game.states.localmap.DesktopLocalMapInputHandler;

public class DesktopDefExtensInjectModule extends AbstractInjectModule {

	
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
