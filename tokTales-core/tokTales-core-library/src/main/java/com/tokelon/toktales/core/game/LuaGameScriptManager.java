package com.tokelon.toktales.core.game;

import javax.inject.Inject;

import org.slf4j.ILoggerFactory;

import com.tokelon.toktales.tools.script.lua.LuaScriptManager;

public class LuaGameScriptManager extends LuaScriptManager implements IGameScriptManager {


	public LuaGameScriptManager() {
		super();
	}
	
	@Inject
	public LuaGameScriptManager(ILoggerFactory loggerFactory) {
		super(loggerFactory);
	}
	
}
