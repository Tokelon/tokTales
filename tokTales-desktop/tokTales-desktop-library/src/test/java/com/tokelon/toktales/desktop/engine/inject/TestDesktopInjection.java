package com.tokelon.toktales.desktop.engine.inject;

import org.junit.Test;

import com.google.inject.Injector;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.inject.BaseSetupInjectModule;
import com.tokelon.toktales.core.engine.setup.BaseInjectSetup;
import com.tokelon.toktales.core.game.IGameAdapter;
import com.tokelon.toktales.core.test.engine.inject.InjectionTestHelper;
import com.tokelon.toktales.core.test.game.DummyGameAdapter;
import com.tokelon.toktales.desktop.test.engine.inject.DesktopMockPlatformInjectModule;

public class TestDesktopInjection {


	public static final Class<?>[] DESKTOP_EXPECTED_BINDING_TYPES =
	{
			IGameAdapter.class
	};
	
	
	@Test
	public void injectorCreationWithoutExpectedBindings_ShouldFail() {
		MasterDesktopInjectConfig injectConfig = new MasterDesktopInjectConfig();
		
		InjectionTestHelper.assertInjectorCreationFailsWithExpectedBindings(injectConfig, DESKTOP_EXPECTED_BINDING_TYPES, new Class<?>[0][0]);
	}
	
	
	@Test
	public void injectorCreationWithSetupModule_ShouldSucceed() {
		MasterDesktopInjectConfig injectConfig = new MasterDesktopInjectConfig();
		
		injectConfig.extend(new BaseSetupInjectModule(DummyGameAdapter.class));
		
		Injector injector = injectConfig.createInjector();
	}
	
	@Test
	public void engineCreationWithSetupModule_ShouldSucceed() {
		MasterDesktopInjectConfig injectConfig = new MasterDesktopInjectConfig();

		injectConfig.extend(new BaseSetupInjectModule(DummyGameAdapter.class));
		
		Injector injector = injectConfig.createInjector();
		IEngineContext engineContext = injector.getInstance(IEngineContext.class);
	}

	@Test
	public void setupCreationWithSetupModule_ShouldSucceed() throws EngineException {
		MasterDesktopInjectConfig injectConfig = new MasterDesktopInjectConfig();

		injectConfig.extend(new BaseSetupInjectModule(DummyGameAdapter.class));

		BaseInjectSetup setup = new BaseInjectSetup();
		IEngineContext engineContext = setup.create(injectConfig);
	}
	
	
	@Test
	public void setupCreationWithMockPlatform_ShouldSucceed() throws EngineException {
		MasterDesktopInjectConfig injectConfig = new MasterDesktopInjectConfig();
		
		injectConfig.override(new DesktopMockPlatformInjectModule());
		
		BaseInjectSetup setup = new BaseInjectSetup();
		IEngineContext engineContext = setup.create(injectConfig);
	}
	
}
