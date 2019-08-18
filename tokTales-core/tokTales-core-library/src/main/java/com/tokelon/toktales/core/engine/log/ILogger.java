package com.tokelon.toktales.core.engine.log;

public interface ILogger {
	// TODO: Remove overloads with tag because they are inefficient

	
	/* Standard Logger
	System.out.printf("d | %s : %s", tag, message);
	System.out.println();
	
	System.out.printf("d | %s : " + format.replaceAll("{}", "%s"), name, arg);
	 */
	
	/*
	public enum LogLevel {
		ERROR,
		INFO,
		DEBUG,
		TRACE
	}


	public void log(LogLevel level, String message);
	public void log(LogLevel level, String tag, String message);

	public void log(LogLevel level, String format, Object arg);
	public void log(LogLevel level, String format, Object arg1, Object arg2);
	public void log(LogLevel level, String format, Object... args);
	public void log(LogLevel level, String message, Throwable cause);
	public void log(LogLevel level, String tag, String format, Object arg);
	public void log(LogLevel level, String tag, String format, Object arg1, Object arg2);
	public void log(LogLevel level, String tag, String format, Object... args);
	public void log(LogLevel level, String tag, String message, Throwable cause);
	*/

	
	public String getName();
	
	public boolean isErrorEnabled();
	public boolean isInfoEnabled();
	public boolean isDebugEnabled();
	public boolean isTraceEnabled();

	
	public void d(String message);
	public void d(String tag, String message);

	public void d(String format, Object arg);
	public void d(String format, Object arg1, Object arg2);
	public void d(String format, Object... args);
	public void d(String message, Throwable cause);
	public void d(String tag, String format, Object arg);
	public void d(String tag, String format, Object arg1, Object arg2);
	public void d(String tag, String format, Object... args);
	public void d(String tag, String message, Throwable cause);
	

	public void e(String message);
	public void e(String tag, String message);
	
	public void e(String format, Object arg);
	public void e(String format, Object arg1, Object arg2);
	public void e(String format, Object... args);
	public void e(String message, Throwable cause);
	public void e(String tag, String format, Object arg);
	public void e(String tag, String format, Object arg1, Object arg2);
	public void e(String tag, String format, Object... args);
	public void e(String tag, String message, Throwable cause);

	
	public void w(String message);
	public void w(String tag, String message);

	public void w(String format, Object arg);
	public void w(String format, Object arg1, Object arg2);
	public void w(String format, Object... args);
	public void w(String message, Throwable cause);
	public void w(String tag, String format, Object arg);
	public void w(String tag, String format, Object arg1, Object arg2);
	public void w(String tag, String format, Object... args);
	public void w(String tag, String message, Throwable cause);
	
	
	public void i(String message);
	public void i(String tag, String message);
	
	public void i(String format, Object arg);
	public void i(String format, Object arg1, Object arg2);
	public void i(String format, Object... args);
	public void i(String message, Throwable cause);
	public void i(String tag, String format, Object arg);
	public void i(String tag, String format, Object arg1, Object arg2);
	public void i(String tag, String format, Object... args);
	public void i(String tag, String message, Throwable cause);

	
	
	
	public void logOnce(char loglevel, String id, String tag, String message);
	
	//public void logN(char loglevel, String id, String tag, String message, int maxN);

}
