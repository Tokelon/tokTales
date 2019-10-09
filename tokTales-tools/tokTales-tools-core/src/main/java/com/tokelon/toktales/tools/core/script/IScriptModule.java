package com.tokelon.toktales.tools.core.script;

public interface IScriptModule {
	
	/* A module is not a script
	 * A module can be generated from a script
	 * 
	 */

	// Remember its script?
	//public IScript getScript();

	
	/* TODO: Document that for synchronization it is necessary that we synchronize on the script environment
	 * 
	 */
	
	/**
	 * 
	 * @return This module's environment or null.
	 */
	public IScriptEnvironment getEnvironment();
	
	
	public Object callFunction(String functionName) throws ScriptErrorException;
	
	public Object callFunction(String functionName, Object argument) throws ScriptErrorException;
	
	public Object callFunction(String functionName, Object... args) throws ScriptErrorException;

	
	public Object[] invokeFunction(String functionName, Object... args) throws ScriptErrorException;
	
	
	//public boolean hasFunction(String functionName, Object... args);
	
}
