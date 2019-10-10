package com.tokelon.toktales.tools.script.lua;

import com.tokelon.toktales.tools.script.IScriptManager;

public interface ILuaScriptManager extends IScriptManager {
	// TODO: What is all this needed for?


	//public static final String MODULE_PLAYER = "Player";

	/**
	 * 
	 * @param luaClass
	 * @param moduleName
	 * @throws IllegalArgumentException If moduleName is empty.
	 * @throws NullPointerException If luaClass, luaObject or moduleName is null.
	 */
	public void addLuaObject(ILuaClass<?> luaClass, String moduleName);
	
}
