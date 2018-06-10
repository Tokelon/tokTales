package com.tokelon.toktales.extens.def.desktop.engine;

import org.junit.Test;

import com.google.inject.Injector;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.inject.BaseSetupInjectModule;
import com.tokelon.toktales.core.engine.setup.BaseInjectSetup;
import com.tokelon.toktales.extens.def.desktop.engine.inject.DesktopDefExtensInjectConfig;
import com.tokelon.toktales.test.core.game.DummyGameAdapter;
import com.tokelon.toktales.test.desktop.engine.inject.DesktopMockPlatformInjectModule;

public class TestDesktopDefExtensInjection {

	
	@Test
	public void injectorCreationWithSetupModule_ShouldSucceed() {
		DesktopDefExtensInjectConfig injectConfig = new DesktopDefExtensInjectConfig();
		
		injectConfig.extend(new BaseSetupInjectModule(DummyGameAdapter.class));
		
		Injector injector = injectConfig.createInjector();
	}
	
	
	@Test
	public void setupCreationWithMockPlatform_ShouldSucceed() throws EngineException {
		DesktopDefExtensInjectConfig injectConfig = new DesktopDefExtensInjectConfig();

		injectConfig.override(new DesktopMockPlatformInjectModule());
		// No DesktopDefExtensPlatformInjectModule needed yet
		
		BaseInjectSetup setup = new BaseInjectSetup();
		IEngineContext engineContext = setup.create(injectConfig);
	}
	
}
