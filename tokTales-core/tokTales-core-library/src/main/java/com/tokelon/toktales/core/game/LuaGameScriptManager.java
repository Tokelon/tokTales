package com.tokelon.toktales.core.game;

import java.io.PrintStream;

import javax.inject.Inject;

import org.luaj.vm2.Globals;
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


	@Override
	public void setOutputStreams(PrintStream outStream, PrintStream errStream) {
		Globals globals = getScriptState().getGlobals();

		globals.STDOUT = outStream;
		globals.STDERR = errStream;
	}

}
