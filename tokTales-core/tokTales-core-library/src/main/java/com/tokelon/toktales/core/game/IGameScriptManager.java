package com.tokelon.toktales.core.game;

import com.tokelon.toktales.tools.script.IScriptEnvironment;
import com.tokelon.toktales.tools.script.lua.ILuaClass;

public interface IGameScriptManager extends IScriptEnvironment {

	// TODO: Rework this interface
	
	public static final String MODULE_PLAYER = "Player";
	
	/**
	 * 
	 * @param luaClass
	 * @param moduleName
	 * @throws IllegalArgumentException If moduleName is empty.
	 * @throws NullPointerException If luaClass, luaObject or moduleName is null.
	 */
	public void addLuaObject(ILuaClass<?> luaClass, String moduleName);
	
}
