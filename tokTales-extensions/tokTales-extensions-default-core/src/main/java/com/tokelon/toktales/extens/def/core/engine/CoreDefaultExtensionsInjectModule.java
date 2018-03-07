package com.tokelon.toktales.extens.def.core.engine;

import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.engine.inject.For;
import com.tokelon.toktales.core.engine.inject.ForClass;
import com.tokelon.toktales.core.game.screen.IRenderingStrategy;
import com.tokelon.toktales.core.game.states.IGameStateInputHandler;
import com.tokelon.toktales.extens.def.core.game.logic.IConsoleInterpreter;
import com.tokelon.toktales.extens.def.core.game.screen.ConsoleRenderingStrategy;
import com.tokelon.toktales.extens.def.core.game.screen.LocalMapStateRenderer;
import com.tokelon.toktales.extens.def.core.game.states.ConsoleGamestate;
import com.tokelon.toktales.extens.def.core.game.states.ConsoleGamestateInterpreter;
import com.tokelon.toktales.extens.def.core.game.states.IConsoleGamestate;
import com.tokelon.toktales.extens.def.core.game.states.IConsoleGamestate.IConsoleGamestateInterpreterFactory;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapControlHandler;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapControlHandler.ILocalMapControlHandlerFactory;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapGamestate;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapStateRenderer;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapStateRenderer.ILocalMapStateRendererFactory;
import com.tokelon.toktales.extens.def.core.game.states.localmap.LocalMapControlHandler;
import com.tokelon.toktales.extens.def.core.game.states.localmap.LocalMapGamestate;

public class CoreDefaultExtensionsInjectModule extends AbstractInjectModule {

	
    @Override
    protected void configure() {
    	// LocalMapGamestate
    	bind(ILocalMapGamestate.class).to(LocalMapGamestate.class);
		 install(new FactoryModuleBuilder()
				 .implement(ILocalMapStateRenderer.class, LocalMapStateRenderer.class)
				 .build(ILocalMapStateRendererFactory.class));
		 install(new FactoryModuleBuilder()
				 .implement(ILocalMapControlHandler.class, LocalMapControlHandler.class)
				 .build(ILocalMapControlHandlerFactory.class));
    	
		 
	    // ConsoleGamestate
		bind(IConsoleGamestate.class).to(ConsoleGamestate.class);
	    bind(IRenderingStrategy.class).annotatedWith(For.forClass(ConsoleGamestate.class)).to(ConsoleRenderingStrategy.class);
		install(new FactoryModuleBuilder()
				.implement(IConsoleInterpreter.class, ConsoleGamestateInterpreter.class)
				.build(IConsoleGamestateInterpreterFactory.class));
		
    }

}
