package com.tokelon.toktales.core.script;

import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILoggerFactory;
import com.tokelon.toktales.core.engine.log.LoggingManager;
import com.tokelon.toktales.tools.core.script.IScriptModule;

import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;

public class ScriptModuleBase {


	public static final int DEFAULT_MAX_ERRORS_LOGGED = 3; // For one source
	
	private int maxErrorsLogged = DEFAULT_MAX_ERRORS_LOGGED;
	
	
	private final TObjectIntMap<String> errorCountMap; // Does this need to be synchronized?

	private final ILogger logger;
	private final IScriptModule module;
	
	public ScriptModuleBase(IScriptModule scriptModule) {
		this(LoggingManager.getLoggerFactory(), scriptModule);
	}
	
	public ScriptModuleBase(ILoggerFactory loggerFactory, IScriptModule scriptModule) {
		if(loggerFactory == null || scriptModule == null) {
			throw new NullPointerException();
		}

		this.logger = loggerFactory.getLogger(getClass());
		this.module = scriptModule;

		this.errorCountMap = new TObjectIntHashMap<>();
	}
	
	
	public void setMaxErrorsLogged(int max) {
		this.maxErrorsLogged = max;
	}
	
	public int getErrorCount(String source) {
		return errorCountMap.get(source);
	}
	

	protected ILogger getLogger() {
		return logger;
	}
	
	protected void reportError(String source) {
		if(!errorCountMap.containsKey(source)) {
			errorCountMap.put(source, 1);
		}
	}
	
	protected void reportError(String source, String error) {
		reportError(source, error, null);
	}
	
	protected void reportError(String source, String error, Throwable throwable) {
		if(!errorCountMap.containsKey(source)) {
			errorCountMap.put(source, 1);
		}
		
		int count = errorCountMap.get(source);
		if(++count <= maxErrorsLogged) {
			logger.error("Error reported from {}: {}", source, error, throwable);
		}
		
		errorCountMap.put(source, count);
	}
	
	protected IScriptModule getScriptModule() {
		return module;
	}
	
}
