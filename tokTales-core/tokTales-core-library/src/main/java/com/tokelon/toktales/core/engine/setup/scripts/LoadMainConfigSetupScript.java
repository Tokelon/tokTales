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
import com.tokelon.toktales.tools.core.config.CiniConfigStreamReader;
import com.tokelon.toktales.tools.core.config.ConfigDataException;
import com.tokelon.toktales.tools.core.config.ConfigFormatException;
import com.tokelon.toktales.tools.core.config.MutableCiniConfig;

public class LoadMainConfigSetupScript implements ISetupScript {


	public static final String MAIN_CONFIG_FILE_NAME = "main.conf";
	
	
	@Override
	public void run(IEngineContext context) throws EngineException {
		IMainConfig mainConfig = loadConfig(context.getEngine().getStorageService(), context.getLogging().getLogger(getClass()), MAIN_CONFIG_FILE_NAME);
		context.getGame().getConfigManager().loadConfig(IConfigManager.MAIN_CONFIG, mainConfig);
	}
	

	private IMainConfig loadConfig(IStorageService storageService, ILogger logger, String mainConfigFileName) {
		InputStream configFileIn = storageService.tryReadAppFileOnExternal(storageService.getRootLocation(), mainConfigFileName);


		IMainConfig mainConfig = null;

		if(configFileIn != null) {
			logger.info("Found main config file: {}", mainConfigFileName);


			MutableCiniConfig ciniConfig;
			try {
				CiniConfigStreamReader reader = new CiniConfigStreamReader();
				ciniConfig = reader.readConfig(configFileIn);


				logger.info("Checking main config...");
				mainConfig = new CiniMainConfig(ciniConfig);
				logger.info("Main config was loaded: {}", mainConfigFileName);
			}
			catch (ConfigFormatException cofoex) {
				logger.warn("Bad config file:", cofoex);
			} catch (ConfigDataException codaex) {
				logger.warn("Unsupported config:", codaex);
			}
			finally {
				try {
					configFileIn.close();
				} catch (IOException e) {
					logger.warn("close() threw exception", e);
				}
			}
		}


		if(mainConfig == null) {
			logger.warn("No main config was loaded. The default will be used");
			mainConfig = new CiniMainConfig();
		}


		return mainConfig;
	}

}
