package com.tokelon.toktales.core.engine.setup.scripts;

import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.script.StorageLocationResourceFinder;
import com.tokelon.toktales.core.storage.utils.LocationImpl;
import com.tokelon.toktales.tools.core.script.IScriptModule;
import com.tokelon.toktales.tools.core.script.ScriptErrorException;

public class InitScriptingSetupScript implements ISetupScript {


	private static final String SCRIPTS_PATH = "scripts";
	
	// Maybe have Toktales functions instead of variables (getEngine() etc.)
	private static final String LOAD_GLOBALS_MODULE = 
			"local LoadEngineModule = {}\n" +
			"function LoadEngineModule.load(tEngine, tLog, tGame)\n" +
			"  Toktales = {engine = tEngine, log = tLog, game = tGame}\n" +
			"end\n" +
			"return LoadEngineModule\n";


	@Override
	public void run(IEngineContext context) throws EngineException {
		ILogger logger = context.getLogging().getLogger(getClass());
		
		LocationImpl scriptsLocation = new LocationImpl(SCRIPTS_PATH);
		StorageLocationResourceFinder finder = new StorageLocationResourceFinder(context.getEngine().getStorageService(), scriptsLocation);
		context.getGame().getScriptManager().getResourceFinder().addResourceFinder(finder);

		try {
			IScriptModule loadModule = context.getGame().getScriptManager().loadModule(LOAD_GLOBALS_MODULE);

			loadModule.callFunction("load", context.getEngine(), logger, context.getGame());
		} catch (ScriptErrorException e) {
			logger.error("Failed to load global script objects:", e);
		}
	}

	
}
