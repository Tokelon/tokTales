package com.tokelon.toktales.android.engine;

import com.tokelon.toktales.android.app.AndroidSetup;
import com.tokelon.toktales.core.engine.CoreInjectModule;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.IEngineLauncher;
import com.tokelon.toktales.core.engine.IEngineSetup;
import com.tokelon.toktales.core.engine.IInjectConfig;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.game.IGameAdapter;

import android.content.Context;

public class AndroidEngineLauncher implements IEngineLauncher {

	private final Context appContext;
	private final IInjectConfig injectConfig;

	
	/** Ctor with the application context and a default inject config.
	 * <p>
	 * The default config includes {@link CoreInjectModule} and {@link AndroidInjectModule}.
	 * 
	 * @param applicationContext
	 */
	public AndroidEngineLauncher(Context applicationContext) { // TODO: Inject the context
		this(applicationContext, new AndroidInjectConfig());
	}
	
	/** Ctor with the application context and an inject config.
	 * 
	 * @param applicationContext
	 * @param injectConfig
	 */
	public AndroidEngineLauncher(Context applicationContext, IInjectConfig injectConfig) {
        this.appContext = applicationContext;
        this.injectConfig = injectConfig;
    }
	
	

	@Override
	public void launch(IGameAdapter adapter) throws EngineException {
		AndroidSetup setup = new AndroidSetup(adapter, appContext);
		launchAndSetup(setup);
	}

	
	@Override
	public void launchAndSetup(IEngineSetup setup) throws EngineException {
	    // Create engine context
		IEngineContext engineContext = setup.create(injectConfig);
		
		// Load into TokTales
		TokTales.load(engineContext);

        
		// Run after setting up TokTales
		setup.run(engineContext);
		
		
		// calls onCreate on adapter
		engineContext.getGame().getGameControl().createGame();

		
		// Called in GameProcess when process is started
		engineContext.getGame().getGameControl().startGame();
		engineContext.getGame().getGameControl().resumeGame();
		
		
		// TODO: Implement calling .destroy() somewhere
	}
	
	
}
