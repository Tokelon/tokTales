package com.tokelon.toktales.android.engine.inject;

import static org.mockito.Mockito.mock;

import org.junit.Test;

import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import com.tokelon.toktales.android.test.engine.inject.AndroidMockPlatformInjectModule;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.inject.BaseSetupInjectModule;
import com.tokelon.toktales.core.engine.setup.BaseInjectSetup;
import com.tokelon.toktales.core.game.IGameAdapter;
import com.tokelon.toktales.core.test.engine.inject.InjectionTestHelper;
import com.tokelon.toktales.core.test.game.DummyGameAdapter;

import android.content.Context;

public class TestAndroidInjection {
	// TODO: Fix this not working in manual Eclipse run

	public static final Class<?>[] ANDROID_EXPECTED_BINDING_TYPES =
	{
			Context.class,
			IGameAdapter.class,
	};
	
	private static final Context mockedContext = mock(Context.class);

	
	@Test
	public void injectorCreationWithoutExpectedBindings_ShouldFail() {
		MasterAndroidInjectConfig injectConfig = new MasterAndroidInjectConfig();
		
		InjectionTestHelper.assertInjectorCreationFailsWithExpectedBindings(injectConfig, ANDROID_EXPECTED_BINDING_TYPES, new Class<?>[0][0]);
	}
	
	@Test
	public void injectorCreationWithSetupModule_ShouldSucceed() {
		MasterAndroidInjectConfig injectConfig = new MasterAndroidInjectConfig();
		
		injectConfig.extend(new BaseSetupInjectModule(DummyGameAdapter.class), new AndroidSetupInjectModule(mockedContext));
		
		Injector injector = injectConfig.createInjector();
	}
	
	
	@Test(expected = ProvisionException.class)
	public void engineCreationWithStubs_ShouldFail() {
		MasterAndroidInjectConfig injectConfig = new MasterAndroidInjectConfig();
		injectConfig.extend(new BaseSetupInjectModule(DummyGameAdapter.class), new AndroidSetupInjectModule(mockedContext));

		Injector injector = injectConfig.createInjector();
		IEngineContext engineContext = injector.getInstance(IEngineContext.class);
	}
	
	@Test(expected = ProvisionException.class)
	public void setupCreationWithStubs_ShouldFail() throws EngineException {
		MasterAndroidInjectConfig injectConfig = new MasterAndroidInjectConfig();
		injectConfig.extend(new BaseSetupInjectModule(DummyGameAdapter.class), new AndroidSetupInjectModule(mockedContext));

		BaseInjectSetup setup = new BaseInjectSetup();
		IEngineContext engineContext = setup.create(injectConfig);
	}
	

	@Test
	public void setupCreationWithMockPlatform_ShouldSucceed() throws EngineException {
		MasterAndroidInjectConfig injectConfig = new MasterAndroidInjectConfig();
		
		injectConfig.override(new AndroidMockPlatformInjectModule());
		
		BaseInjectSetup setup = new BaseInjectSetup();
		IEngineContext engineContext = setup.create(injectConfig);
	}
	
}
