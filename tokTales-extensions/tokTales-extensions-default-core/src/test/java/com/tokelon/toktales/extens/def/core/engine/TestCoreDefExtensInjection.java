package com.tokelon.toktales.extens.def.core.engine;

import org.junit.Test;

import com.google.inject.CreationException;
import com.google.inject.Injector;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.inject.ForClass;
import com.tokelon.toktales.core.engine.setup.BaseInjectSetup;
import com.tokelon.toktales.core.game.states.IControlScheme;
import com.tokelon.toktales.core.game.states.IGameStateInputHandler;
import com.tokelon.toktales.core.test.engine.inject.CoreMockPlatformInjectModule;
import com.tokelon.toktales.core.test.engine.inject.InjectionTestHelper;
import com.tokelon.toktales.extens.def.core.engine.inject.MasterCoreDefExtensInjectConfig;
import com.tokelon.toktales.extens.def.core.game.states.console.ConsoleGamestate;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapControlScheme;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapInputHandler;
import com.tokelon.toktales.extens.def.core.test.engine.inject.CoreDefExtensMockPlatformInjectModule;

public class TestCoreDefExtensInjection {
	
	
	public static final String[] CORE_DEFAULT_EXTENSIONS_EXPECTED_BINDING_TYPES =
	{
			ILocalMapInputHandler.ILocalMapInputHandlerFactory.class.getName(),
			ILocalMapControlScheme.class.getName()
	};
	
	public static final String[][] CORE_DEFAULT_EXTENSIONS_EXPECTED_BINDING_ANNOTATIONS = 
	{
			{ IControlScheme.class.getName(), ForClass.class.getName() },
			{ IGameStateInputHandler.class.getName(), ForClass.class.getName(), ConsoleGamestate.class.getName() }
	};
	
	public static final String[] ALL_EXPECTED_BINDING_TYPES = 
			InjectionTestHelper.combineExpectedBindingTypes(InjectionTestHelper.CORE_EXPECTED_BINDING_TYPES, CORE_DEFAULT_EXTENSIONS_EXPECTED_BINDING_TYPES);
	
	public static final String[][] ALL_EXPECTED_BINDING_ANNOTATIONS = 
			InjectionTestHelper.combineExpectedBindingAnnotations(InjectionTestHelper.CORE_EXPECTED_BINDING_ANNOTATIONS, CORE_DEFAULT_EXTENSIONS_EXPECTED_BINDING_ANNOTATIONS);
	
	
	@Test(expected = CreationException.class)
	public void injectorCreationWithoutExpectedBindings_ShouldFail() {
		MasterCoreDefExtensInjectConfig injectConfig = new MasterCoreDefExtensInjectConfig();
		Injector injector = injectConfig.createInjector();
	}
	
	@Test
	public void injectorCreation_ShouldOnlyFailOnExpectedBindings() {
		MasterCoreDefExtensInjectConfig injectConfig = new MasterCoreDefExtensInjectConfig();
		
		InjectionTestHelper.assertInjectorCreationFailsWithExpectedBindings(injectConfig, ALL_EXPECTED_BINDING_TYPES, ALL_EXPECTED_BINDING_ANNOTATIONS);
	}
	

	@Test
	public void injectorCreationWithMockPlatform_ShouldSucceed() {
		MasterCoreDefExtensInjectConfig injectConfig = new MasterCoreDefExtensInjectConfig();
		
		injectConfig.extend(new CoreMockPlatformInjectModule());
		injectConfig.extend(new CoreDefExtensMockPlatformInjectModule());
		
		Injector injector = injectConfig.createInjector();
	}
	
	@Test
	public void engineCreationWithMockPlatform_ShouldSucceed() {
		MasterCoreDefExtensInjectConfig injectConfig = new MasterCoreDefExtensInjectConfig();
		
		injectConfig.extend(new CoreMockPlatformInjectModule());
		injectConfig.extend(new CoreDefExtensMockPlatformInjectModule());

		Injector injector = injectConfig.createInjector();
		IEngineContext engineContext = injector.getInstance(IEngineContext.class);
	}
	
	@Test
	public void setupCreationWithMockPlatform_ShouldSucceed() throws EngineException {
		MasterCoreDefExtensInjectConfig injectConfig = new MasterCoreDefExtensInjectConfig();
		
		injectConfig.extend(new CoreMockPlatformInjectModule());
		injectConfig.extend(new CoreDefExtensMockPlatformInjectModule());

		BaseInjectSetup setup = new BaseInjectSetup();
		IEngineContext engineContext = setup.create(injectConfig);
	}
	
}
