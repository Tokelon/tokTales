package com.tokelon.toktales.desktop.engine.inject;

import org.junit.Test;

import com.google.inject.Injector;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.inject.BaseSetupInjectModule;
import com.tokelon.toktales.core.engine.setup.BaseInjectSetup;
import com.tokelon.toktales.test.core.engine.inject.TestInjectionHelper;
import com.tokelon.toktales.test.core.game.DummyGameAdapter;
import com.tokelon.toktales.test.desktop.engine.inject.DesktopMockPlatformInjectModule;

public class TestDesktopInjection {

	public static final String[] DESKTOP_EXPECTED_BINDING_TYPES = { "IGameAdapter" };
	
	
	@Test
	public void injectorCreationWithoutExpectedBindings_ShouldFail() {
		DesktopInjectConfig injectConfig = new DesktopInjectConfig();
		
		TestInjectionHelper.assertInjectorCreationFailsWithExpectedBindings(injectConfig, DESKTOP_EXPECTED_BINDING_TYPES, new String[0][0]);
	}
	
	
	@Test
	public void injectorCreationWithSetupModule_ShouldSucceed() {
		DesktopInjectConfig injectConfig = new DesktopInjectConfig();
		
		injectConfig.extend(new BaseSetupInjectModule(DummyGameAdapter.class));
		
		Injector injector = injectConfig.createInjector();
	}
	
	@Test
	public void engineCreationWithSetupModule_ShouldSucceed() {
		DesktopInjectConfig injectConfig = new DesktopInjectConfig();

		injectConfig.extend(new BaseSetupInjectModule(DummyGameAdapter.class));
		
		Injector injector = injectConfig.createInjector();
		IEngineContext engineContext = injector.getInstance(IEngineContext.class);
	}

	@Test
	public void setupCreationWithSetupModule_ShouldSucceed() throws EngineException {
		DesktopInjectConfig injectConfig = new DesktopInjectConfig();

		injectConfig.extend(new BaseSetupInjectModule(DummyGameAdapter.class));

		BaseInjectSetup setup = new BaseInjectSetup();
		IEngineContext engineContext = setup.create(injectConfig);
	}
	
	
	@Test
	public void setupCreationWithMockPlatform_ShouldSucceed() throws EngineException {
		DesktopInjectConfig injectConfig = new DesktopInjectConfig();
		
		injectConfig.override(new DesktopMockPlatformInjectModule());
		
		BaseInjectSetup setup = new BaseInjectSetup();
		IEngineContext engineContext = setup.create(injectConfig);
	}
	
}
