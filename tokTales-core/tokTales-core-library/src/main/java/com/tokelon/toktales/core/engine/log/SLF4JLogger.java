package com.tokelon.toktales.core.engine.log;

import org.slf4j.Logger;
import org.slf4j.Marker;

import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;

public class SLF4JLogger implements ILogger {


	private final TObjectIntMap<String> logCount = new TObjectIntHashMap<>(); // Make lazy?
	
	private final Logger logger;
	
	public SLF4JLogger(Logger logger) {
		this.logger = logger;
	}
	

	@Override
	public String getName() {
		return logger.getName();
	}
	

	@Override
	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}

	@Override
	public void traceOnce(String message) {
		if(logger.isTraceEnabled() && !logCount.increment(message)) {
			logger.trace(message);
		}
	}
	
	@Override
	public void traceOnce(String message, Object arg) {
		if(logger.isTraceEnabled() && !logCount.increment(message)) {
			logger.trace(message, arg);
		}
	}

	@Override
	public void traceOnce(String message, Object arg1, Object arg2) {
		if(logger.isTraceEnabled() && !logCount.increment(message)) {
			logger.trace(message, arg1, arg2);
		}
	}

	@Override
	public void traceOnce(String message, Object... args) {
		if(logger.isTraceEnabled() && !logCount.increment(message)) {
			logger.trace(message, args);
		}
	}

	@Override
	public void traceOnceForId(String id, String message) {
		if(logger.isTraceEnabled() && !logCount.increment(id)) {
			logger.trace(message);
		}
	}
	
	@Override
	public void traceOnceForId(String id, String message, Object arg) {
		if(logger.isTraceEnabled() && !logCount.increment(id)) {
			logger.trace(message, arg);
		}
	}

	@Override
	public void traceOnceForId(String id, String message, Object arg1, Object arg2) {
		if(logger.isTraceEnabled() && !logCount.increment(id)) {
			logger.trace(message, arg1, arg2);
		}
	}

	@Override
	public void traceOnceForId(String id, String message, Object... args) {
		if(logger.isTraceEnabled() && !logCount.increment(id)) {
			logger.trace(message, args);
		}
	}
	
	@Override
	public void trace(String msg) {
		logger.trace(msg);
	}

	@Override
	public void trace(String format, Object arg) {
		logger.trace(format, arg);
	}

	@Override
	public void trace(String format, Object arg1, Object arg2) {
		logger.trace(format, arg1, arg2);
	}

	@Override
	public void trace(String format, Object... arguments) {
		logger.trace(format, arguments);
	}

	@Override
	public void trace(String msg, Throwable t) {
		logger.trace(msg, t);
	}

	@Override
	public boolean isTraceEnabled(Marker marker) {
		return logger.isTraceEnabled(marker);
	}

	@Override
	public void trace(Marker marker, String msg) {
		logger.trace(marker, msg);
	}

	@Override
	public void trace(Marker marker, String format, Object arg) {
		logger.trace(marker, format, arg);
	}

	@Override
	public void trace(Marker marker, String format, Object arg1, Object arg2) {
		logger.trace(marker, format, arg1, arg2);
	}

	@Override
	public void trace(Marker marker, String format, Object... argArray) {
		logger.trace(marker, format, argArray);
	}

	@Override
	public void trace(Marker marker, String msg, Throwable t) {
		logger.trace(marker, msg, t);
	}

	
	@Override
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}
	
	@Override
	public void debugOnce(String message) {
		if(logger.isDebugEnabled() && !logCount.increment(message)) {
			logger.debug(message);
		}
	}
	
	@Override
	public void debugOnce(String message, Object arg) {
		if(logger.isDebugEnabled() && !logCount.increment(message)) {
			logger.debug(message, arg);
		}
	}

	@Override
	public void debugOnce(String message, Object arg1, Object arg2) {
		if(logger.isDebugEnabled() && !logCount.increment(message)) {
			logger.debug(message, arg1, arg2);
		}
	}

	@Override
	public void debugOnce(String message, Object... args) {
		if(logger.isDebugEnabled() && !logCount.increment(message)) {
			logger.debug(message, args);
		}
	}
	
	@Override
	public void debugOnceForId(String id, String message) {
		if(logger.isDebugEnabled() && !logCount.increment(id)) {
			logger.debug(message);
		}
	}

	@Override
	public void debugOnceForId(String id, String message, Object arg) {
		if(logger.isDebugEnabled() && !logCount.increment(id)) {
			logger.debug(message, arg);
		}
	}

	@Override
	public void debugOnceForId(String id, String message, Object arg1, Object arg2) {
		if(logger.isDebugEnabled() && !logCount.increment(id)) {
			logger.debug(message, arg1, arg2);
		}
	}

	@Override
	public void debugOnceForId(String id, String message, Object... args) {
		if(logger.isDebugEnabled() && !logCount.increment(id)) {
			logger.debug(message, args);
		}
	}

	@Override
	public void debug(String msg) {
		logger.debug(msg);
	}

	@Override
	public void debug(String format, Object arg) {
		logger.debug(format, arg);
	}

	@Override
	public void debug(String format, Object arg1, Object arg2) {
		logger.debug(format, arg1, arg2);
	}

	@Override
	public void debug(String format, Object... arguments) {
		logger.debug(format, arguments);
	}

	@Override
	public void debug(String msg, Throwable t) {
		logger.debug(msg, t);
	}

	@Override
	public boolean isDebugEnabled(Marker marker) {
		return logger.isDebugEnabled(marker);
	}

	@Override
	public void debug(Marker marker, String msg) {
		logger.debug(marker, msg);
	}

	@Override
	public void debug(Marker marker, String format, Object arg) {
		logger.debug(marker, format, arg);
	}

	@Override
	public void debug(Marker marker, String format, Object arg1, Object arg2) {
		logger.debug(marker, format, arg1, arg2);
	}

	@Override
	public void debug(Marker marker, String format, Object... arguments) {
		logger.debug(marker, format, arguments);
	}

	@Override
	public void debug(Marker marker, String msg, Throwable t) {
		logger.debug(marker, msg, t);
	}

	
	@Override
	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	@Override
	public void infoOnce(String message) {
		if(logger.isInfoEnabled() && !logCount.increment(message)) {
			logger.info(message);
		}
	}
	
	@Override
	public void infoOnce(String message, Object arg) {
		if(logger.isInfoEnabled() && !logCount.increment(message)) {
			logger.info(message, arg);
		}
	}

	@Override
	public void infoOnce(String message, Object arg1, Object arg2) {
		if(logger.isInfoEnabled() && !logCount.increment(message)) {
			logger.info(message, arg1, arg2);
		}
	}

	@Override
	public void infoOnce(String message, Object... args) {
		if(logger.isInfoEnabled() && !logCount.increment(message)) {
			logger.info(message, args);
		}
	}

	@Override
	public void infoOnceForId(String id, String message) {
		if(logger.isInfoEnabled() && !logCount.increment(id)) {
			logger.info(message);
		}
	}
	
	@Override
	public void infoOnceForId(String id, String message, Object arg) {
		if(logger.isInfoEnabled() && !logCount.increment(id)) {
			logger.info(message, arg);
		}
	}

	@Override
	public void infoOnceForId(String id, String message, Object arg1, Object arg2) {
		if(logger.isInfoEnabled() && !logCount.increment(id)) {
			logger.info(message, arg1, arg2);
		}
	}

	@Override
	public void infoOnceForId(String id, String message, Object... args) {
		if(logger.isInfoEnabled() && !logCount.increment(id)) {
			logger.info(message, args);
		}
	}
	
	@Override
	public void info(String msg) {
		logger.info(msg);
	}

	@Override
	public void info(String format, Object arg) {
		logger.info(format, arg);
	}

	@Override
	public void info(String format, Object arg1, Object arg2) {
		logger.info(format, arg1, arg2);
	}

	@Override
	public void info(String format, Object... arguments) {
		logger.info(format, arguments);
	}

	@Override
	public void info(String msg, Throwable t) {
		logger.info(msg, t);
	}

	@Override
	public boolean isInfoEnabled(Marker marker) {
		return logger.isInfoEnabled(marker);
	}

	@Override
	public void info(Marker marker, String msg) {
		logger.info(marker, msg);
	}

	@Override
	public void info(Marker marker, String format, Object arg) {
		logger.info(marker, format, arg);
	}

	@Override
	public void info(Marker marker, String format, Object arg1, Object arg2) {
		logger.info(marker, format, arg1, arg2);
	}

	@Override
	public void info(Marker marker, String format, Object... arguments) {
		logger.info(marker, format, arguments);
	}

	@Override
	public void info(Marker marker, String msg, Throwable t) {
		logger.info(marker, msg, t);
	}

	
	@Override
	public boolean isWarnEnabled() {
		return logger.isWarnEnabled();
	}
	
	@Override
	public void warnOnce(String message) {
		if(logger.isWarnEnabled() && !logCount.increment(message)) {
			logger.warn(message);
		}
	}
	
	@Override
	public void warnOnce(String message, Object arg) {
		if(logger.isWarnEnabled() && !logCount.increment(message)) {
			logger.warn(message, arg);
		}
	}

	@Override
	public void warnOnce(String message, Object arg1, Object arg2) {
		if(logger.isWarnEnabled() && !logCount.increment(message)) {
			logger.warn(message, arg1, arg2);
		}
	}

	@Override
	public void warnOnce(String message, Object... args) {
		if(logger.isWarnEnabled() && !logCount.increment(message)) {
			logger.warn(message, args);
		}
	}

	@Override
	public void warnOnceForId(String id, String message) {
		if(logger.isWarnEnabled() && !logCount.increment(id)) {
			logger.warn(message);
		}
	}
	
	@Override
	public void warnOnceForId(String id, String message, Object arg) {
		if(logger.isWarnEnabled() && !logCount.increment(id)) {
			logger.warn(message, arg);
		}
	}

	@Override
	public void warnOnceForId(String id, String message, Object arg1, Object arg2) {
		if(logger.isWarnEnabled() && !logCount.increment(id)) {
			logger.warn(message, arg1, arg2);
		}
	}

	@Override
	public void warnOnceForId(String id, String message, Object... args) {
		if(logger.isWarnEnabled() && !logCount.increment(id)) {
			logger.warn(message, args);
		}
	}
	
	@Override
	public void warn(String msg) {
		logger.warn(msg);
	}

	@Override
	public void warn(String format, Object arg) {
		logger.warn(format, arg);
	}

	@Override
	public void warn(String format, Object... arguments) {
		logger.warn(format, arguments);
	}

	@Override
	public void warn(String format, Object arg1, Object arg2) {
		logger.warn(format, arg1, arg2);
	}

	@Override
	public void warn(String msg, Throwable t) {
		logger.warn(msg, t);
	}

	@Override
	public boolean isWarnEnabled(Marker marker) {
		return logger.isWarnEnabled(marker);
	}

	@Override
	public void warn(Marker marker, String msg) {
		logger.warn(marker, msg);
	}

	@Override
	public void warn(Marker marker, String format, Object arg) {
		logger.warn(marker, format, arg);
	}

	@Override
	public void warn(Marker marker, String format, Object arg1, Object arg2) {
		logger.warn(marker, format, arg1, arg2);
	}

	@Override
	public void warn(Marker marker, String format, Object... arguments) {
		logger.warn(marker, format, arguments);
	}

	@Override
	public void warn(Marker marker, String msg, Throwable t) {
		logger.warn(marker, msg, t);
	}

	
	@Override
	public boolean isErrorEnabled() {
		return logger.isErrorEnabled();
	}
	
	@Override
	public void errorOnce(String message) {
		if(logger.isErrorEnabled() && !logCount.increment(message)) {
			logger.error(message);
		}
	}

	@Override
	public void errorOnce(String message, Object arg) {
		if(logger.isErrorEnabled() && !logCount.increment(message)) {
			logger.error(message, arg);
		}
	}

	@Override
	public void errorOnce(String message, Object arg1, Object arg2) {
		if(logger.isErrorEnabled() && !logCount.increment(message)) {
			logger.error(message, arg1, arg2);
		}
	}

	@Override
	public void errorOnce(String message, Object... args) {
		if(logger.isErrorEnabled() && !logCount.increment(message)) {
			logger.error(message, args);
		}
	}

	@Override
	public void errorOnceForId(String id, String message) {
		if(logger.isErrorEnabled() && !logCount.increment(id)) {
			logger.error(message);
		}
	}
	
	@Override
	public void errorOnceForId(String id, String message, Object arg) {
		if(logger.isErrorEnabled() && !logCount.increment(id)) {
			logger.error(message, arg);
		}
	}

	@Override
	public void errorOnceForId(String id, String message, Object arg1, Object arg2) {
		if(logger.isErrorEnabled() && !logCount.increment(id)) {
			logger.error(message, arg1, arg2);
		}
	}

	@Override
	public void errorOnceForId(String id, String message, Object... args) {
		if(logger.isErrorEnabled() && !logCount.increment(id)) {
			logger.error(message, args);
		}
	}

	@Override
	public void error(String msg) {
		logger.error(msg);
	}

	@Override
	public void error(String format, Object arg) {
		logger.error(format, arg);
	}

	@Override
	public void error(String format, Object arg1, Object arg2) {
		logger.error(format, arg1, arg2);
	}

	@Override
	public void error(String format, Object... arguments) {
		logger.error(format, arguments);
	}

	@Override
	public void error(String msg, Throwable t) {
		logger.error(msg, t);
	}

	@Override
	public boolean isErrorEnabled(Marker marker) {
		return logger.isErrorEnabled(marker);
	}

	@Override
	public void error(Marker marker, String msg) {
		logger.error(marker, msg);
	}

	@Override
	public void error(Marker marker, String format, Object arg) {
		logger.error(marker, format, arg);
	}

	@Override
	public void error(Marker marker, String format, Object arg1, Object arg2) {
		logger.error(marker, format, arg1, arg2);
	}

	@Override
	public void error(Marker marker, String format, Object... arguments) {
		logger.error(marker, format, arguments);
	}

	@Override
	public void error(Marker marker, String msg, Throwable t) {
		logger.error(marker, msg, t);
	}

}
