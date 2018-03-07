package com.tokelon.toktales.extens.def.desktop.engine;

import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.engine.inject.For;
import com.tokelon.toktales.core.game.screen.IRenderingStrategy;
import com.tokelon.toktales.extens.def.core.game.logic.IConsoleInterpreter;
import com.tokelon.toktales.extens.def.core.game.states.ConsoleGamestate;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapControlHandler;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapControlHandler.ILocalMapControlHandlerFactory;
import com.tokelon.toktales.extens.def.desktop.game.screen.DesktopConsoleRenderingStrategy;
import com.tokelon.toktales.extens.def.desktop.game.states.DesktopConsoleGamestateInterpreter;
import com.tokelon.toktales.extens.def.desktop.game.states.localmap.DesktopLocalMapControlHandler;

public class DesktopDefaultExtensionsOverrideInjectModule extends AbstractInjectModule {

	
	@Override
	protected void configure() {
		// LocalMapGamestate
		install(new FactoryModuleBuilder()
				.implement(ILocalMapControlHandler.class, DesktopLocalMapControlHandler.class)
				.build(ILocalMapControlHandlerFactory.class));
		
		
		// ConsoleGamestate
	    bind(IRenderingStrategy.class).annotatedWith(For.forClass(ConsoleGamestate.class)).to(DesktopConsoleRenderingStrategy.class);
	    bind(IConsoleInterpreter.class).annotatedWith(For.forClass(ConsoleGamestate.class)).to(DesktopConsoleGamestateInterpreter.class);

	}
	
}
