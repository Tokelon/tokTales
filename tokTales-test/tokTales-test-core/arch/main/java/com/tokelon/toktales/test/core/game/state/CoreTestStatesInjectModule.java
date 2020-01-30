package com.tokelon.toktales.test.core.game.state;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.inject.Injector;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.engine.inject.MasterCoreInjectConfig;
import com.tokelon.toktales.core.game.state.IControlScheme;
import com.tokelon.toktales.core.test.engine.inject.CoreMockPlatformInjectModule;
import com.tokelon.toktales.test.core.game.state.enginestate.EngineGamescene;
import com.tokelon.toktales.test.core.game.state.enginestate.EngineGamestate;
import com.tokelon.toktales.test.core.game.state.enginestate.EngineGamestateControlHandler;
import com.tokelon.toktales.test.core.game.state.enginestate.EngineGamestateRenderer;
import com.tokelon.toktales.test.core.game.state.enginestate.IEngineGamescene;
import com.tokelon.toktales.test.core.game.state.enginestate.IEngineGamestate;
import com.tokelon.toktales.test.core.game.state.enginestate.IEngineGamestateControlHandler;
import com.tokelon.toktales.test.core.game.state.enginestate.IEngineGamestateInputHandler;
import com.tokelon.toktales.test.core.game.state.enginestate.IEngineGamestateRenderer;
import com.tokelon.toktales.test.core.game.state.enginestate.IEngineGamestate.IEngineGamestateType;
import com.tokelon.toktales.test.core.game.state.enginestate.IEngineGamestateControlHandler.IEngineGamestateControlHandlerFactory;
import com.tokelon.toktales.test.core.game.state.enginestate.IEngineGamestateInputHandler.IEngineGamestateInputHandlerFactory;
import com.tokelon.toktales.test.core.game.state.enginestate.IEngineGamestateRenderer.IEngineGamestateRendererFactory;
import com.tokelon.toktales.test.core.game.state.enginestate.subenginestate.ISubEngineGamescene;
import com.tokelon.toktales.test.core.game.state.enginestate.subenginestate.ISubEngineGamestate;
import com.tokelon.toktales.test.core.game.state.enginestate.subenginestate.SubEngineGamescene;
import com.tokelon.toktales.test.core.game.state.enginestate.subenginestate.SubEngineGamestate;

public class CoreTestStatesInjectModule extends AbstractInjectModule {

	private static final IEngineGamestateInputHandlerFactory engineStateInputHandlerFactoryMock = mock(IEngineGamestateInputHandlerFactory.class);
	private static final IEngineGamestateInputHandler engineStateInputHandler = mock(IEngineGamestateInputHandler.class);
	
	static {
		when(engineStateInputHandlerFactoryMock.create(any(IEngineGamestate.class))).thenReturn(engineStateInputHandler);
	}

	
	@Override
	protected void configure() {
		// EngineGamestate
		bind(IEngineGamestate.class).to(EngineGamestate.class);
		install(new FactoryModuleBuilder()
				.implement(IEngineGamestateRenderer.class, EngineGamestateRenderer.class)
				.build(IEngineGamestateRendererFactory.class));
		install(new FactoryModuleBuilder()
				.implement(IEngineGamestateControlHandler.class, EngineGamestateControlHandler.class)
				.build(IEngineGamestateControlHandlerFactory.class));
		bind(IEngineGamescene.class).to(EngineGamescene.class);
		
		// EngineGamestate - Mocked
		bind(IControlScheme.class).annotatedWith(IEngineGamestateType.class).to(IControlScheme.EmptyControlScheme.class);
		bind(IEngineGamestateInputHandlerFactory.class).toInstance(engineStateInputHandlerFactoryMock);
		
		// SubEngineGamestate
		bind(ISubEngineGamestate.class).to(SubEngineGamestate.class);
		bind(ISubEngineGamescene.class).to(SubEngineGamescene.class);
		
	}
	

	// This should probably be in a config
	public static Injector createCoreStatesInjector() {
		MasterCoreInjectConfig injectConfig = new MasterCoreInjectConfig();
		injectConfig.extend(new CoreMockPlatformInjectModule());
		
		injectConfig.override(new CoreTestStatesInjectModule());
		
		Injector injector = injectConfig.createInjector();
		return injector;
	}
	
}
