package com.tokelon.toktales.core.game;

import java.io.PrintStream;

import com.tokelon.toktales.tools.script.IScriptManager;

public interface IGameScriptManager extends IScriptManager {


	public void setOutputStreams(PrintStream outStream, PrintStream errStream);

}
