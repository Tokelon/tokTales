package com.tokelon.toktales.android.engine;

import android.content.Context;

import com.tokelon.toktales.android.app.AndroidSetup;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.IEngineLauncher;
import com.tokelon.toktales.core.engine.IEngineSetup;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.game.IGameAdapter;

public class AndroidEngineLauncher implements IEngineLauncher {

	private final Context appContext;
	
	public AndroidEngineLauncher(Context applicationContext) {
		this.appContext = applicationContext;
	}
	

	@Override
	public void launch(IGameAdapter adapter) throws EngineException {
		AndroidSetup setup = new AndroidSetup(adapter, appContext);
		launchAndSetup(setup);
	}

	
	@Override
	public void launchAndSetup(IEngineSetup setup) throws EngineException {
		IEngineContext engineContext = setup.create();
		
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
