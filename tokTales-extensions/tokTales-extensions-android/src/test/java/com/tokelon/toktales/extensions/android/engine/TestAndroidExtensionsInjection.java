package com.tokelon.toktales.extensions.android.engine;

import static org.mockito.Mockito.mock;

import org.junit.Test;

import com.google.inject.Injector;
import com.tokelon.toktales.android.engine.inject.AndroidSetupInjectModule;
import com.tokelon.toktales.android.test.engine.inject.AndroidMockPlatformInjectModule;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.inject.BaseSetupInjectModule;
import com.tokelon.toktales.core.engine.setup.BaseInjectSetup;
import com.tokelon.toktales.core.test.game.DummyGameAdapter;
import com.tokelon.toktales.extensions.android.engine.inject.MasterAndroidExtensionsInjectConfig;

import android.content.Context;

public class TestAndroidExtensionsInjection {
	
	private static final Context mockedContext = mock(Context.class);

	
	@Test
	public void injectorCreationWithSetupModule_ShouldSucceed() {
		MasterAndroidExtensionsInjectConfig injectConfig = new MasterAndroidExtensionsInjectConfig();
		
		injectConfig.extend(new BaseSetupInjectModule(DummyGameAdapter.class), new AndroidSetupInjectModule(mockedContext));
		
		Injector injector = injectConfig.createInjector();
	}
	

	@Test
	public void setupCreationWithMockPlatform_ShouldSucceed() throws EngineException {
		MasterAndroidExtensionsInjectConfig injectConfig = new MasterAndroidExtensionsInjectConfig();
		
		injectConfig.override(new AndroidMockPlatformInjectModule());
		// No AndroidExtensionsMockPlatformInjectModule needed yet
		
		BaseInjectSetup setup = new BaseInjectSetup();
		IEngineContext engineContext = setup.create(injectConfig);
	}
	
}
