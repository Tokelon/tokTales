package com.tokelon.toktales.extens.def.desktop.engine;

import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.engine.inject.For;
import com.tokelon.toktales.core.game.states.IControlScheme;
import com.tokelon.toktales.core.game.states.IGameStateInputHandler;
import com.tokelon.toktales.extens.def.core.game.states.ChunkTestingGamestate;
import com.tokelon.toktales.extens.def.core.game.states.ConsoleGamestate;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapInputHandler;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapInputHandler.ILocalMapInputHandlerFactory;
import com.tokelon.toktales.extens.def.core.game.states.localmap.LocalMapGamestate;
import com.tokelon.toktales.extens.def.desktop.game.states.DesktopChunkTestingInputHandler;
import com.tokelon.toktales.extens.def.desktop.game.states.DesktopConsoleInputHandler;
import com.tokelon.toktales.extens.def.desktop.game.states.localmap.DesktopLocalMapControlScheme;
import com.tokelon.toktales.extens.def.desktop.game.states.localmap.DesktopLocalMapInputHandler;

public class DesktopDefaultExtensionsInjectModule extends AbstractInjectModule {

	
    @Override
    protected void configure() {
    	// LocalMapGamestate
    	install(new FactoryModuleBuilder()
    			.implement(ILocalMapInputHandler.class, DesktopLocalMapInputHandler.class)
    			.build(ILocalMapInputHandlerFactory.class));
    	bind(IControlScheme.class).annotatedWith(For.forClass(LocalMapGamestate.class)).to(DesktopLocalMapControlScheme.class);

    	
    	// ConsoleGamestate
    	bind(IGameStateInputHandler.class).annotatedWith(For.forClass(ConsoleGamestate.class)).to(DesktopConsoleInputHandler.class);
    	
    	
    	// ChunkTestingGamestate
    	bind(IGameStateInputHandler.class).annotatedWith(For.forClass(ChunkTestingGamestate.class)).to(DesktopChunkTestingInputHandler.class);
    	
    }

}
