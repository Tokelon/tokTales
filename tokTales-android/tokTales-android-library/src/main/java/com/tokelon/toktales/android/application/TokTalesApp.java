package com.tokelon.toktales.android.application;

import com.tokelon.toktales.android.engine.AndroidLauncherFactory;
import com.tokelon.toktales.android.engine.IAndroidEngineLauncher;
import com.tokelon.toktales.android.engine.inject.MasterAndroidInjectConfig;
import com.tokelon.toktales.android.engine.setup.AndroidEngineSetup;
import com.tokelon.toktales.core.application.IEngineApplication;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineLauncher;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILoggerFactory;
import com.tokelon.toktales.core.engine.log.LoggingManager;
import com.tokelon.toktales.core.engine.setup.IEngineSetup;
import com.tokelon.toktales.core.game.EmptyGameAdapter;
import com.tokelon.toktales.core.game.IGameAdapter;
import com.tokelon.toktales.tools.core.sub.inject.config.IHierarchicalInjectConfig;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;

public class TokTalesApp extends Application implements IEngineApplication {
	// Add callback onEngineLaunch(IEngineContext context)?


	public static final String META_DATA_KEY_GAME_ADAPTER_CLASS = "com.tokelon.toktales.android.game_adapter_class";
	public static final String META_DATA_KEY_INJECT_CONFIG_CLASS = "com.tokelon.toktales.android.inject_config_class";


	private final ILogger logger;

	/** Default constructor.
	 * <p>
	 * A default constructor is required because it will be used to instantiate this class.
	 */
	public TokTalesApp() {
		this(LoggingManager.getLoggerFactory());
	}

	/** Constructor with a logger factory.
	 *
	 * @param loggerFactory
	 */
	protected TokTalesApp(ILoggerFactory loggerFactory) {
		this.logger = loggerFactory.getLogger(getClass());
	}


	/**
	 * @return The logger for this class.
	 */
	protected ILogger getLogger() {
		return logger;
	}


	@Override
	public void onCreate() {
		super.onCreate();

		try {
			run(new String[0]);
		}
		catch(EngineException engineException) {
			// TODO: What to do here?
			// Set an error in TokTales and maybe show dialog and then exit?
		}
	}


	@Override
	public void run(String[] args) throws EngineException {
		IAndroidEngineLauncher launcher = createDefaultEngineLauncher();
		try {
			launchEngine(launcher);
		}
		catch(EngineException engineException) {
			getLogger().error("Error while launching engine: ", engineException);
			throw engineException;
		}
	}

	@Override
	public void launchEngine(IEngineLauncher defaultLauncher) throws EngineException {
		getLogger().debug("Default engine launcher used. Checking meta-data for launch configuration...");

		Class<? extends IGameAdapter> metaGameAdapterClass = null;
		Class<? extends IHierarchicalInjectConfig> metaInjectConfigClass = null;
		try {
			ApplicationInfo ai = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);

			if(ai.metaData == null) {
				getLogger().error("Failed to get meta-data from application info: meta-data was null");
			}
			else {
				getLogger().debug("Looking for game adapter entry...");
				metaGameAdapterClass = loadClassFromApplicationInfo(IGameAdapter.class, META_DATA_KEY_GAME_ADAPTER_CLASS, ai);

				getLogger().debug("Looking for inject config entry...");
				metaInjectConfigClass = loadClassFromApplicationInfo(IHierarchicalInjectConfig.class, META_DATA_KEY_INJECT_CONFIG_CLASS, ai);
			}
		}
		catch(NameNotFoundException nameNotFoundException) {
			getLogger().error("Failed to get meta-data from application info:", nameNotFoundException);
		}


		IEngineLauncher launcher = defaultLauncher;
		if(metaInjectConfigClass == null) {
			getLogger().info("The default inject config will be used. To change this, add meta-data configuration to your manifest or override TokTalesApp.launchEngine().");
		}
		else {
			try {
				getLogger().debug("Instantiating inject config of type {}", metaInjectConfigClass);
				IHierarchicalInjectConfig injectConfig = metaInjectConfigClass.newInstance();

				launcher = new AndroidLauncherFactory().createDefaultBuilder(getApplicationContext()).withInjectConfig(injectConfig).build();
				getLogger().info("Engine launcher will use inject config of type: {}", metaInjectConfigClass);
			}
			catch(InstantiationException instantiationException) {
				getLogger().error("Failed to create inject config. Make sure you provide a public no-args constructor. Error:", instantiationException);
			}
			catch(IllegalAccessException illegalAccessException) {
				getLogger().error("Failed to access inject config constructor. Make sure your class is public and has a public no-args constructor. Error:", illegalAccessException);
			}
		}

		Class<? extends IGameAdapter> gameAdapterClass = getDefaultGameAdapter();
		if(metaGameAdapterClass == null) {
			getLogger().info("The default game adapter will be used. To change this, add meta-data configuration to your manifest or override TokTalesApp.launchEngine().");
		}
		else {
			getLogger().info("Engine launcher will use game adapter of type: {}", metaGameAdapterClass);
			gameAdapterClass = metaGameAdapterClass;
		}


		getLogger().info("Launching engine...");
		launcher.launchWithSetup(gameAdapterClass, createDefaultEngineSetup());
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
			getLogger().debug("No meta-data for key {} found", metaDataKey);
		}
		else if(!(infoValue instanceof String)) {
			getLogger().error("Invalid datatype for key {}: {}. Expected a string.", metaDataKey, infoValue.getClass());
		}
		else {
			String infoClassName = (String) infoValue;
			try {
				Class<?> infoClass = Class.forName(infoClassName);
				if(typeNeeded.isAssignableFrom(infoClass)) {
					result = (Class<T>) infoClass;
				}
				else {
					getLogger().error("Invalid class for key {}: {} Provided class must be assignable from {}.", metaDataKey, infoClass.getName(), typeNeeded);
				}
			}
			catch(ClassNotFoundException classNotFoundException) {
				getLogger().error("Class not found for key {}:", metaDataKey, classNotFoundException);
			}
		}

		return result;
	}


	@Override
	public IAndroidEngineLauncher createDefaultEngineLauncher() {
		return new AndroidLauncherFactory()
				.createDefaultBuilder(getApplicationContext())
				.withInjectConfig(createDefaultInjectConfig())
				.build();
	}

	@Override
	public IHierarchicalInjectConfig createDefaultInjectConfig() {
		return new MasterAndroidInjectConfig();
	}

	@Override
	public IEngineSetup createDefaultEngineSetup() {
		return new AndroidEngineSetup();
	}

	@Override
	public Class<? extends IGameAdapter> getDefaultGameAdapter() {
		return EmptyGameAdapter.class;
	}


	@Override
	public void onTerminate() {
		/* Not actually called in productive environment
		 * Apparently this will never be called
		 */
		super.onTerminate();
	}


	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		getLogger().debug("App configuration has changed");
	}

}
