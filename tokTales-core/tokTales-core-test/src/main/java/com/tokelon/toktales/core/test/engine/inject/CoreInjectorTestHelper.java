package com.tokelon.toktales.core.test.engine.inject;

import com.google.inject.Injector;
import com.google.inject.Module;
import com.tokelon.toktales.core.engine.inject.CoreInjectConfig;

public class CoreInjectorTestHelper {

	private CoreInjectorTestHelper() {}
	
	
	public static Injector createCoreMockInjector() {
		return createCoreMockInjector(new Module[0], new Module[0]);
	}
	
	public static Injector createCoreMockInjector(Module[] extendModules, Module[] overrideModules) {
		CoreInjectConfig injectConfig = new CoreInjectConfig();
		
		injectConfig.extend(new CoreMockPlatformInjectModule());
		
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
