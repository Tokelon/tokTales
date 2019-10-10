package com.tokelon.toktales.tools.core.script.lua;

import java.util.HashMap;
import java.util.Map;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@ThreadSafe
public class LuaScriptManager extends LuaScriptEnvironment implements ILuaScriptManager {


	private static final String SCRIPT_LOAD_MODULE =
			"function LoadModule(modulename, module)\n" +
			"  _G[modulename] = module\n" +
			"end\n";
	
	private static final String FUNCTION_LOAD_MODULE = "LoadModule";
	
	
	
	private final Map<ILuaClass<?>, ILuaObject> luaMappings;
	private final LuaValue loadModuleFunction;
	
	private final Logger logger;
	
	public LuaScriptManager() {
		this(LoggerFactory.getILoggerFactory()); // Use getter in supertype instead?
	}
	
	public LuaScriptManager(ILoggerFactory loggerFactory) {
		super(loggerFactory);
		
		this.logger = loggerFactory.getLogger(getClass().getName());
		
		luaMappings = new HashMap<ILuaClass<?>, ILuaObject>();
		loadModuleFunction = initLoadModuleFunction();
	}
	
	
	private LuaValue initLoadModuleFunction() {

		LuaValue loadModuleScript = getScriptState().load(SCRIPT_LOAD_MODULE);
		
		LuaValue lmfunc = null;
		try {
			loadModuleScript.call();
			
			lmfunc = getScriptState().getGlobals().get(FUNCTION_LOAD_MODULE);
		}
		catch (LuaError le) {
			logger.error("Failed call load module script:", le);
		}

		return lmfunc;
	}
	
	
	// TODO: Is synchronization correct ?

	@Override
	public synchronized void addLuaObject(ILuaClass<?> luaClass, String moduleName) {
		if(luaClass == null || moduleName == null) {
			throw new NullPointerException();
		}
		if(moduleName.trim().isEmpty()) {
			throw new IllegalArgumentException("moduleName cannot be empty");
		}
		
		ILuaObject luaObject = luaClass.getLuaObject();
		
		
		LuaValue luaValueModuleName = getScriptState().coerceToLua(moduleName);
		LuaValue luaValueObject = getScriptState().coerceToLua(luaObject);
		
		try {
			loadModuleFunction.call(luaValueModuleName, luaValueObject);
			
			luaMappings.put(luaClass, luaObject);
		}
		catch (LuaError le) {
			logger.error("Failed to pass Object to Lua:", le);
		}
	}
	
}
