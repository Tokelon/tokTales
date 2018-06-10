package com.tokelon.toktales.extens.def.android.engine;

import static org.mockito.Mockito.mock;

import org.junit.Test;

import com.google.inject.Injector;
import com.tokelon.toktales.android.engine.inject.AndroidSetupInjectModule;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.inject.BaseSetupInjectModule;
import com.tokelon.toktales.core.engine.setup.BaseInjectSetup;
import com.tokelon.toktales.extens.def.android.engine.inject.AndroidDefExtensInjectConfig;
import com.tokelon.toktales.test.android.engine.inject.AndroidMockPlatformInjectModule;
import com.tokelon.toktales.test.core.game.DummyGameAdapter;

import android.content.Context;

public class TestAndroidDefExtensInjection {
	
	private static final Context mockedContext = mock(Context.class);

	
	@Test
	public void injectorCreationWithSetupModule_ShouldSucceed() {
		AndroidDefExtensInjectConfig injectConfig = new AndroidDefExtensInjectConfig();
		
		injectConfig.extend(new BaseSetupInjectModule(DummyGameAdapter.class), new AndroidSetupInjectModule(mockedContext));
		
		Injector injector = injectConfig.createInjector();
	}
	

	@Test
	public void setupCreationWithMockPlatform_ShouldSucceed() throws EngineException {
		AndroidDefExtensInjectConfig injectConfig = new AndroidDefExtensInjectConfig();
		
		injectConfig.override(new AndroidMockPlatformInjectModule());
		// No AndroidDefExtensMockPlatformInjectModule needed yet
		
		BaseInjectSetup setup = new BaseInjectSetup();
		IEngineContext engineContext = setup.create(injectConfig);
	}
	
}
