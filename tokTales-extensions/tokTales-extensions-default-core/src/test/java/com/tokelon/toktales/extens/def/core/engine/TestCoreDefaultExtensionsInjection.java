package com.tokelon.toktales.extens.def.core.engine;

import org.junit.Test;

import com.google.inject.CreationException;
import com.google.inject.Injector;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.setup.BaseInjectSetup;
import com.tokelon.toktales.test.core.engine.inject.CoreMockPlatformInjectModule;
import com.tokelon.toktales.test.core.engine.inject.TestInjectionHelper;
import com.tokelon.toktales.test.extens.def.core.engine.inject.CoreDefExtensMockPlatformInjectModule;

public class TestCoreDefaultExtensionsInjection {
	
	
	public static final String[] CORE_DEFAULT_EXTENSIONS_EXPECTED_BINDING_TYPES =
	{
			"ILocalMapInputHandler$ILocalMapInputHandlerFactory",
			"IConsoleGamestateInputHandler$IConsoleGamestateInputHandlerFactory"
	};
	
	public static final String[][] CORE_DEFAULT_EXTENSIONS_EXPECTED_BINDING_ANNOTATIONS = 
	{
			{ "IControlScheme", "ForClass", "LocalMapGamestate" },
			{ "IGameStateInputHandler", "ForClass", "ConsoleGamestate"}
	};
	
	public static final String[] ALL_EXPECTED_BINDING_TYPES = 
			TestInjectionHelper.combineExpectedBindingTypes(TestInjectionHelper.CORE_EXPECTED_BINDING_TYPES, CORE_DEFAULT_EXTENSIONS_EXPECTED_BINDING_TYPES);
	
	public static final String[][] ALL_EXPECTED_BINDING_ANNOTATIONS = 
			TestInjectionHelper.combineExpectedBindingAnnotations(TestInjectionHelper.CORE_EXPECTED_BINDING_ANNOTATIONS, CORE_DEFAULT_EXTENSIONS_EXPECTED_BINDING_ANNOTATIONS);
	
	
	@Test(expected = CreationException.class)
	public void injectorCreationWithoutExpectedBindings_ShouldFail() {
		
		CoreDefaultExtensionsInjectConfig injectConfig = new CoreDefaultExtensionsInjectConfig();
		Injector injector = injectConfig.createInjector();	
	}
	
	@Test
	public void injectorCreation_ShouldOnlyFailOnExpectedBindings() {
		CoreDefaultExtensionsInjectConfig injectConfig = new CoreDefaultExtensionsInjectConfig();
		
		TestInjectionHelper.assertInjectorCreationFailsWithExpectedBindings(injectConfig, ALL_EXPECTED_BINDING_TYPES, ALL_EXPECTED_BINDING_ANNOTATIONS);
	}
	

	@Test
	public void injectorCreationWithMockPlatform_ShouldSucceed() {
		CoreDefaultExtensionsInjectConfig injectConfig = new CoreDefaultExtensionsInjectConfig();
		
		injectConfig.extend(new CoreMockPlatformInjectModule());
		injectConfig.extend(new CoreDefExtensMockPlatformInjectModule());
		
		Injector injector = injectConfig.createInjector();
	}
	
	@Test
	public void engineCreationWithMockPlatform_ShouldSucceed() {
		CoreDefaultExtensionsInjectConfig injectConfig = new CoreDefaultExtensionsInjectConfig();
		
		injectConfig.extend(new CoreMockPlatformInjectModule());
		injectConfig.extend(new CoreDefExtensMockPlatformInjectModule());

		Injector injector = injectConfig.createInjector();
		IEngineContext engineContext = injector.getInstance(IEngineContext.class);
	}
	
	@Test
	public void setupCreationWithMockPlatform_ShouldSucceed() throws EngineException {
		CoreDefaultExtensionsInjectConfig injectConfig = new CoreDefaultExtensionsInjectConfig();
		
		injectConfig.extend(new CoreMockPlatformInjectModule());
		injectConfig.extend(new CoreDefExtensMockPlatformInjectModule());

		BaseInjectSetup setup = new BaseInjectSetup();
		IEngineContext engineContext = setup.create(injectConfig);
	}
	
}
