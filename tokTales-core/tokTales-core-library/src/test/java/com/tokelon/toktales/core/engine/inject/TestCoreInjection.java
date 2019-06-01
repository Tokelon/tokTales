package com.tokelon.toktales.core.engine.inject;

import org.junit.Test;

import com.google.inject.CreationException;
import com.google.inject.Injector;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.setup.BaseInjectSetup;
import com.tokelon.toktales.core.test.engine.inject.CoreMockPlatformInjectModule;
import com.tokelon.toktales.core.test.engine.inject.InjectionTestHelper;

public class TestCoreInjection {


	@Test(expected = CreationException.class)
	public void injectorCreationWithoutExpectedBindings_ShouldFail() {
		
		MasterCoreInjectConfig injectConfig = new MasterCoreInjectConfig();
		Injector injector = injectConfig.createInjector();
	}
	
	@Test
	public void injectorCreation_ShouldOnlyFailOnExpectedBindings() {
		MasterCoreInjectConfig injectConfig = new MasterCoreInjectConfig();

		InjectionTestHelper.assertInjectorCreationFailsWithExpectedBindings(injectConfig, InjectionTestHelper.CORE_EXPECTED_BINDING_TYPES, InjectionTestHelper.CORE_EXPECTED_BINDING_ANNOTATIONS);
	}
	
	
	@Test
	public void injectorCreationWithMockPlatform_ShouldSucceed() {
		MasterCoreInjectConfig injectConfig = new MasterCoreInjectConfig();
		
		injectConfig.extend(new CoreMockPlatformInjectModule());
		
		Injector injector = injectConfig.createInjector();
	}
	
	@Test
	public void engineCreationWithMockPlatform_ShouldSucceed() {
		MasterCoreInjectConfig injectConfig = new MasterCoreInjectConfig();
		
		injectConfig.extend(new CoreMockPlatformInjectModule());
		
		Injector injector = injectConfig.createInjector();
		IEngineContext engineContext = injector.getInstance(IEngineContext.class);
	}
	
	@Test
	public void setupCreationWithMockPlatform_ShouldSucceed() throws EngineException {
		MasterCoreInjectConfig injectConfig = new MasterCoreInjectConfig();
		
		injectConfig.extend(new CoreMockPlatformInjectModule());
		
		BaseInjectSetup setup = new BaseInjectSetup();
		IEngineContext engineContext = setup.create(injectConfig);
	}
	
}
