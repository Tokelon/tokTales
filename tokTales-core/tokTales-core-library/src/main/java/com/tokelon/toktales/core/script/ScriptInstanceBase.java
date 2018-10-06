package com.tokelon.toktales.core.script;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.tools.script.IScriptInstance;

public class ScriptInstanceBase {

	// For one source
	public static final int DEFAULT_MAX_ERRORS_LOGGED = 3;
	
	
	
	private final Map<String, Integer> errorCountMap;
	
	private int maxErrorsLogged = DEFAULT_MAX_ERRORS_LOGGED;
	
	
	private final IScriptInstance instance;
	
	public ScriptInstanceBase(IScriptInstance scriptInstance) {
		if(scriptInstance == null) {
			throw new NullPointerException();
		}
		
		this.instance = scriptInstance;
		
		errorCountMap = Collections.synchronizedMap(new HashMap<String, Integer>());
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
			TokTales.getLog().e(tag, error);
		}
		
		errorCountMap.put(source, count);
	}
	
	protected IScriptInstance getScriptInstance() {
		return instance;
	}
	
	
}
