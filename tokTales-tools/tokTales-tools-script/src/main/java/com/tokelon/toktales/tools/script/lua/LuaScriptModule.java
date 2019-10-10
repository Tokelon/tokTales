package com.tokelon.toktales.tools.script.lua;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;

import com.tokelon.toktales.tools.script.IScriptEnvironment;
import com.tokelon.toktales.tools.script.IScriptModule;
import com.tokelon.toktales.tools.script.ScriptErrorException;


public class LuaScriptModule implements IScriptModule {

	
	private final LuaValue mModuleValue;
	
	private LuaScriptEnvironment mScriptEnvironment;
	
	
	
	/**
	 * 
	 * @param moduleValue
	 * @throws NullPointerException If moduleValue is null.
	 */
	public LuaScriptModule(LuaValue moduleValue) {
		if(moduleValue == null) {
			throw new NullPointerException();
		}
		
		this.mModuleValue = moduleValue;
	}
	
	
	/**
	 * 
	 * @param moduleValue
	 * @param scriptEnvironment
	 * @throws NullPointerException If moduleValue is null.
	 */
	public LuaScriptModule(LuaValue moduleValue, LuaScriptEnvironment scriptEnvironment) {
		if(moduleValue == null) {
			throw new NullPointerException();
		}
		
		this.mModuleValue = moduleValue;
		this.mScriptEnvironment = scriptEnvironment;
	}
	
		
	
	public LuaValue getModuleValue() {
		return mModuleValue;
	}
	
	
	
	
	@Override
	public IScriptEnvironment getEnvironment() {
		return mScriptEnvironment;
	}

	

	@Override
	public Object callFunction(String functionName) throws ScriptErrorException {
		
		// Use the environment somehow?
		
		try {
			LuaValue functionValue = mModuleValue.get(functionName);
			
			if(!functionValue.isnil() && functionValue.isfunction()) {
				LuaValue result = functionValue.call();
				
				return result.isnil() ? null : CoerceLuaToJava.coerce(result, Object.class);
			}
			else {
				throw new ScriptErrorException("No such function: " +functionName);
			}
		}
		catch(LuaError le) {
			throw new ScriptErrorException(le);
		}
	}

	
	@Override
	public Object callFunction(String functionName, Object argument) throws ScriptErrorException {
		
		try {
			LuaValue functionValue = mModuleValue.get(functionName);
			
			if(!functionValue.isnil() && functionValue.isfunction()) {
				LuaValue luaArg = CoerceJavaToLua.coerce(argument);
				
				LuaValue result = functionValue.call(luaArg);
				
				
				return result.isnil() ? null : CoerceLuaToJava.coerce(result, Object.class);
			}
			else {
				throw new ScriptErrorException("No such function: " +functionName);
			}
		}
		catch(LuaError le) {
			throw new ScriptErrorException(le);
		}
		
	}
	
	
	@Override
	public Object callFunction(String functionName, Object... args) throws ScriptErrorException {

		try {
			LuaValue functionValue = mModuleValue.get(functionName);
			
			if(!functionValue.isnil() && functionValue.isfunction()) {
				LuaValue[] luaArgs = new LuaValue[args.length];
				
				for(int i = 0; i < args.length; i++) {
					luaArgs[i] = CoerceJavaToLua.coerce(args[i]);
				}
				

				Varargs results = functionValue.invoke(luaArgs);
				LuaValue firstResult = results.arg1();
				
				return firstResult.isnil() ? null : CoerceLuaToJava.coerce(firstResult, Object.class);
			}
			else {
				throw new ScriptErrorException("No such function: " +functionName);
			}
		}
		catch(LuaError le) {
			throw new ScriptErrorException(le);
		}
		
	}
	
	
	@Override
	public Object[] invokeFunction(String functionName, Object... args) throws ScriptErrorException {

		try {
			LuaValue functionValue = mModuleValue.get(functionName);
			
			if(!functionValue.isnil() && functionValue.isfunction()) {
				LuaValue[] luaArgs = new LuaValue[args.length];
				
				for(int i = 0; i < args.length; i++) {
					luaArgs[i] = CoerceJavaToLua.coerce(args[i]);
				}
				
				
				Varargs results = functionValue.invoke(luaArgs);
				
				
				Object[] javaResults = new Object[results.narg()];
				
				for(int i = 0; i < results.narg(); i++) {
					LuaValue res = results.arg(i);
					javaResults[i] = res.isnil() ? null : CoerceLuaToJava.coerce(res, Object.class);
				}
				
				return javaResults;
			}
			else {
				throw new ScriptErrorException("No such function: " +functionName);
			}
		}
		catch(LuaError le) {
			throw new ScriptErrorException(le);
		}
		
	}
	
	
	
}
