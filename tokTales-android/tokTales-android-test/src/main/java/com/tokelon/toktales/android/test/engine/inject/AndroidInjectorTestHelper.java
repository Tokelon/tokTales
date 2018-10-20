package com.tokelon.toktales.android.test.engine.inject;

import android.content.Context;

import com.google.inject.Injector;
import com.google.inject.Module;
import com.tokelon.toktales.android.engine.inject.AndroidInjectConfig;
import com.tokelon.toktales.android.engine.inject.AndroidSetupInjectModule;
import com.tokelon.toktales.core.engine.inject.BaseSetupInjectModule;
import com.tokelon.toktales.core.test.game.DummyGameAdapter;

import static org.mockito.Mockito.mock;

public class AndroidInjectorTestHelper {

	private AndroidInjectorTestHelper() {}
	
	
	public static final Context MOCKED_ANDROID_CONTEXT = mock(Context.class);

	
	public static Injector createAndroidInjector() {
		return createAndroidInjector(new Module[0], new Module[0], MOCKED_ANDROID_CONTEXT);
	}
	
	public static Injector createAndroidInjector(Context androidContext) {
		return createAndroidInjector(new Module[0], new Module[0], androidContext);
	}
	
	public static Injector createAndroidInjector(Module[] extendModules, Module[] overrideModules) {
		return createAndroidInjector(extendModules, overrideModules, MOCKED_ANDROID_CONTEXT);
	}
	
	public static Injector createAndroidInjector(Module[] extendModules, Module[] overrideModules, Context androidContext) {
		AndroidInjectConfig injectConfig = new AndroidInjectConfig();
		
		injectConfig.extend(new BaseSetupInjectModule(DummyGameAdapter.class), new AndroidSetupInjectModule(androidContext));
		
		if(extendModules.length > 0) {
			injectConfig.extend(extendModules);
		}
		if(overrideModules.length > 0) {
			injectConfig.override(overrideModules);
		}
		
		Injector injector = injectConfig.createInjector();
		return injector;
	}
	
	
	public static Injector createAndroidMockInjector() {
		return createAndroidMockInjector(new Module[0], new Module[0]);
	}
	
	public static Injector createAndroidMockInjector(Module[] extendModules, Module[] overrideModules) {
		AndroidInjectConfig injectConfig = new AndroidInjectConfig();
		
		injectConfig.override(new AndroidMockPlatformInjectModule());
		
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