package com.tokelon.toktales.android.engine;

import com.tokelon.toktales.android.engine.inject.AndroidInjectConfig;
import com.tokelon.toktales.android.engine.inject.AndroidSetupInjectModule;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.IEngineLauncher;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.engine.inject.IHierarchicalInjectConfig;
import com.tokelon.toktales.core.engine.setup.BaseInjectSetup;
import com.tokelon.toktales.core.engine.setup.IEngineSetup;
import com.tokelon.toktales.core.game.IGameAdapter;

import android.content.Context;

public class AndroidEngineLauncher implements IEngineLauncher {

	
	private final IHierarchicalInjectConfig injectConfig;
	private final Context appContext;
	
	/** Ctor with an inject config and an application context.
	 * 
	 * @param injectConfig
	 * @param applicationContext
	 * 
	 * @see AndroidInjectConfig
	 */
	public AndroidEngineLauncher(IHierarchicalInjectConfig injectConfig, Context applicationContext) {
        this.appContext = applicationContext;
        this.injectConfig = injectConfig;
    }

	

	@Override
	public void launch(Class<? extends IGameAdapter> adapter) throws EngineException {
		BaseInjectSetup setup = new BaseInjectSetup();
		launchWithSetup(adapter, setup);
	}

	
	@Override
	public void launchWithSetup(Class<? extends IGameAdapter> adapter, IEngineSetup setup) throws EngineException {
		// Inject game adapter and context
		injectConfig.override(new AndroidSetupInjectModule(adapter, appContext));

	    // Create engine context
		IEngineContext engineContext = setup.create(injectConfig);
		
		// Load into TokTales
		TokTales.load(engineContext);

        
		// Run after setting up TokTales
		setup.run(engineContext);
		
		
		// calls onCreate on adapter
		engineContext.getGame().getGameControl().createGame();

		
		engineContext.getGame().getGameControl().startGame();
		engineContext.getGame().getGameControl().resumeGame();
		
		
		// TODO: Implement calling .destroy() somewhere
	}
	
}
