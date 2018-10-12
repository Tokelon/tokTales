package com.tokelon.toktales.test.core.game.states;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.inject.Injector;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.engine.inject.CoreInjectConfig;
import com.tokelon.toktales.core.game.states.IControlScheme;
import com.tokelon.toktales.core.test.engine.inject.CoreMockPlatformInjectModule;
import com.tokelon.toktales.test.core.game.states.enginestate.EngineGamescene;
import com.tokelon.toktales.test.core.game.states.enginestate.EngineGamestate;
import com.tokelon.toktales.test.core.game.states.enginestate.EngineGamestateControlHandler;
import com.tokelon.toktales.test.core.game.states.enginestate.EngineGamestateRender;
import com.tokelon.toktales.test.core.game.states.enginestate.IEngineGamescene;
import com.tokelon.toktales.test.core.game.states.enginestate.IEngineGamestate;
import com.tokelon.toktales.test.core.game.states.enginestate.IEngineGamestate.IEngineGamestateType;
import com.tokelon.toktales.test.core.game.states.enginestate.IEngineGamestateControlHandler;
import com.tokelon.toktales.test.core.game.states.enginestate.IEngineGamestateControlHandler.IEngineGamestateControlHandlerFactory;
import com.tokelon.toktales.test.core.game.states.enginestate.IEngineGamestateInputHandler;
import com.tokelon.toktales.test.core.game.states.enginestate.IEngineGamestateInputHandler.IEngineGamestateInputHandlerFactory;
import com.tokelon.toktales.test.core.game.states.enginestate.IEngineGamestateRender;
import com.tokelon.toktales.test.core.game.states.enginestate.IEngineGamestateRender.IEngineGamestateRenderFactory;
import com.tokelon.toktales.test.core.game.states.enginestate.subenginestate.ISubEngineGamescene;
import com.tokelon.toktales.test.core.game.states.enginestate.subenginestate.ISubEngineGamestate;
import com.tokelon.toktales.test.core.game.states.enginestate.subenginestate.SubEngineGamescene;
import com.tokelon.toktales.test.core.game.states.enginestate.subenginestate.SubEngineGamestate;

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
				.implement(IEngineGamestateRender.class, EngineGamestateRender.class)
				.build(IEngineGamestateRenderFactory.class));
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
	

	public static Injector createCoreStatesInjector() {
		CoreInjectConfig injectConfig = new CoreInjectConfig();
		injectConfig.extend(new CoreMockPlatformInjectModule());
		
		injectConfig.override(new CoreTestStatesInjectModule());
		
		Injector injector = injectConfig.createInjector();
		return injector;
	}
	
}
