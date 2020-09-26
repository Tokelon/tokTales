package com.tokelon.toktales.android.engine.inject;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import com.tokelon.toktales.android.engine.inject.AndroidSetupInjectModule;
import com.tokelon.toktales.android.engine.inject.MasterAndroidInjectConfig;
import com.tokelon.toktales.android.test.engine.inject.AndroidMockPlatformInjectModule;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.inject.BaseSetupInjectModule;
import com.tokelon.toktales.core.engine.setup.DefaultEngineSetup;
import com.tokelon.toktales.core.game.IGameAdapter;
import com.tokelon.toktales.core.test.engine.inject.InjectionTestHelper;
import com.tokelon.toktales.core.test.game.DummyGameAdapter;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class TestAndroidInjectionInstrumented {


	@Test
	public void engineCreationWithWithInstrumentedContext_ShouldSucceed() {
		Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

		MasterAndroidInjectConfig injectConfig = new MasterAndroidInjectConfig();

		injectConfig.extend(new BaseSetupInjectModule(DummyGameAdapter.class, new DefaultEngineSetup()), new AndroidSetupInjectModule(context));

		Injector injector = injectConfig.createInjector();
		IEngineContext engineContext = injector.getInstance(IEngineContext.class);
	}

	@Test
	public void setupCreationWithInstrumentedContext_ShouldSucceed() throws EngineException {
		Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

		MasterAndroidInjectConfig injectConfig = new MasterAndroidInjectConfig();

		DefaultEngineSetup setup = new DefaultEngineSetup();
		injectConfig.extend(new BaseSetupInjectModule(DummyGameAdapter.class, setup), new AndroidSetupInjectModule(context));

		IEngineContext engineContext = setup.create(injectConfig);
	}


	@Test
	public void setupCreationWithMockPlatform_ShouldSucceed() throws EngineException {
		MasterAndroidInjectConfig injectConfig = new MasterAndroidInjectConfig();

		injectConfig.override(new AndroidMockPlatformInjectModule());

		DefaultEngineSetup setup = new DefaultEngineSetup();
		IEngineContext engineContext = setup.create(injectConfig);
	}

}
