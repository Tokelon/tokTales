package com.tokelon.toktales.core.engine.setup;

import com.google.inject.Injector;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.inject.IHierarchicalInjectConfig;
import com.tokelon.toktales.core.engine.inject.IInjectConfig;
import com.tokelon.toktales.core.game.IGameAdapter;

public abstract class AbstractInjectSetup implements IEngineSetup {


	@Override
	public IEngineContext create(IHierarchicalInjectConfig injectConfig) throws EngineException {
		Injector injector = createInjector(injectConfig); 
		IEngineContext engineContext = createEngineContext(injector);
		
		return engineContext;
	}

	
	@Override
	public void run(IEngineContext context) throws EngineException {
		doRun(context);
		
		createGameAdapter(context.getInjector());
	}

	protected abstract void doRun(IEngineContext context) throws EngineException;


	protected Injector createInjector(IInjectConfig injectConfig) throws EngineException {
		System.out.println("Creating engine injector.....");
		long before = System.currentTimeMillis();

		Injector injector = injectConfig.createInjector();
		
		System.out.printf("Injector creation took %d ms", (System.currentTimeMillis() - before));
		System.out.println();
		return injector;
	}

	protected IEngineContext createEngineContext(Injector injector) {
		System.out.println("Creating engine context...");
		long before = System.currentTimeMillis();
		
		IEngineContext engineContext = injector.getInstance(IEngineContext.class);
		
		System.out.printf("Engine context creation took %d ms", (System.currentTimeMillis() - before));
		System.out.println();
		return engineContext;
	}
	
	protected IGameAdapter createGameAdapter(Injector injector) {
		System.out.println("Creating game adapter...");
		long before = System.currentTimeMillis();

		IGameAdapter gameAdapter = injector.getInstance(IGameAdapter.class);
		
		System.out.printf("Game adapter creation took %d ms", (System.currentTimeMillis() - before));
		System.out.println();
		return gameAdapter;
	}
	
}
