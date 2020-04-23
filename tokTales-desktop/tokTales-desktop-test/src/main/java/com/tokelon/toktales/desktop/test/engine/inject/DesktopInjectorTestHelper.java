package com.tokelon.toktales.desktop.test.engine.inject;

import com.google.inject.Injector;
import com.google.inject.Module;
import com.tokelon.toktales.core.engine.inject.BaseSetupInjectModule;
import com.tokelon.toktales.core.engine.setup.DefaultEngineSetup;
import com.tokelon.toktales.core.test.game.DummyGameAdapter;
import com.tokelon.toktales.desktop.engine.inject.MasterDesktopInjectConfig;

public class DesktopInjectorTestHelper {

	private DesktopInjectorTestHelper() {}


	public static Injector createDesktopInjector() {
		return createDesktopInjector(new Module[0], new Module[0]);
	}

	public static Injector createDesktopInjector(Module[] extendModules, Module[] overrideModules) {
		MasterDesktopInjectConfig injectConfig = new MasterDesktopInjectConfig();

		injectConfig.extend(new BaseSetupInjectModule(DummyGameAdapter.class, new DefaultEngineSetup()));

		if(extendModules.length > 0) {
			injectConfig.extend(extendModules);
		}
		if(overrideModules.length > 0) {
			injectConfig.override(overrideModules);
		}

		Injector injector = injectConfig.createInjector();
		return injector;
	}


	public static Injector createDesktopMockInjector() {
		return createDesktopMockInjector(new Module[0], new Module[0]);
	}

	public static Injector createDesktopMockInjector(Module[] extendModules, Module[] overrideModules) {
		MasterDesktopInjectConfig injectConfig = new MasterDesktopInjectConfig();

		injectConfig.override(new DesktopMockPlatformInjectModule());

		if(extendModules.length > 0) {
			injectConfig.extend(extendModules);
		}
		if(overrideModules.length > 0) {
			injectConfig.override(overrideModules);
		}

		Injector injector = injectConfig.createInjector();
		return injector;
	}

}
