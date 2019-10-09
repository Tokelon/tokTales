package com.tokelon.toktales.tools.core.script.lua;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;

import com.tokelon.toktales.tools.core.script.IScriptEnvironment;
import com.tokelon.toktales.tools.core.script.IScriptInstance;
import com.tokelon.toktales.tools.core.script.ScriptErrorException;

public class LuaScriptInstance implements IScriptInstance {

	
	private final LuaValue mInstanceValue;
	
	private LuaScriptEnvironment mScriptEnvironment;
	
	
	
	/**
	 * 
	 * @param instanceValue
	 * @throws NullPointerException If instanceValue is null.
	 */
	public LuaScriptInstance(LuaValue instanceValue) {
		if(instanceValue == null) {
			throw new NullPointerException();
		}
		
		this.mInstanceValue = instanceValue;
	}

	
	/**
	 * 
	 * @param instanceValue
	 * @throws NullPointerException If instanceValue is null.
	 */
	public LuaScriptInstance(LuaValue instanceValue, LuaScriptEnvironment scriptEnvironment) {
		if(instanceValue == null) {
			throw new NullPointerException();
		}
		
		this.mInstanceValue = instanceValue;
		this.mScriptEnvironment = scriptEnvironment;
	}
	

	public LuaValue getInstanceValue() {
		return mInstanceValue;
	}
	
	
	
	
	@Override
	public IScriptEnvironment getEnvironment() {
		return mScriptEnvironment;
	}
	

	@Override
	public Object callMethod(String methodName) throws ScriptErrorException {
		
		// Use the environment somehow ?
		
		try {
			// Maybe completely ignore the methodValue and immediately call the method
			
			LuaValue methodValue = mInstanceValue.get(methodName);
			
			if(!methodValue.isnil() && methodValue.isfunction()) {
				LuaValue result = mInstanceValue.method(methodName);
				
				return result.isnil() ? null : CoerceLuaToJava.coerce(result, Object.class);
			}
			else {
				throw new ScriptErrorException("No such method: " +methodName);
			}
		}
		catch(LuaError le) {
			throw new ScriptErrorException(le);
		}
	}

	
	@Override
	public Object callMethod(String methodName, Object argument) throws ScriptErrorException {
		try {
			LuaValue methodValue = mInstanceValue.get(methodName);
			
			if(!methodValue.isnil() && methodValue.isfunction()) {
				LuaValue luaArg = CoerceJavaToLua.coerce(argument);
				
				LuaValue result = mInstanceValue.method(methodName, luaArg);
				
				
				return result.isnil() ? null : CoerceLuaToJava.coerce(result, Object.class);
			}
			else {
				throw new ScriptErrorException("No such method: " +methodName);
			}
		}
		catch(LuaError le) {
			throw new ScriptErrorException(le);
		}
	}

	
	@Override
	public Object callMethod(String methodName, Object... args) throws ScriptErrorException {
		try {
			LuaValue methodValue = mInstanceValue.get(methodName);
			
			if(!methodValue.isnil() && methodValue.isfunction()) {
				LuaValue[] luaArgs = new LuaValue[args.length];
				
				for(int i = 0; i < args.length; i++) {
					luaArgs[i] = CoerceJavaToLua.coerce(args[i]);
				}
				
				Varargs results = mInstanceValue.invokemethod(methodName, luaArgs);
				LuaValue firstResult = results.arg1();
				
				return firstResult.isnil() ? null : CoerceLuaToJava.coerce(firstResult, Object.class);
			}
			else {
				throw new ScriptErrorException("No such method: " +methodName);
			}
		}
		catch(LuaError le) {
			throw new ScriptErrorException(le);
		}
	}

	
	@Override
	public Object[] invokeMethod(String methodName, Object... args) throws ScriptErrorException {
		try {
			LuaValue methodValue = mInstanceValue.get(methodName);
			
			if(!methodValue.isnil() && methodValue.isfunction()) {
				LuaValue[] luaArgs = new LuaValue[args.length];
				
				for(int i = 0; i < args.length; i++) {
					luaArgs[i] = CoerceJavaToLua.coerce(args[i]);
				}
				
				Varargs results = mInstanceValue.invokemethod(methodName, luaArgs);
				
				
				Object[] javaResults = new Object[results.narg()];
				
				for(int i = 0; i < results.narg(); i++) {
					LuaValue res = results.arg(i);
					javaResults[i] = res.isnil() ? null : CoerceLuaToJava.coerce(res, Object.class);
				}
				
				return javaResults;
			}
			else {
				throw new ScriptErrorException("No such method: " +methodName);
			}
		}
		catch(LuaError le) {
			throw new ScriptErrorException(le);
		}
	}

	
	
}
