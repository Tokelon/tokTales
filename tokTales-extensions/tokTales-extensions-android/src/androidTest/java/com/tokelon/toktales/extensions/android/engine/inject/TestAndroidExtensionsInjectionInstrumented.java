package com.tokelon.toktales.extensions.android.engine.inject;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.inject.Injector;
import com.tokelon.toktales.android.engine.inject.AndroidSetupInjectModule;
import com.tokelon.toktales.android.test.engine.inject.AndroidMockPlatformInjectModule;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.inject.BaseSetupInjectModule;
import com.tokelon.toktales.core.engine.setup.DefaultEngineSetup;
import com.tokelon.toktales.core.test.game.DummyGameAdapter;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class TestAndroidExtensionsInjectionInstrumented {


	@Test
	public void injectorCreationWithSetupModule_ShouldSucceed() {
		Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

		MasterAndroidExtensionsInjectConfig injectConfig = new MasterAndroidExtensionsInjectConfig();

		injectConfig.extend(new BaseSetupInjectModule(DummyGameAdapter.class, new DefaultEngineSetup()), new AndroidSetupInjectModule(context));

		Injector injector = injectConfig.createInjector();
	}


	@Test
	public void setupCreationWithMockPlatform_ShouldSucceed() throws EngineException {
		MasterAndroidExtensionsInjectConfig injectConfig = new MasterAndroidExtensionsInjectConfig();

		injectConfig.override(new AndroidMockPlatformInjectModule());
		// No AndroidExtensionsMockPlatformInjectModule needed yet

		DefaultEngineSetup setup = new DefaultEngineSetup();
		IEngineContext engineContext = setup.create(injectConfig);
	}

}
