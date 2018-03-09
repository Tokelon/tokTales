package com.tokelon.toktales.extens.def.android.engine;

import static org.mockito.Mockito.mock;

import org.junit.Test;

import com.google.inject.Injector;
import com.tokelon.toktales.android.engine.inject.AndroidSetupInjectModule;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.setup.BaseInjectSetup;
import com.tokelon.toktales.extens.def.android.engine.inject.AndroidDefaultExtensionsInjectConfig;
import com.tokelon.toktales.test.android.engine.inject.AndroidMockPlatformInjectModule;
import com.tokelon.toktales.test.core.game.DummyGameAdapter;

import android.content.Context;

public class TestAndroidDefaultExtensionsInjection {
	
	private static final Context mockedContext = mock(Context.class);

	
	@Test
	public void injectorCreationWithSetupModule_ShouldSucceed() {
		AndroidDefaultExtensionsInjectConfig injectConfig = new AndroidDefaultExtensionsInjectConfig();
		
		injectConfig.override(new AndroidSetupInjectModule(DummyGameAdapter.class, mockedContext));
		
		Injector injector = injectConfig.createInjector();
	}
	

	@Test
	public void setupCreationWithMockPlatform_ShouldSucceed() throws EngineException {
		AndroidDefaultExtensionsInjectConfig injectConfig = new AndroidDefaultExtensionsInjectConfig();
		
		injectConfig.override(new AndroidMockPlatformInjectModule());
		// No AndroidDefExtensMockPlatformInjectModule needed yet
		
		BaseInjectSetup setup = new BaseInjectSetup();
		IEngineContext engineContext = setup.create(injectConfig);
	}
	
}
