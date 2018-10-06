package com.tokelon.toktales.tools.script;

public interface IScriptInstance {



	/* TODO: Document that for synchronization it is necessary that we synchronize on the script environment
	 * 
	 */

	/**
	 * 
	 * @return This module's environment or null.
	 */
	public IScriptEnvironment getEnvironment();


	public Object callMethod(String methodName) throws ScriptErrorException;

	public Object callMethod(String methodName, Object argument) throws ScriptErrorException;

	public Object callMethod(String methodName, Object... args) throws ScriptErrorException;


	public Object[] invokeMethod(String methodName, Object... args) throws ScriptErrorException;


	//public boolean hasMethod(String methodName, Object... args);

}
