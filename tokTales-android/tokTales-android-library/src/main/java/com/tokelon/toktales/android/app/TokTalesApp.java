package com.tokelon.toktales.android.app;

import com.tokelon.toktales.android.engine.AndroidEngineLauncher;
import com.tokelon.toktales.android.engine.inject.AndroidInjectConfig;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineLauncher;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.game.IGameAdapter;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

public class TokTalesApp extends Application {

	
	/** Override to implement launching with a custom game adapter or setup.
	 * 
	 * @param defaultLauncher
	 * @throws EngineException 
	 */
	protected void launchEngine(IEngineLauncher defaultLauncher) throws EngineException {
		Log.i("App", "No custom game adapter used. Override TokTalesApp.launch() to use your custom game adapter");
		
		// Default implementation
		defaultLauncher.launch(EmptyGameAdapter.class); // TODO: Load classname from manifest if possible
	}
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		AndroidEngineLauncher androidLauncher = new AndroidEngineLauncher(new AndroidInjectConfig(), getApplicationContext());
		try {
			launchEngine(androidLauncher);
		} catch (EngineException e) {
			// TODO: What to do here?
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void onTerminate() {
		super.onTerminate();
		
		// Not actually called in productive environment
		
		// Apparently this will never be called
		// TODO: Implement termination inside an activity (the main activity)
		
		TokTales.getGame().getGameControl().pauseGame();
		TokTales.getGame().getGameControl().stopGame();
		TokTales.getGame().getGameControl().destroyGame();
	}

	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		
		TokTales.getLog().i("TokelonApp", "Info: configuration has changed");
	}
	
	
	// Default
	private class EmptyGameAdapter implements IGameAdapter { }
	
	
}
