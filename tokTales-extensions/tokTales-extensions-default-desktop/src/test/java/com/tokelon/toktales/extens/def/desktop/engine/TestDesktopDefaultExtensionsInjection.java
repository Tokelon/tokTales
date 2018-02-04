package com.tokelon.toktales.extens.def.desktop.engine;

import org.junit.Test;

import com.google.inject.Injector;
import com.tokelon.toktales.core.game.IGameAdapter;
import com.tokelon.toktales.desktop.engine.inject.DesktopSetupInjectModule;

public class TestDesktopDefaultExtensionsInjection {

	
	@Test
	public void injectorCreationWithSetupModule_ShouldSucceed() {
		DesktopDefaultExtensionsInjectConfig injectConfig = new DesktopDefaultExtensionsInjectConfig();
		
		IGameAdapter adapter = new IGameAdapter.EmptyGameAdapter();
		injectConfig.override(new DesktopSetupInjectModule(adapter));
		
		Injector injector = injectConfig.createInjector();
	}
	
}
