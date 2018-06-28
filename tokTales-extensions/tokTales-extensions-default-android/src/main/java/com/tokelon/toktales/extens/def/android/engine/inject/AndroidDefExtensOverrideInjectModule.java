package com.tokelon.toktales.extens.def.android.engine.inject;

import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.extens.def.android.states.integration.AndroidConsoleIntegrationControlHandler;
import com.tokelon.toktales.extens.def.android.states.localmap.AndroidLocalMapStateRenderer;
import com.tokelon.toktales.extens.def.core.game.states.integration.IConsoleIntegrationControlHandler;
import com.tokelon.toktales.extens.def.core.game.states.integration.IConsoleIntegrationControlHandler.IConsoleIntegrationControlHandlerFactory;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapStateRenderer;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapStateRenderer.ILocalMapStateRendererFactory;

public class AndroidDefExtensOverrideInjectModule extends AbstractInjectModule {


	@Override
	protected void configure() {
		// LocalMapGamestate
		install(new FactoryModuleBuilder()
				.implement(IConsoleIntegrationControlHandler.class, AndroidConsoleIntegrationControlHandler.class)
				.build(IConsoleIntegrationControlHandlerFactory.class));
		install(new FactoryModuleBuilder()
				.implement(ILocalMapStateRenderer.class, AndroidLocalMapStateRenderer.class)
				.build(ILocalMapStateRendererFactory.class));
		
	}

}
