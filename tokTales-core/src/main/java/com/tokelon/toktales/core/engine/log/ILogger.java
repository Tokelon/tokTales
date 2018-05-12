package com.tokelon.toktales.core.engine.log;

public interface ILogger {
	// TODO: Implement SLF4J compatibility

	// Maybe make the logger independent of the log framework
	// and have an emergency logger loaded when created? and after the log framework has been loaded replace that

	//public void log(level)
	

	public void d(String message);
	public void d(String tag, String message);
	
	public void e(String message);
	public void e(String tag, String message);
	//public void e(String tag, String message, Throwable throwable);
	
	public void w(String message);
	public void w(String tag, String message);
	//public void w(String tag, String message, Throwable throwable);
	
	public void i(String message);
	public void i(String tag, String message);
	
	
	public void logOnce(char loglevel, String id, String tag, String message);
	
	//public void logN(char loglevel, String id, String tag, String message, int maxN);

}
