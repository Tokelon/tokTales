package com.tokelon.toktales.core.engine.log;

import org.slf4j.Logger;

public interface ILogger extends Logger {


	public void traceOnce(String message);
	public void traceOnce(String message, Object arg);
	public void traceOnce(String message, Object arg1, Object arg2);
	public void traceOnce(String message, Object... args);
	
	public void traceOnceForId(String id, String message);
	public void traceOnceForId(String id, String message, Object arg);
	public void traceOnceForId(String id, String message, Object arg1, Object arg2);
	public void traceOnceForId(String id, String message, Object... args);
	
	
	public void debugOnce(String message);
	public void debugOnce(String message, Object arg);
	public void debugOnce(String message, Object arg1, Object arg2);
	public void debugOnce(String message, Object... args);
	
	public void debugOnceForId(String id, String message);
	public void debugOnceForId(String id, String message, Object arg);
	public void debugOnceForId(String id, String message, Object arg1, Object arg2);
	public void debugOnceForId(String id, String message, Object... args);
	
	
	public void infoOnce(String message);
	public void infoOnce(String message, Object arg);
	public void infoOnce(String message, Object arg1, Object arg2);
	public void infoOnce(String message, Object... args);
	
	public void infoOnceForId(String id, String message);
	public void infoOnceForId(String id, String message, Object arg);
	public void infoOnceForId(String id, String message, Object arg1, Object arg2);
	public void infoOnceForId(String id, String message, Object... args);
	
	
	public void warnOnce(String message);
	public void warnOnce(String message, Object arg);
	public void warnOnce(String message, Object arg1, Object arg2);
	public void warnOnce(String message, Object... args);
	
	public void warnOnceForId(String id, String message);
	public void warnOnceForId(String id, String message, Object arg);
	public void warnOnceForId(String id, String message, Object arg1, Object arg2);
	public void warnOnceForId(String id, String message, Object... args);
	
	
	public void errorOnce(String message);
	public void errorOnce(String message, Object arg);
	public void errorOnce(String message, Object arg1, Object arg2);
	public void errorOnce(String message, Object... args);
	
	public void errorOnceForId(String id, String message);
	public void errorOnceForId(String id, String message, Object arg);
	public void errorOnceForId(String id, String message, Object arg1, Object arg2);
	public void errorOnceForId(String id, String message, Object... args);
	
	
	
	/*
	public enum LogLevel {
		ERROR,
		INFO,
		DEBUG,
		TRACE
	}

	
	public boolean isLogLevelEnabled(LogLevel level);
	
	public void log(LogLevel level, String message);
	public void log(LogLevel level, String format, Object arg);
	public void log(LogLevel level, String format, Object arg1, Object arg2);
	public void log(LogLevel level, String format, Object... args);
	public void log(LogLevel level, String message, Throwable cause);
	
	public void logN(LogLevel loglevel, String id, int n, String message, Object arg);
	public void logEveryN(LogLevel loglevel, String id, int n, String message, Object arg);
	
	public void logOnce(LogLevel loglevel, String id, String message, Object arg);
	public void logOnce(LogLevel loglevel, String id, String message, Object arg1, Object arg2);
	public void logOnce(LogLevel loglevel, String id, String message, Object... args);
	
	public void logOnce(LogLevel loglevel, String id, ILoggingContext, String message, Object arg);
	public void logOnce(LogLevel loglevel, String id, ILoggingContext, String message, Object arg1, Object arg2);
	public void logOnce(LogLevel loglevel, String id, ILoggingContext, String message, Object... args);
	*/

}
