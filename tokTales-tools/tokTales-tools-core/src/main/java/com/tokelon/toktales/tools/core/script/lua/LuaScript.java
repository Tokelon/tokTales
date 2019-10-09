package com.tokelon.toktales.tools.core.script.lua;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;

import com.tokelon.toktales.tools.core.script.IScript;
import com.tokelon.toktales.tools.core.script.ScriptErrorException;

public class LuaScript implements IScript {

	
	private final LuaValue mScriptValue;
	
	
	/**
	 * 
	 * @param scriptValue
	 * @throws NullPointerException If scriptValue is null.
	 */
	public LuaScript(LuaValue scriptValue) {
		if(scriptValue == null) {
			throw new NullPointerException();
		}
		
		this.mScriptValue = scriptValue;
	}
	
	
	
	public LuaValue getScriptValue() {
		return mScriptValue;
	}
	
	
	
	public void runScript() throws ScriptErrorException {
		try {
			mScriptValue.call();
		}
		catch (LuaError le) {
			throw new ScriptErrorException(le);
		}
	}
	
	
}
