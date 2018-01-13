package com.tokelon.toktales.core.engine.setup;

import com.google.inject.Injector;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.inject.IHierarchicalInjectConfig;
import com.tokelon.toktales.core.engine.inject.IInjectConfig;

public abstract class AbstractInjectSetup implements IEngineSetup {


	@Override
	public IEngineContext create(IHierarchicalInjectConfig injectConfig) throws EngineException {
		Injector injector = createInjector(injectConfig); 
		return injector.getInstance(IEngineContext.class);
	}

	
	@Override
	public void run(IEngineContext context) throws EngineException {
		doRun(context);
	}


	protected abstract void doRun(IEngineContext context) throws EngineException;


	protected Injector createInjector(IInjectConfig injectConfig) throws EngineException {
		long before = System.currentTimeMillis();

		Injector injector = injectConfig.createInjector();
		
		System.out.println("Injector creation time (ms): " + (System.currentTimeMillis() - before));
		return injector;
	}


}
