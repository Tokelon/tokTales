package com.tokelon.toktales.extens.def.desktop.engine.inject;

import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.engine.inject.For;
import com.tokelon.toktales.core.game.screen.IRenderingStrategy;
import com.tokelon.toktales.extens.def.core.game.logic.IConsoleInterpreter;
import com.tokelon.toktales.extens.def.core.game.states.ConsoleGamestate;
import com.tokelon.toktales.extens.def.core.game.states.consover.IConsoleOverlayControlHandler;
import com.tokelon.toktales.extens.def.core.game.states.consover.IConsoleOverlayControlHandler.IConsoleOverlayControlHandlerFactory;
import com.tokelon.toktales.extens.def.desktop.game.screen.DesktopConsoleRenderingStrategy;
import com.tokelon.toktales.extens.def.desktop.game.states.DesktopConsoleGamestateInterpreter;
import com.tokelon.toktales.extens.def.desktop.game.states.integration.DesktopConsoleIntegrationControlHandler;

public class DesktopDefExtensOverrideInjectModule extends AbstractInjectModule {

	
	@Override
	protected void configure() {
		// LocalMapGamestate
		install(new FactoryModuleBuilder()
				.implement(IConsoleOverlayControlHandler.class, DesktopConsoleIntegrationControlHandler.class)
				.build(IConsoleOverlayControlHandlerFactory.class));
		
		
		// ConsoleGamestate
	    bind(IRenderingStrategy.class).annotatedWith(For.forClass(ConsoleGamestate.class)).to(DesktopConsoleRenderingStrategy.class);
	    bind(IConsoleInterpreter.class).annotatedWith(For.forClass(ConsoleGamestate.class)).to(DesktopConsoleGamestateInterpreter.class);

	}
	
}
