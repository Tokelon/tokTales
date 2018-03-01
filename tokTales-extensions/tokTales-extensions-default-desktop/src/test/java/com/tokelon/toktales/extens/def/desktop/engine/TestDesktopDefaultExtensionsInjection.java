package com.tokelon.toktales.extens.def.desktop.engine;

import org.junit.Test;

import com.google.inject.Injector;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.setup.BaseInjectSetup;
import com.tokelon.toktales.desktop.engine.inject.DesktopSetupInjectModule;
import com.tokelon.toktales.test.core.game.DummyGameAdapter;
import com.tokelon.toktales.test.desktop.engine.inject.DesktopMockPlatformInjectModule;

public class TestDesktopDefaultExtensionsInjection {

	
	@Test
	public void injectorCreationWithSetupModule_ShouldSucceed() {
		DesktopDefaultExtensionsInjectConfig injectConfig = new DesktopDefaultExtensionsInjectConfig();
		
		injectConfig.override(new DesktopSetupInjectModule(DummyGameAdapter.class));
		
		Injector injector = injectConfig.createInjector();
	}
	
	
	@Test
	public void setupCreationWithMockPlatform_ShouldSucceed() throws EngineException {
		DesktopDefaultExtensionsInjectConfig injectConfig = new DesktopDefaultExtensionsInjectConfig();

		injectConfig.extend(new DesktopMockPlatformInjectModule());
		// No DesktopDefExtensPlatformInjectModule needed yet
		
		BaseInjectSetup setup = new BaseInjectSetup();
		IEngineContext engineContext = setup.create(injectConfig);
	}
	
}
