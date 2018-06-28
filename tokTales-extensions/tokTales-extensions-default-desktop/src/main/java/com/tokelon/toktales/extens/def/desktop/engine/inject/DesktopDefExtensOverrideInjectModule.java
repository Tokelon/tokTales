package com.tokelon.toktales.extens.def.desktop.engine.inject;

import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.engine.inject.For;
import com.tokelon.toktales.core.game.screen.IRenderingStrategy;
import com.tokelon.toktales.extens.def.core.game.logic.IConsoleInterpreter;
import com.tokelon.toktales.extens.def.core.game.states.console.ConsoleGamestate;
import com.tokelon.toktales.extens.def.core.game.states.integration.IConsoleIntegrationControlHandler;
import com.tokelon.toktales.extens.def.core.game.states.integration.IConsoleIntegrationControlHandler.IConsoleIntegrationControlHandlerFactory;
import com.tokelon.toktales.extens.def.desktop.game.states.console.DesktopConsoleGamestateInterpreter;
import com.tokelon.toktales.extens.def.desktop.game.states.console.DesktopConsoleRenderingStrategy;
import com.tokelon.toktales.extens.def.desktop.game.states.integration.DesktopConsoleIntegrationControlHandler;

public class DesktopDefExtensOverrideInjectModule extends AbstractInjectModule {

	
	@Override
	protected void configure() {
		// LocalMapGamestate
		install(new FactoryModuleBuilder()
				.implement(IConsoleIntegrationControlHandler.class, DesktopConsoleIntegrationControlHandler.class)
				.build(IConsoleIntegrationControlHandlerFactory.class));
		
		
		// ConsoleGamestate
	    bind(IRenderingStrategy.class).annotatedWith(For.forClass(ConsoleGamestate.class)).to(DesktopConsoleRenderingStrategy.class);
	    bind(IConsoleInterpreter.class).annotatedWith(For.forClass(ConsoleGamestate.class)).to(DesktopConsoleGamestateInterpreter.class);

	}
	
}
