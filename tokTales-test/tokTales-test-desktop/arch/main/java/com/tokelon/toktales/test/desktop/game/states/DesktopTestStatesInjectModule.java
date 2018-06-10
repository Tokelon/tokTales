package com.tokelon.toktales.test.desktop.game.states;

import com.google.inject.Injector;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.game.states.IControlScheme;
import com.tokelon.toktales.desktop.engine.inject.DesktopInjectConfig;
import com.tokelon.toktales.test.core.game.states.CoreTestStatesInjectModule;
import com.tokelon.toktales.test.core.game.states.enginestate.IEngineGamestate.IEngineGamestateType;
import com.tokelon.toktales.test.core.game.states.enginestate.IEngineGamestateInputHandler;
import com.tokelon.toktales.test.core.game.states.enginestate.IEngineGamestateInputHandler.IEngineGamestateInputHandlerFactory;
import com.tokelon.toktales.test.desktop.engine.inject.DesktopMockPlatformInjectModule;
import com.tokelon.toktales.test.desktop.game.states.enginestate.DesktopEngineGamestateControlScheme;
import com.tokelon.toktales.test.desktop.game.states.enginestate.DesktopEngineGamestateInputHandler;

public class DesktopTestStatesInjectModule extends AbstractInjectModule {

	
	@Override
	protected void configure() {
		// EngineGamestate
		install(new FactoryModuleBuilder()
				.implement(IEngineGamestateInputHandler.class, DesktopEngineGamestateInputHandler.class)
				.build(IEngineGamestateInputHandlerFactory.class));

		bind(IControlScheme.class).annotatedWith(IEngineGamestateType.class).to(DesktopEngineGamestateControlScheme.class);
		
	}

	
	public static Injector createDesktopStatesInjector() {
		DesktopInjectConfig injectConfig = new DesktopInjectConfig();
		
		injectConfig.extend(new CoreTestStatesInjectModule());
		injectConfig.override(new DesktopTestStatesInjectModule());
		injectConfig.override(new DesktopMockPlatformInjectModule());
		
		Injector injector = injectConfig.createInjector();
		return injector;
	}

}
