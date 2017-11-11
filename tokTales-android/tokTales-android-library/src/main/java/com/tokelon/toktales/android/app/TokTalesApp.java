package com.tokelon.toktales.android.app;

import android.app.Application;
import android.content.res.Configuration;

import com.tokelon.toktales.android.engine.AndroidEngineLauncher;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineLauncher;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.game.IGameAdapter.EmptyGameAdapter;

public class TokTalesApp extends Application {

	
	/** Override this to implement custom launching.
	 * 
	 * @param launcher
	 * @throws EngineException 
	 */
	protected void launch(IEngineLauncher defaultLauncher) throws EngineException {
		// Default implementation
		defaultLauncher.launch(new EmptyGameAdapter());
	}
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		AndroidEngineLauncher androidLauncher = new AndroidEngineLauncher(getApplicationContext());
		
		try {
			launch(androidLauncher);
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
	
	
}
