package com.tokelon.toktales.test.desktop.game.state;

import com.google.inject.Injector;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.game.state.IControlScheme;
import com.tokelon.toktales.desktop.engine.inject.MasterDesktopInjectConfig;
import com.tokelon.toktales.desktop.test.engine.inject.DesktopMockPlatformInjectModule;
import com.tokelon.toktales.test.core.game.state.CoreTestStatesInjectModule;
import com.tokelon.toktales.test.core.game.state.enginestate.IEngineGamestateInputHandler;
import com.tokelon.toktales.test.core.game.state.enginestate.IEngineGamestate.IEngineGamestateType;
import com.tokelon.toktales.test.core.game.state.enginestate.IEngineGamestateInputHandler.IEngineGamestateInputHandlerFactory;
import com.tokelon.toktales.test.desktop.game.state.enginestate.DesktopEngineGamestateControlScheme;
import com.tokelon.toktales.test.desktop.game.state.enginestate.DesktopEngineGamestateInputHandler;

public class DesktopTestStatesInjectModule extends AbstractInjectModule {

	
	@Override
	protected void configure() {
		// EngineGamestate
		install(new FactoryModuleBuilder()
				.implement(IEngineGamestateInputHandler.class, DesktopEngineGamestateInputHandler.class)
				.build(IEngineGamestateInputHandlerFactory.class));

		bind(IControlScheme.class).annotatedWith(IEngineGamestateType.class).to(DesktopEngineGamestateControlScheme.class);
		
	}

	
	// This should probably be in a config
	public static Injector createDesktopStatesInjector() {
		MasterDesktopInjectConfig injectConfig = new MasterDesktopInjectConfig();
		
		injectConfig.extend(new CoreTestStatesInjectModule());
		injectConfig.override(new DesktopTestStatesInjectModule());
		injectConfig.override(new DesktopMockPlatformInjectModule());
		
		Injector injector = injectConfig.createInjector();
		return injector;
	}

}
