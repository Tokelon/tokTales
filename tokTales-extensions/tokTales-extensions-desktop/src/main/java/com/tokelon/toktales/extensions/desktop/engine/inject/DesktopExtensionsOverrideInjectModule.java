package com.tokelon.toktales.extensions.desktop.engine.inject;

import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.extensions.core.game.state.integration.IConsoleIntegrationControlHandler;
import com.tokelon.toktales.extensions.core.game.state.integration.IConsoleIntegrationControlHandler.IConsoleIntegrationControlHandlerFactory;
import com.tokelon.toktales.extensions.desktop.game.state.integration.DesktopConsoleIntegrationControlHandler;

public class DesktopExtensionsOverrideInjectModule extends AbstractInjectModule {

	
	@Override
	protected void configure() {
		// LocalMapGamestate
		install(new FactoryModuleBuilder()
				.implement(IConsoleIntegrationControlHandler.class, DesktopConsoleIntegrationControlHandler.class)
				.build(IConsoleIntegrationControlHandlerFactory.class));
		
	}
	
}
