package com.tokelon.toktales.core.engine.setup.scripts;

import java.io.IOException;
import java.io.InputStream;

import com.tokelon.toktales.core.config.CiniMainConfig;
import com.tokelon.toktales.core.config.IConfigManager;
import com.tokelon.toktales.core.config.IMainConfig;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.storage.IStorageService;
import com.tokelon.toktales.tools.config.CiniConfigStreamReader;
import com.tokelon.toktales.tools.config.ConfigDataException;
import com.tokelon.toktales.tools.config.ConfigFormatException;
import com.tokelon.toktales.tools.config.MutableCiniConfig;

public class LoadMainConfigSetupScript implements ISetupScript {
	
	public static final String TAG = "LoadMainConfigSetupScript";

	
	private static final String MAIN_CONFIG_FILE_NAME = "main.conf";
	
	
	@Override
	public void run(IEngineContext context) throws EngineException {
		
		IMainConfig mainConfig = loadConfig(context.getEngine().getStorageService(), context.getLog());
		context.getGame().getConfigManager().loadConfig(IConfigManager.MAIN_CONFIG, mainConfig);
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
			logger.w(TAG, "No main config was loaded. The default will be used");
			mainConfig = new CiniMainConfig();
		}


		return mainConfig;
	}

}
