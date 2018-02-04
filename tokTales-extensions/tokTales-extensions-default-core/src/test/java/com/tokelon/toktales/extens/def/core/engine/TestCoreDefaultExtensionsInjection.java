package com.tokelon.toktales.extens.def.core.engine;

import org.junit.Test;

import com.google.inject.CreationException;
import com.google.inject.Injector;
import com.tokelon.toktales.test.core.engine.inject.TestInjectionHelper;

public class TestCoreDefaultExtensionsInjection {
	
	public static final String[] CORE_DEFAULT_EXTENSIONS_EXPECTED_BINDING_TYPES = TestInjectionHelper.CORE_EXPECTED_BINDING_TYPES;
	
	
	@Test(expected = CreationException.class)
	public void injectorCreationShouldFail() {
		
		CoreDefaultExtensionsInjectConfig injectConfig = new CoreDefaultExtensionsInjectConfig();
		Injector injector = injectConfig.createInjector();	
	}
	
	
	@Test
	public void injectorCreationShouldOnlyFailOnExpectedBindings() {
		CoreDefaultExtensionsInjectConfig injectConfig = new CoreDefaultExtensionsInjectConfig();
		
		TestInjectionHelper.assertInjectorCreationFailsWithExpectedBindings(injectConfig, CORE_DEFAULT_EXTENSIONS_EXPECTED_BINDING_TYPES);
	}
	
}
