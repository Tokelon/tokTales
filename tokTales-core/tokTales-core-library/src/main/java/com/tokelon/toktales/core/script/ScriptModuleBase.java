package com.tokelon.toktales.core.script;

import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.LoggingManager;
import com.tokelon.toktales.tools.script.IScriptModule;

import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;

public class ScriptModuleBase {


	public static final int DEFAULT_MAX_ERRORS_LOGGED = 3; // For one source
	
	private int maxErrorsLogged = DEFAULT_MAX_ERRORS_LOGGED;
	
	private static final ILogger logger = LoggingManager.getLogger(ScriptModuleBase.class);
	
	
	private final TObjectIntMap<String> errorCountMap; // Does this need to be synchronized?

	private final IScriptModule module;
	
	public ScriptModuleBase(IScriptModule scriptModule) {
		if(scriptModule == null) {
			throw new NullPointerException();
		}
		
		this.module = scriptModule;
		
		this.errorCountMap = new TObjectIntHashMap<>();
	}
	
	
	public void setMaxErrorsLogged(int max) {
		this.maxErrorsLogged = max;
	}
	
	public int getErrorCount(String source) {
		return errorCountMap.get(source);
	}
	
	
	
	protected void reportError(String source) {
		if(!errorCountMap.containsKey(source)) {
			errorCountMap.put(source, 1);
		}
	}
	
	protected void reportError(String tag, String source, String error) {
		if(!errorCountMap.containsKey(source)) {
			errorCountMap.put(source, 1);
		}
		
		int count = errorCountMap.get(source);
		if(++count <= maxErrorsLogged) {
			logger.error("{}: {}", tag, error);
		}
		
		errorCountMap.put(source, count);
	}
	
	protected IScriptModule getScriptModule() {
		return module;
	}
	
}
