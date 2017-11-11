package com.tokelon.toktales.core.engine.log;

import com.tokelon.toktales.core.prog.IProgramInterface;

public interface ILogger extends IProgramInterface {

	//public void log(level)
	
	// TODO: Maybe make the logger independent of the log framework
	// and have an emergency logger loaded when created? and after the log framework has been loaded replace that
	
	
	public void d(String message);
	public void d(String tag, String message);
	
	public void e(String message);
	public void e(String tag, String message);
	
	public void w(String message);
	public void w(String tag, String message);
	
	public void i(String message);
	public void i(String tag, String message);
	
	
	public void logOnce(char loglevel, String id, String tag, String message);
	
	//public void logN(char loglevel, String id, String tag, String message, int maxN);

}
