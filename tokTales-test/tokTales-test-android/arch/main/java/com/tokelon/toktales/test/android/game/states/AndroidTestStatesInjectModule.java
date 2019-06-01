package com.tokelon.toktales.test.android.game.states;

import com.google.inject.Injector;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.tokelon.toktales.android.engine.inject.MasterAndroidInjectConfig;
import com.tokelon.toktales.android.test.engine.inject.AndroidMockPlatformInjectModule;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.game.states.IControlScheme;
import com.tokelon.toktales.test.android.game.states.enginestate.AndroidEngineGamestateControlScheme;
import com.tokelon.toktales.test.android.game.states.enginestate.AndroidEngineGamestateInputHandler;
import com.tokelon.toktales.test.core.game.states.CoreTestStatesInjectModule;
import com.tokelon.toktales.test.core.game.states.enginestate.IEngineGamestate.IEngineGamestateType;
import com.tokelon.toktales.test.core.game.states.enginestate.IEngineGamestateInputHandler;
import com.tokelon.toktales.test.core.game.states.enginestate.IEngineGamestateInputHandler.IEngineGamestateInputHandlerFactory;

public class AndroidTestStatesInjectModule extends AbstractInjectModule {

	
	@Override
	protected void configure() {
		// EngineGamestate
		install(new FactoryModuleBuilder()
				.implement(IEngineGamestateInputHandler.class, AndroidEngineGamestateInputHandler.class)
				.build(IEngineGamestateInputHandlerFactory.class));

		bind(IControlScheme.class).annotatedWith(IEngineGamestateType.class).to(AndroidEngineGamestateControlScheme.class);
		
	}


	// This should probably be in a config
	public static Injector createAndroidStatesInjector() {
		MasterAndroidInjectConfig injectConfig = new MasterAndroidInjectConfig();
		
		injectConfig.extend(new CoreTestStatesInjectModule());
		injectConfig.override(new AndroidTestStatesInjectModule());
		injectConfig.override(new AndroidMockPlatformInjectModule());
		
		Injector injector = injectConfig.createInjector();
		return injector;
	}
	
}
