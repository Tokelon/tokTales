package com.tokelon.toktales.android.app;

import com.tokelon.toktales.android.engine.AndroidEngineLauncher;
import com.tokelon.toktales.android.engine.inject.MasterAndroidInjectConfig;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineLauncher;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.LoggingManager;
import com.tokelon.toktales.core.game.IGame;
import com.tokelon.toktales.core.game.IGameAdapter;
import com.tokelon.toktales.tools.core.sub.inject.config.IHierarchicalInjectConfig;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;

public class TokTalesApp extends Application {
	// TODO: Add callback onEngineLaunch(IEngineContext context) ?


	private static final ILogger logger = LoggingManager.getLogger(TokTalesApp.class);
	
	public static final String META_DATA_KEY_GAME_ADAPTER_CLASS = "com.tokelon.toktales.android.game_adapter_class";
	public static final String META_DATA_KEY_INJECT_CONFIG_CLASS = "com.tokelon.toktales.android.inject_config_class";
	
	
	/** Launches the engine. Override to customize the launch configuration.
	 * 
	 * @param defaultLauncher A default launcher you can use.
	 * @throws EngineException If an exceptions is thrown while launching the engine.
	 */
	protected void launchEngine(IEngineLauncher defaultLauncher) throws EngineException {
		logger.debug("Default engine launcher used. Checking meta-data for launch configuration...");
		
		Class<? extends IGameAdapter> metaGameAdapterClass = null;
		Class<? extends IHierarchicalInjectConfig> metaInjectConfigClass = null;
		try {
			ApplicationInfo ai = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);

			if(ai.metaData == null) {
				logger.error("Failed to get meta-data from application info: meta-data was null");
			}
			else {
				logger.debug("Looking for game adapter entry...");
				metaGameAdapterClass = loadClassFromApplicationInfo(IGameAdapter.class, META_DATA_KEY_GAME_ADAPTER_CLASS, ai);
				
				logger.debug("Looking for inject config entry...");
				metaInjectConfigClass = loadClassFromApplicationInfo(IHierarchicalInjectConfig.class, META_DATA_KEY_INJECT_CONFIG_CLASS, ai);
			}
		} catch (NameNotFoundException e) {
			logger.error("Failed to get meta-data from application info:", e);
		}
		
		
		IEngineLauncher launcher = defaultLauncher;
		if(metaInjectConfigClass == null) {
			logger.info("The default inject config will be used. To change this, add meta-data configuration to your manifest or override TokTalesApp.launchEngine().");
		}
		else {
			try {
				logger.debug("Instantiating inject config of type {}", metaInjectConfigClass);
				IHierarchicalInjectConfig injectConfig = metaInjectConfigClass.newInstance();
				
				launcher = new AndroidEngineLauncher(injectConfig, getApplicationContext());
				logger.info("Engine launcher will use inject config of type: {}", metaInjectConfigClass);
			} catch (InstantiationException e) {
				logger.error("Failed to create inject config. Make sure you provide a public no-args constructor. Error:", e);
			} catch (IllegalAccessException e) {
				logger.error("Failed to access inject config constructor. Make sure your class is public and has a public no-args constructor. Error:", e);
			}
		}
		
		Class<? extends IGameAdapter> gameAdapterClass = EmptyGameAdapter.class;
		if(metaGameAdapterClass == null) {
			logger.info("The default game adapter will be used. To change this, add meta-data configuration to your manifest or override TokTalesApp.launchEngine().");
		}
		else {
			logger.info("Engine launcher will use game adapter of type: {}", metaGameAdapterClass);
			gameAdapterClass = metaGameAdapterClass;
		}
		
		
		logger.info("Launching engine...");
		launcher.launch(gameAdapterClass);
	}
	
	
	/** Loads a class of given type for a given key from the application meta-data. 
	 * 
	 * @param typeNeeded the type the class should be assignable from
	 * @param metaDataKey the name of the meta-data entry
	 * @param info the application info
	 * @return The loaded class, or null if there was no entry or an error occurred.
	 */
	@SuppressWarnings("unchecked")
	protected <T> Class<? extends T> loadClassFromApplicationInfo(Class<T> typeNeeded, String metaDataKey, ApplicationInfo info) {
		Class<? extends T> result = null;
		
		Object infoValue = info.metaData.get(metaDataKey);
		if(infoValue == null) {
			logger.debug("No meta-data for key {} found", metaDataKey);
		}
		else if(!(infoValue instanceof String)) {
			logger.error("Invalid datatype for key {}: {}. Expected a string.", metaDataKey, infoValue.getClass());
		}
		else {
			String infoClassName = (String) infoValue;
			try {
				Class<?> infoClass = Class.forName(infoClassName);
				if(typeNeeded.isAssignableFrom(infoClass)) {
					result = (Class<T>) infoClass;
				}
				else {
					logger.error("Invalid class for key {}: {} Provided class must be assignable from {}.", metaDataKey, infoClass.getName(), typeNeeded);
				}
			} catch (ClassNotFoundException e) {
				logger.error("Class not found for key {}:", metaDataKey, e);
			}
		}
		
		return result;
	}
	
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		AndroidEngineLauncher androidLauncher = new AndroidEngineLauncher(new MasterAndroidInjectConfig(), getApplicationContext());
		try {
			launchEngine(androidLauncher);
		} catch (EngineException e) {
			// TODO: What to do here?
			// Set an error in TokTales and maybe show dialog and then exit?
			logger.error("Error while launching engine:", e);
		}
	}
	
	
	@Override
	public void onTerminate() {
		super.onTerminate();
		
		/* Not actually called in productive environment
		 * Apparently this will never be called
		 * TODO: Implement termination inside an activity (the main activity)
		 */
		
		IGame game = TokTales.getGame(); // TODO: Replace with callback onEngineLaunch() ?
		if(game != null) {
			game.getGameControl().pauseGame();
			game.getGameControl().stopGame();
			game.getGameControl().destroyGame();	
		}
	}

	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		
		logger.debug("App configuration has changed");
	}
	
	
	// Default
	private static class EmptyGameAdapter implements IGameAdapter { }
	
}
