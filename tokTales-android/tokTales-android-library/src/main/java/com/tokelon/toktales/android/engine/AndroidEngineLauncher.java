package com.tokelon.toktales.android.engine;

import com.tokelon.toktales.android.engine.inject.AndroidInjectConfig;
import com.tokelon.toktales.android.engine.inject.AndroidSetupInjectModule;
import com.tokelon.toktales.core.engine.BaseEngineLauncher;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.inject.IHierarchicalInjectConfig;
import com.tokelon.toktales.core.engine.setup.IEngineSetup;
import com.tokelon.toktales.core.game.IGameAdapter;

import android.content.Context;

public class AndroidEngineLauncher extends BaseEngineLauncher {

	
	private final Context appContext;
	
	/** Constructor with an inject config and an application context.
	 * 
	 * @param injectConfig
	 * @param applicationContext
	 * 
	 * @see AndroidInjectConfig
	 */
	public AndroidEngineLauncher(IHierarchicalInjectConfig injectConfig, Context applicationContext) {
		super(injectConfig);
		
        this.appContext = applicationContext;
    }

	
	
	/** {@inheritDoc} */
	@Override
	protected IEngineContext createEngine(Class<? extends IGameAdapter> adapter, IEngineSetup setup) throws EngineException {
		IHierarchicalInjectConfig injectConfig = getInjectConfig();
		
		// Inject game adapter and app context
		injectConfig.extend(new AndroidSetupInjectModule(appContext));

		return super.createEngine(adapter, setup);
	}
	
	
	/** Creates the game.
	 * Does not run a loop since Android works differently.
	 * <p>
	 * This implementation does not call super.
	 * 
	 * @param engineContext
	 * @throws EngineException
	 */
	@Override
	protected void startEngine(IEngineContext engineContext) throws EngineException {
		// Do NOT call super here
		
		// calls onCreate on adapter
		engineContext.getGame().getGameControl().createGame();
		

		// The Android lifecycle is a bit more dynamic, so we can not do this here
		// These will have to be called in an activity or something like GameIntegration
		//engineContext.getGame().getGameControl().startGame();
		//engineContext.getGame().getGameControl().resumeGame();
		
		
		// TODO: Implement calling .destroy() somewhere
	}
	
}
