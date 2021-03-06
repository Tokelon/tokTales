package com.tokelon.toktales.extensions.desktop.engine;

import org.junit.Test;

import com.google.inject.Injector;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.inject.BaseSetupInjectModule;
import com.tokelon.toktales.core.engine.setup.DefaultEngineSetup;
import com.tokelon.toktales.core.test.game.DummyGameAdapter;
import com.tokelon.toktales.desktop.test.engine.inject.DesktopMockPlatformInjectModule;
import com.tokelon.toktales.extensions.desktop.engine.inject.MasterDesktopExtensionsInjectConfig;

public class TestDesktopExtensionsInjection {


	@Test
	public void injectorCreationWithSetupModule_ShouldSucceed() {
		MasterDesktopExtensionsInjectConfig injectConfig = new MasterDesktopExtensionsInjectConfig();

		injectConfig.extend(new BaseSetupInjectModule(DummyGameAdapter.class, new DefaultEngineSetup()));

		Injector injector = injectConfig.createInjector();
	}


	@Test
	public void setupCreationWithMockPlatform_ShouldSucceed() throws EngineException {
		MasterDesktopExtensionsInjectConfig injectConfig = new MasterDesktopExtensionsInjectConfig();

		injectConfig.override(new DesktopMockPlatformInjectModule());
		// No DesktopDefExtensPlatformInjectModule needed yet

		DefaultEngineSetup setup = new DefaultEngineSetup();
		IEngineContext engineContext = setup.create(injectConfig);
	}

}
