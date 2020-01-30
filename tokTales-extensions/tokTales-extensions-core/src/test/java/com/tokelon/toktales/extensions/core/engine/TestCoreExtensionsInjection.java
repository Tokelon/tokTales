package com.tokelon.toktales.extensions.core.engine;

import org.junit.Test;

import com.google.inject.CreationException;
import com.google.inject.Injector;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.setup.BaseInjectSetup;
import com.tokelon.toktales.core.game.state.IControlScheme;
import com.tokelon.toktales.core.game.state.IGameStateInputHandler;
import com.tokelon.toktales.core.test.engine.inject.CoreMockPlatformInjectModule;
import com.tokelon.toktales.core.test.engine.inject.InjectionTestHelper;
import com.tokelon.toktales.extensions.core.engine.inject.MasterCoreExtensionsInjectConfig;
import com.tokelon.toktales.extensions.core.game.state.console.ConsoleGamestate;
import com.tokelon.toktales.extensions.core.game.state.localmap.ILocalMapControlScheme;
import com.tokelon.toktales.extensions.core.game.state.localmap.ILocalMapInputHandler;
import com.tokelon.toktales.extensions.core.test.engine.inject.CoreExtensionsMockPlatformInjectModule;
import com.tokelon.toktales.tools.core.sub.inject.scope.ForClass;

public class TestCoreExtensionsInjection {
	
	
	public static final Class<?>[] CORE_DEFAULT_EXTENSIONS_EXPECTED_BINDING_TYPES =
	{
			ILocalMapInputHandler.ILocalMapInputHandlerFactory.class,
			ILocalMapControlScheme.class
	};
	
	public static final Class<?>[][] CORE_DEFAULT_EXTENSIONS_EXPECTED_BINDING_ANNOTATIONS = 
	{
			{ IControlScheme.class, ForClass.class },
			{ IGameStateInputHandler.class, ForClass.class, ConsoleGamestate.class }
	};
	
	public static final Class<?>[] ALL_EXPECTED_BINDING_TYPES = 
			InjectionTestHelper.combineExpectedBindingTypes(InjectionTestHelper.CORE_EXPECTED_BINDING_TYPES, CORE_DEFAULT_EXTENSIONS_EXPECTED_BINDING_TYPES);
	
	public static final Class<?>[][] ALL_EXPECTED_BINDING_ANNOTATIONS = 
			InjectionTestHelper.combineExpectedBindingAnnotations(InjectionTestHelper.CORE_EXPECTED_BINDING_ANNOTATIONS, CORE_DEFAULT_EXTENSIONS_EXPECTED_BINDING_ANNOTATIONS);
	
	
	@Test(expected = CreationException.class)
	public void injectorCreationWithoutExpectedBindings_ShouldFail() {
		MasterCoreExtensionsInjectConfig injectConfig = new MasterCoreExtensionsInjectConfig();
		Injector injector = injectConfig.createInjector();
	}
	
	@Test
	public void injectorCreation_ShouldOnlyFailOnExpectedBindings() {
		MasterCoreExtensionsInjectConfig injectConfig = new MasterCoreExtensionsInjectConfig();
		
		InjectionTestHelper.assertInjectorCreationFailsWithExpectedBindings(injectConfig, ALL_EXPECTED_BINDING_TYPES, ALL_EXPECTED_BINDING_ANNOTATIONS);
	}
	

	@Test
	public void injectorCreationWithMockPlatform_ShouldSucceed() {
		MasterCoreExtensionsInjectConfig injectConfig = new MasterCoreExtensionsInjectConfig();
		
		injectConfig.extend(new CoreMockPlatformInjectModule());
		injectConfig.extend(new CoreExtensionsMockPlatformInjectModule());
		
		Injector injector = injectConfig.createInjector();
	}
	
	@Test
	public void engineCreationWithMockPlatform_ShouldSucceed() {
		MasterCoreExtensionsInjectConfig injectConfig = new MasterCoreExtensionsInjectConfig();
		
		injectConfig.extend(new CoreMockPlatformInjectModule());
		injectConfig.extend(new CoreExtensionsMockPlatformInjectModule());

		Injector injector = injectConfig.createInjector();
		IEngineContext engineContext = injector.getInstance(IEngineContext.class);
	}
	
	@Test
	public void setupCreationWithMockPlatform_ShouldSucceed() throws EngineException {
		MasterCoreExtensionsInjectConfig injectConfig = new MasterCoreExtensionsInjectConfig();
		
		injectConfig.extend(new CoreMockPlatformInjectModule());
		injectConfig.extend(new CoreExtensionsMockPlatformInjectModule());

		BaseInjectSetup setup = new BaseInjectSetup();
		IEngineContext engineContext = setup.create(injectConfig);
	}
	
}
