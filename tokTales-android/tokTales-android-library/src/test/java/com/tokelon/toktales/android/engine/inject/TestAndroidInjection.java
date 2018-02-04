package com.tokelon.toktales.android.engine.inject;

import static org.mockito.Mockito.mock;

import org.junit.Test;

import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.test.core.engine.inject.TestInjectionHelper;
import com.tokelon.toktales.core.engine.setup.BaseInjectSetup;
import com.tokelon.toktales.core.game.IGameAdapter;

import android.content.Context;

public class TestAndroidInjection {

	public static final String[] ANDROID_EXPECTED_BINDING_TYPES = { "android.content.Context" };
	
	private static final Context mockedContext = mock(Context.class);

	
	@Test
	public void injectorCreationWithoutExpectedBindings_ShouldFail() {
		AndroidInjectConfig injectConfig = new AndroidInjectConfig();
		
		TestInjectionHelper.assertInjectorCreationFailsWithExpectedBindings(injectConfig, ANDROID_EXPECTED_BINDING_TYPES);
	}
	
	
	@Test
	public void injectorCreation_ShouldSucceed() {
		AndroidInjectConfig injectConfig = new AndroidInjectConfig();
		
		injectConfig.extend(new AndroidFakeSetupInjectModule());

		Injector injector = injectConfig.createInjector();
	}
	
	@Test
	public void injectorCreationWithSetupModule_ShouldSucceed() {
		AndroidInjectConfig injectConfig = new AndroidInjectConfig();
		
		injectConfig.override(new AndroidSetupInjectModule(mockedContext, new IGameAdapter.EmptyGameAdapter()));
		
		Injector injector = injectConfig.createInjector();
	}
	
	
	@Test(expected = ProvisionException.class)
	public void engineCreationWithStubs_ShouldFail() {
		AndroidInjectConfig injectConfig = new AndroidInjectConfig();
		injectConfig.override(new AndroidSetupInjectModule(mockedContext, new IGameAdapter.EmptyGameAdapter()));

		Injector injector = injectConfig.createInjector();
		IEngineContext engineContext = injector.getInstance(IEngineContext.class);
	}
	
	
	@Test(expected = ProvisionException.class)
	public void setupCreationWithStubs_ShouldFail() throws EngineException {
		AndroidInjectConfig injectConfig = new AndroidInjectConfig();
		injectConfig.override(new AndroidSetupInjectModule(mockedContext, new IGameAdapter.EmptyGameAdapter()));

		BaseInjectSetup setup = new BaseInjectSetup();
		IEngineContext engineContext = setup.create(injectConfig);
	}

	
	private static class AndroidFakeSetupInjectModule extends AbstractInjectModule {
		@Override
		protected void configure() {
	        bind(Context.class).toInstance(mockedContext);
		}
	}
	
	
}
