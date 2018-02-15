package com.tokelon.toktales.extens.def.desktop.engine;

import org.junit.Test;

import com.google.inject.Injector;
import com.tokelon.toktales.desktop.engine.inject.DesktopSetupInjectModule;
import com.tokelon.toktales.test.core.game.DummyGameAdapter;

public class TestDesktopDefaultExtensionsInjection {

	
	@Test
	public void injectorCreationWithSetupModule_ShouldSucceed() {
		DesktopDefaultExtensionsInjectConfig injectConfig = new DesktopDefaultExtensionsInjectConfig();
		
		injectConfig.override(new DesktopSetupInjectModule(DummyGameAdapter.class));
		
		Injector injector = injectConfig.createInjector();
	}
	
}
