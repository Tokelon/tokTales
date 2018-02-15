package com.tokelon.toktales.desktop.engine.inject;

import org.junit.Test;

import com.google.inject.Injector;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.engine.setup.BaseInjectSetup;
import com.tokelon.toktales.core.game.IGameAdapter;
import com.tokelon.toktales.test.core.engine.inject.TestInjectionHelper;
import com.tokelon.toktales.test.core.game.DummyGameAdapter;

public class TestDesktopInjection {

	public static final String[] DESKTOP_EXPECTED_BINDING_TYPES = { "IGameAdapter" };
	
	
	@Test
	public void injectorCreationWithoutExpectedBindings_ShouldFail() {
		DesktopInjectConfig injectConfig = new DesktopInjectConfig();
		
		TestInjectionHelper.assertInjectorCreationFailsWithExpectedBindings(injectConfig, DESKTOP_EXPECTED_BINDING_TYPES);
	}
	
	@Test
	public void injectorCreationWithSetupModule_ShouldSucceed() {
		DesktopInjectConfig injectConfig = new DesktopInjectConfig();
		
		injectConfig.override(new DesktopSetupInjectModule(DummyGameAdapter.class));
		
		Injector injector = injectConfig.createInjector();
	}
	
	
	@Test
	public void engineCreation_ShouldSucceed() {
		DesktopInjectConfig injectConfig = new DesktopInjectConfig();
		
		injectConfig.extend(new DesktopFakeSetupInjectModule());

		Injector injector = injectConfig.createInjector();
		IEngineContext engineContext = injector.getInstance(IEngineContext.class);
	}
	
	@Test
	public void engineCreationWithSetupModule_ShouldSucceed() {
		DesktopInjectConfig injectConfig = new DesktopInjectConfig();

		injectConfig.override(new DesktopSetupInjectModule(DummyGameAdapter.class));
		
		Injector injector = injectConfig.createInjector();
		IEngineContext engineContext = injector.getInstance(IEngineContext.class);
	}
	
	
	@Test
	public void setupCreation_ShouldSucceed() throws EngineException {
		DesktopInjectConfig injectConfig = new DesktopInjectConfig();

		injectConfig.override(new DesktopSetupInjectModule(DummyGameAdapter.class));

		BaseInjectSetup setup = new BaseInjectSetup();
		IEngineContext engineContext = setup.create(injectConfig);
	}
	
	
	private static class DesktopFakeSetupInjectModule extends AbstractInjectModule {
		@Override
		protected void configure() {
			bind(IGameAdapter.class).to(DummyGameAdapter.class);
		}
	}
	
	
}
