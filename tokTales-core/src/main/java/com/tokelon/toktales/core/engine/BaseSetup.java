package com.tokelon.toktales.core.engine;

import java.io.IOException;
import java.io.InputStream;

import com.tokelon.toktales.core.config.CiniMainConfig;
import com.tokelon.toktales.core.config.IConfigManager;
import com.tokelon.toktales.core.config.IMainConfig;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.storage.IStorageService;
import com.tokelon.toktales.core.game.IGameAdapter;
import com.tokelon.toktales.core.script.StorageLocationResourceFinder;
import com.tokelon.toktales.core.storage.utils.LocationImpl;
import com.tokelon.toktales.tools.config.CiniConfigStreamReader;
import com.tokelon.toktales.tools.config.ConfigDataException;
import com.tokelon.toktales.tools.config.ConfigFormatException;
import com.tokelon.toktales.tools.config.MutableCiniConfig;
import com.tokelon.toktales.tools.script.IScriptModule;
import com.tokelon.toktales.tools.script.ScriptErrorException;

public abstract class BaseSetup extends AbstractSetup {

	public static final String TAG = "BaseSetup";

	private static final String MAIN_CONFIG_FILE_NAME = "main.conf";

	private static final String SCRIPTS_PATH = "scripts";

	
	// Maybe have Toktales functions instead of variables (getEngine() etc.)
	private static final String LOAD_GLOBALS_MODULE = 
			"local LoadEngineModule = {}\n" +
	        "function LoadEngineModule.load(tEngine, tLog, tGame)\n" +
			"  Toktales = {engine = tEngine, log = tLog, game = tGame}\n" +
	        "end\n" +
			"return LoadEngineModule\n";

	
	public BaseSetup(IGameAdapter gameAdapter) {
		super(gameAdapter);
	}
	
	
	@Override
	protected void doRun(IEngineContext context) {
		
		IMainConfig mainConfig = loadConfig(context.getEngine().getStorageService(), context.getLog());
		context.getGame().getConfigManager().loadConfig(IConfigManager.MAIN_CONFIG, mainConfig);
		
		
		initScripts(context);
		
	}
	

	private IMainConfig loadConfig(IStorageService storageService, ILogger logger) {

		String mainConfigFileName = MAIN_CONFIG_FILE_NAME;

		InputStream configFileIn = storageService.tryReadAppFileOnExternal(storageService.getRootLocation(), mainConfigFileName);


		IMainConfig mainConfig = null;

		if(configFileIn != null) {
			logger.i(TAG, "Found main config file: " +mainConfigFileName);


			MutableCiniConfig ciniConfig;
			try {
				CiniConfigStreamReader reader = new CiniConfigStreamReader();
				ciniConfig = reader.readConfig(configFileIn);


				logger.i(TAG, "Checking main config...");
				mainConfig = new CiniMainConfig(ciniConfig);
				logger.i(TAG, "Main config was loaded: " +mainConfigFileName);
			}
			catch (ConfigFormatException cofoex) {
				logger.w(TAG, "Bad config file: " +cofoex.getMessage());
			} catch (ConfigDataException codaex) {
				logger.w(TAG, "Unsupported config: " +codaex.getMessage());
			}
			finally {
				try {
					configFileIn.close();
				} catch (IOException e) { /* Nothing to see here... */ }
			}
		}


		if(mainConfig == null) {
			logger.w(TAG, "No main config will be loaded.");
			mainConfig = new CiniMainConfig();
		}


		return mainConfig;
	}
	
	
	private void initScripts(IEngineContext context) {
		
		LocationImpl scriptsLocation = new LocationImpl(SCRIPTS_PATH);
		StorageLocationResourceFinder finder = new StorageLocationResourceFinder(context.getEngine().getStorageService(), scriptsLocation);
		context.getGame().getScriptManager().getResourceFinder().addResourceFinder(finder);
		
		try {
			IScriptModule loadModule = context.getGame().getScriptManager().loadModule(LOAD_GLOBALS_MODULE);
			
			loadModule.callFunction("load", context.getEngine(), context.getLog(), context.getGame());
		} catch (ScriptErrorException e) {
			context.getLog().e(TAG, "Failed to load global script objects: " +e.getMessage());
		}
	}
	

}
