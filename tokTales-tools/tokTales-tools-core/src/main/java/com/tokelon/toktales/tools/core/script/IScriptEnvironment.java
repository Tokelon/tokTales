package com.tokelon.toktales.tools.core.script;

import java.io.InputStream;

public interface IScriptEnvironment {


	public IExtendableResourceFinder getResourceFinder();
	
	
	public IScript loadScript(InputStream source, String sourcename) throws ScriptErrorException;
	
	public IScript loadScript(String scriptCode) throws ScriptErrorException;
	
	
	// Using this method has the advantage that the call is synchronized on the script environment
	public void runScript(IScript script) throws ScriptErrorException;
	
	
	
	
	public IScriptModule loadModule(InputStream source, String sourcename) throws ScriptErrorException;
	public IScriptModule loadModule(String scriptCode) throws ScriptErrorException;
	
	
	public IScriptModule moduleFromScript(IScript script) throws ScriptErrorException;	// ScriptModuleException
	
}
