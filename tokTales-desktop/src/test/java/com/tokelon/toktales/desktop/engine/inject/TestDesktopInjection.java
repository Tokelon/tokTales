package com.tokelon.toktales.desktop.engine.inject;

import org.junit.Test;

import com.google.inject.Injector;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.setup.BaseInjectSetup;
import com.tokelon.toktales.core.game.IGameAdapter;

public class TestDesktopInjection {

	
	@Test
	public void injectorCreation_ShouldSucceed() {
		DesktopInjectConfig injectConfig = new DesktopInjectConfig();
		Injector injector = injectConfig.createInjector();
	}
	
	@Test
	public void injectorCreationWithSetupModule_ShouldSucceed() {
		DesktopInjectConfig injectConfig = new DesktopInjectConfig();
		
		IGameAdapter adapter = new IGameAdapter.EmptyGameAdapter();
		injectConfig.override(new DesktopSetupInjectModule(adapter));
		
		Injector injector = injectConfig.createInjector();
	}
	
	
	@Test
	public void engineCreation_ShouldSucceed() {
		DesktopInjectConfig injectConfig = new DesktopInjectConfig();
		Injector injector = injectConfig.createInjector();

		IEngineContext engineContext = injector.getInstance(IEngineContext.class);
	}
	
	@Test
	public void engineCreationWithSetupModule_ShouldSucceed() {
		DesktopInjectConfig injectConfig = new DesktopInjectConfig();

		IGameAdapter adapter = new IGameAdapter.EmptyGameAdapter();
		injectConfig.override(new DesktopSetupInjectModule(adapter));
		
		Injector injector = injectConfig.createInjector();
		IEngineContext engineContext = injector.getInstance(IEngineContext.class);
	}
	
	
	@Test
	public void setupCreation_ShouldSucceed() throws EngineException {
		DesktopInjectConfig injectConfig = new DesktopInjectConfig();

		IGameAdapter adapter = new IGameAdapter.EmptyGameAdapter();
		injectConfig.override(new DesktopSetupInjectModule(adapter));

		BaseInjectSetup setup = new BaseInjectSetup();
		IEngineContext engineContext = setup.create(injectConfig);
	}
	
	
}
