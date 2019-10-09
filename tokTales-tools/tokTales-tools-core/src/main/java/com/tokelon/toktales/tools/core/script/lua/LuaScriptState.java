package com.tokelon.toktales.tools.core.script.lua;

import java.io.InputStream;
import java.util.Arrays;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LoadState;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.compiler.LuaC;
import org.luaj.vm2.lib.Bit32Lib;
import org.luaj.vm2.lib.CoroutineLib;
import org.luaj.vm2.lib.DebugLib;
import org.luaj.vm2.lib.PackageLib;
import org.luaj.vm2.lib.StringLib;
import org.luaj.vm2.lib.TableLib;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JseBaseLib;
import org.luaj.vm2.lib.jse.JseMathLib;

public class LuaScriptState {

	
	private final Globals mGlobals;
	
	private final LuaExtendableResourceFinder mResourceFinder;

	public LuaScriptState() {
		
		//mGlobals = JsePlatform.standardGlobals();
		mGlobals = customGlobals();
		
		mResourceFinder = new LuaExtendableResourceFinder();
		mGlobals.finder = mResourceFinder;
	}
	
	
	
	public synchronized Globals getGlobals() {
		return mGlobals;
	}
	
	
	public synchronized LuaValue coerceToLua(Object o) {
		return CoerceJavaToLua.coerce(o);
	}
	
	
	
	/**
	 * 
	 * @param sourceIn
	 * @param sourcename
	 * @return
	 * @throws LuaError
	 */
	public synchronized LuaValue load(InputStream sourceIn, String sourcename) throws LuaError {
		/* Usually when chunks/functions are defined through files
		 * their source is the filename prefixed with a @
		 */
		LuaValue inChunk = mGlobals.load(sourceIn, "@" + sourcename, "bt", mGlobals);
		
		return inChunk;
	}
	
	/**
	 * 
	 * @param script
	 * @return
	 * @throws LuaError
	 */
	public synchronized LuaValue load(String script) throws LuaError {
		LuaValue codeChunk = mGlobals.load(script);		// Uses script itself as the chunkname
		
		return codeChunk;
	}
	
	
	/**
	 * 
	 * @param chunk
	 * @throws LuaError
	 */
	public synchronized void run(LuaValue chunk) throws LuaError {
		chunk.call();
	}
	
	
	public LuaExtendableResourceFinder getResourceFinder() {
		return mResourceFinder;
	}
	
	
	
	
	private class ScriptRun {
		
		public void setParameters(Object... parameters) {
			
		}
		
		public void setup(LuaValue chunk, Object... parameters) {
			
		}
		
		public void run() {
			
		}
		
	}
	
	
	
	private static final String[] CLASSNAME_WHITELIST =
	{
		"com.tokelon.toktales",
		
		
		/* WARNING:
		 * This means that any class in Object for ex. something like Object$SomeClass will also be acessible
		 * 
		 */
		"java.lang.Object",
		
	};
	
	private static final String[] CLASSNAME_BLACKLIST =
	{
		"com.tokelon.toktales.android",	// Limit access to android classes for now
		"com.tokelon.toktales.tools.script.CustomLuajavaLib",
	};

	public static Globals customGlobals() {
		Globals globals = new Globals();
		globals.load(new JseBaseLib());
		globals.load(new PackageLib());
		globals.load(new Bit32Lib());
		globals.load(new TableLib());
		globals.load(new StringLib());
		globals.load(new CoroutineLib());
		globals.load(new JseMathLib());
		//globals.load(new JseIoLib());
		//globals.load(new JseOsLib());
		
		//globals.load(new LuajavaLib());
		CustomLuajavaLib luajavaLib = new CustomLuajavaLib();
		CustomLuajavaLib.addToWhitelist(Arrays.asList(CLASSNAME_WHITELIST));
		CustomLuajavaLib.addToBlacklist(Arrays.asList(CLASSNAME_BLACKLIST));
		
		// For some reason this does not actually load the passed object but rather creates a new objects of the same class
		globals.load(luajavaLib);	// Use the custom Luajava lib
		
		// Debugging
		globals.load(new DebugLib());	// TODO: Important - Add switch to toggle debugging! As it exposes too much data and functionality
		
		LoadState.install(globals);
		LuaC.install(globals);
		
		return globals;		
	}
	
	
}
