package com.tokelon.toktales.core.script;

import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.tools.script.IScriptInstance;

import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;

public class ScriptInstanceBase {


	public static final int DEFAULT_MAX_ERRORS_LOGGED = 3; // For one source
	
	private int maxErrorsLogged = DEFAULT_MAX_ERRORS_LOGGED;
	
	
	private final TObjectIntMap<String> errorCountMap; // Does this need to be synchronized?
	
	private final IScriptInstance instance;
	
	public ScriptInstanceBase(IScriptInstance scriptInstance) {
		if(scriptInstance == null) {
			throw new NullPointerException();
		}
		
		this.instance = scriptInstance;
		
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
			TokTales.getLog().e(tag, error);
		}
		
		errorCountMap.put(source, count);
	}
	
	protected IScriptInstance getScriptInstance() {
		return instance;
	}
	
}
