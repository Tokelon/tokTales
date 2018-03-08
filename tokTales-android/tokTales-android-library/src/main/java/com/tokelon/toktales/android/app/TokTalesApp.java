package com.tokelon.toktales.android.app;

import com.tokelon.toktales.android.engine.AndroidEngineLauncher;
import com.tokelon.toktales.android.engine.inject.AndroidInjectConfig;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineLauncher;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.engine.inject.IHierarchicalInjectConfig;
import com.tokelon.toktales.core.game.IGameAdapter;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.util.Log;

public class TokTalesApp extends Application {
	
	public static final String TAG = "TokTalesApp";

	public static final String META_DATA_KEY_GAME_ADAPTER_CLASS = "com.tokelon.toktales.android.application.game_adapter_class";
	public static final String META_DATA_KEY_INJECT_CONFIG_CLASS = "com.tokelon.toktales.android.application.inject_config_class";
	
	
	/** Override to implement launching with a custom game adapter or setup.
	 * 
	 * @param defaultLauncher
	 * @throws EngineException 
	 */
	protected void launchEngine(IEngineLauncher defaultLauncher) throws EngineException {
		// TODO: Test and refactor log output to mention meta data
		
		Class<? extends IGameAdapter> metaGameAdapterClass = null;
		Class<? extends IHierarchicalInjectConfig> metaInjectConfigClass = null;
		try {
			ApplicationInfo ai = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);

			metaGameAdapterClass = loadClassFromApplicationInfo(IGameAdapter.class, META_DATA_KEY_GAME_ADAPTER_CLASS, ai);
			metaInjectConfigClass = loadClassFromApplicationInfo(IHierarchicalInjectConfig.class, META_DATA_KEY_INJECT_CONFIG_CLASS, ai);
		} catch (NameNotFoundException e) {
			Log.e(TAG, "Error loading meta-data: " + e.getMessage());
		}
		
		
		IEngineLauncher launcher = defaultLauncher;
		if(metaInjectConfigClass == null) {
			Log.i("App", "No custom inject config used. Override TokTalesApp.launch() to use your custom inject config");
		}
		else {
			try {
				IHierarchicalInjectConfig injectConfig = metaInjectConfigClass.newInstance();
				launcher = new AndroidEngineLauncher(injectConfig, getApplicationContext());
			} catch (InstantiationException e) {
				Log.e(TAG, "Error loading inject config: " + e.getMessage());
			} catch (IllegalAccessException e) {
				Log.e(TAG, "Error loading inject config: " + e.getMessage());
			}
		}
		
		Class<? extends IGameAdapter> gameAdapterClass = EmptyGameAdapter.class;
		if(metaGameAdapterClass == null) {
			Log.i("App", "No custom game adapter used. Override TokTalesApp.launch() to use your custom game adapter");
		}
		else {
			gameAdapterClass = metaGameAdapterClass;
		}
		
		
		launcher.launch(gameAdapterClass);
	}
	
	
	@SuppressWarnings("unchecked")
	private <T> Class<? extends T> loadClassFromApplicationInfo(Class<T> classNeeded, String metaDataKey, ApplicationInfo info) {
		Class<? extends T> result = null;
		
		Object infoValue = info.metaData.get(metaDataKey);
		if(infoValue == null) {
			Log.i(TAG, String.format("No meta-data for key %s found", metaDataKey));
		}
		else if(!(infoValue instanceof String)) {
			Log.e(TAG, String.format("Error loading meta-data for key %s: Invalid data type %s", metaDataKey, infoValue.getClass()));
		}
		else {
			String metaDataGameAdapterClass = (String) infoValue; 
			try {
				Class<?> gaClass = Class.forName(metaDataGameAdapterClass);
				if(classNeeded.isAssignableFrom(gaClass)) {
					result = (Class<T>) gaClass;
				}
				else {
					Log.e(TAG, String.format("Error loading meta-data for key %s: Provided class is not assignable from %s. Invalid type for class %s", metaDataKey, classNeeded, gaClass.getName()));
				}
			} catch (ClassNotFoundException e) {
				Log.e(TAG, String.format("Error loading meta-data for key %s: %s", metaDataKey, e.getMessage()));
			}
		}
		
		return result;
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
