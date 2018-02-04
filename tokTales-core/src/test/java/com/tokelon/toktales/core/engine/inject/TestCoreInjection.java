package com.tokelon.toktales.core.engine.inject;

import org.junit.Test;

import com.google.inject.CreationException;
import com.google.inject.Injector;
import com.tokelon.toktales.test.core.engine.inject.TestInjectionHelper;

public class TestCoreInjection {

	
	@Test(expected = CreationException.class)
	public void injectorCreationWithoutExpectedBindings_ShouldFail() {
		
		CoreInjectConfig injectConfig = new CoreInjectConfig();
		Injector injector = injectConfig.createInjector();
	}

	
	@Test
	public void injectorCreation_ShouldOnlyFailOnExpectedBindings() {
		CoreInjectConfig injectConfig = new CoreInjectConfig();

		TestInjectionHelper.assertInjectorCreationFailsWithExpectedBindings(injectConfig, TestInjectionHelper.CORE_EXPECTED_BINDING_TYPES);
	}
	
	
}
