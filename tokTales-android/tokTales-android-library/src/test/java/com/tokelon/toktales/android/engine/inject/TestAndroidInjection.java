package com.tokelon.toktales.android.engine.inject;

import static org.mockito.Mockito.mock;

import org.junit.Test;

import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.inject.BaseSetupInjectModule;
import com.tokelon.toktales.core.engine.setup.BaseInjectSetup;
import com.tokelon.toktales.test.android.engine.inject.AndroidMockPlatformInjectModule;
import com.tokelon.toktales.test.core.engine.inject.InjectionTestHelper;
import com.tokelon.toktales.test.core.game.DummyGameAdapter;

import android.content.Context;

public class TestAndroidInjection {
	// TODO: Fix this not working in manual Eclipse run

	public static final String[] ANDROID_EXPECTED_BINDING_TYPES = { "android.content.Context", "IGameAdapter" };
	
	private static final Context mockedContext = mock(Context.class);

	
	@Test
	public void injectorCreationWithoutExpectedBindings_ShouldFail() {
		AndroidInjectConfig injectConfig = new AndroidInjectConfig();
		
		InjectionTestHelper.assertInjectorCreationFailsWithExpectedBindings(injectConfig, ANDROID_EXPECTED_BINDING_TYPES, new String[0][0]);
	}
	
	@Test
	public void injectorCreationWithSetupModule_ShouldSucceed() {
		AndroidInjectConfig injectConfig = new AndroidInjectConfig();
		
		injectConfig.extend(new BaseSetupInjectModule(DummyGameAdapter.class), new AndroidSetupInjectModule(mockedContext));
		
		Injector injector = injectConfig.createInjector();
	}
	
	
	@Test(expected = ProvisionException.class)
	public void engineCreationWithStubs_ShouldFail() {
		AndroidInjectConfig injectConfig = new AndroidInjectConfig();
		injectConfig.extend(new BaseSetupInjectModule(DummyGameAdapter.class), new AndroidSetupInjectModule(mockedContext));

		Injector injector = injectConfig.createInjector();
		IEngineContext engineContext = injector.getInstance(IEngineContext.class);
	}
	
	@Test(expected = ProvisionException.class)
	public void setupCreationWithStubs_ShouldFail() throws EngineException {
		AndroidInjectConfig injectConfig = new AndroidInjectConfig();
		injectConfig.extend(new BaseSetupInjectModule(DummyGameAdapter.class), new AndroidSetupInjectModule(mockedContext));

		BaseInjectSetup setup = new BaseInjectSetup();
		IEngineContext engineContext = setup.create(injectConfig);
	}
	

	@Test
	public void setupCreationWithMockPlatform_ShouldSucceed() throws EngineException {
		AndroidInjectConfig injectConfig = new AndroidInjectConfig();
		
		injectConfig.override(new AndroidMockPlatformInjectModule());
		
		BaseInjectSetup setup = new BaseInjectSetup();
		IEngineContext engineContext = setup.create(injectConfig);
	}
	
}
