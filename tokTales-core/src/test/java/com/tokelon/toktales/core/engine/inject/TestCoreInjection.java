package com.tokelon.toktales.core.engine.inject;

import org.junit.Test;

import com.google.inject.CreationException;
import com.google.inject.Injector;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.setup.BaseInjectSetup;
import com.tokelon.toktales.test.core.engine.inject.CoreMockPlatformInjectModule;
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

		TestInjectionHelper.assertInjectorCreationFailsWithExpectedBindings(injectConfig, TestInjectionHelper.CORE_EXPECTED_BINDING_TYPES, TestInjectionHelper.CORE_EXPECTED_BINDING_ANNOTATIONS);
	}
	
	
	@Test
	public void injectorCreationWithMockPlatform_ShouldSucceed() {
		CoreInjectConfig injectConfig = new CoreInjectConfig();
		
		injectConfig.extend(new CoreMockPlatformInjectModule());
		
		Injector injector = injectConfig.createInjector();
	}
	
	@Test
	public void engineCreationWithMockPlatform_ShouldSucceed() {
		CoreInjectConfig injectConfig = new CoreInjectConfig();
		
		injectConfig.extend(new CoreMockPlatformInjectModule());
		
		Injector injector = injectConfig.createInjector();
		IEngineContext engineContext = injector.getInstance(IEngineContext.class);
	}
	
	@Test
	public void setupCreationWithMockPlatform_ShouldSucceed() throws EngineException {
		CoreInjectConfig injectConfig = new CoreInjectConfig();
		
		injectConfig.extend(new CoreMockPlatformInjectModule());
		
		BaseInjectSetup setup = new BaseInjectSetup();
		IEngineContext engineContext = setup.create(injectConfig);
	}
	
}
