package com.tokelon.toktales.tools.script;

public interface IScript {

	/* TODO: Document that for synchronization it is necessary that we synchronize on the script environment
	 * 
	 */
	
	public void runScript() throws ScriptErrorException;
	
}
