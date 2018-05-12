package com.tokelon.toktales.core.engine.setup;

import com.google.inject.Injector;
import com.google.inject.Stage;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.inject.IHierarchicalInjectConfig;
import com.tokelon.toktales.core.engine.inject.IInjectConfig;
import com.tokelon.toktales.core.game.IGameAdapter;

public abstract class AbstractInjectSetup implements IEngineSetup {

	
	// Default setup mode is production
	private SetupMode setupMode = SetupMode.PRODUCTION;
	

	/** Implement your custom setup logic here.
	 * <p>
	 * Called in {@link #run(IEngineContext)}.
	 * 
	 * @param context The engine context created.
	 * @throws EngineException If there was an error while running the setup.
	 */
	protected abstract void doRun(IEngineContext context) throws EngineException;

	
	@Override
	public IEngineContext create(IHierarchicalInjectConfig injectConfig) throws EngineException {
		System.out.println("Engine creation started");
		System.out.println("Engine setup is running in mode " + setupMode);
		
		Injector injector = createInjector(injectConfig, setupMode);
		IEngineContext engineContext = createEngineContext(injector);
		
		return engineContext;
	}

	
	@Override
	public void run(IEngineContext context) throws EngineException {
		doRun(context);
		
		createGameAdapter(context.getInjector());
	}

	
	@Override
	public void setSetupMode(SetupMode mode) {
		this.setupMode = mode;
	}
	
	@Override
	public SetupMode getSetupMode() {
		return setupMode;
	}


	protected Injector createInjector(IInjectConfig injectConfig, SetupMode mode) throws EngineException {
		Stage stage = mode == SetupMode.DEVELOPMENT ? Stage.DEVELOPMENT : Stage.PRODUCTION;
		System.out.println("Determined injector stage as " + stage);
		
		System.out.print("Creating engine injector..... ");
		long before = System.currentTimeMillis();

		Injector injector = injectConfig.createInjector(stage);
		
		System.out.printf("Injector creation took %d ms", (System.currentTimeMillis() - before));
		System.out.println();
		return injector;
	}

	protected IEngineContext createEngineContext(Injector injector) {
		System.out.println("Creating engine context... ");
		long before = System.currentTimeMillis();
		
		IEngineContext engineContext = injector.getInstance(IEngineContext.class);
		
		System.out.printf("...engine context creation took %d ms", (System.currentTimeMillis() - before));
		System.out.println();
		return engineContext;
	}
	
	protected IGameAdapter createGameAdapter(Injector injector) {
		System.out.println("Creating game adapter... ");
		long before = System.currentTimeMillis();

		IGameAdapter gameAdapter = injector.getInstance(IGameAdapter.class);
		
		System.out.printf("...game adapter creation took %d ms", (System.currentTimeMillis() - before));
		System.out.println();
		return gameAdapter;
	}
	
}
