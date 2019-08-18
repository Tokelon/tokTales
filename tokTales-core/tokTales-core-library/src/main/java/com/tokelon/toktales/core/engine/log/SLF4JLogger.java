package com.tokelon.toktales.core.engine.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SLF4JLogger implements ILogger {


	public static final String DEFAULT_TAG = "Log";

	private static final Logger defaultLogger = LoggerFactory.getLogger(SLF4JLogger.class);
	

	private final Logger logger;
	
	public SLF4JLogger() {
		this.logger = defaultLogger;
	}
	
	public SLF4JLogger(String name) {
		this.logger = LoggerFactory.getLogger(name);
	}
	
	public SLF4JLogger(Class<?> clazz) {
		this.logger = LoggerFactory.getLogger(clazz);
	}
	
	
	@Override
	public String getName() {
		return logger.getName();
	}
	
	@Override
	public boolean isErrorEnabled() {
		return logger.isErrorEnabled();
	}
	
	@Override
	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}
	
	@Override
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}
	
	@Override
	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}
	
	
	@Override
	public void d(String message) {
		logger.debug("{}: {}", DEFAULT_TAG, message);
	}
	
	@Override
	public void d(String tag, String message) {
		logger.debug("{}: {}", tag, message);
	}
	
	@Override
	public void d(String format, Object arg) {
		logger.debug(format, arg);
	}
	
	@Override
	public void d(String format, Object arg1, Object arg2) {
		logger.debug(format, arg1, arg2);		
	}
	
	@Override
	public void d(String format, Object... args) {
		logger.debug(format, args);
	}
	
	@Override
	public void d(String message, Throwable cause) {
		logger.debug(message, cause);
	}
	
	@Override
	public void d(String tag, String format, Object arg) {
		logger.debug("{}: " + format, tag, arg);
	}
	
	@Override
	public void d(String tag, String format, Object arg1, Object arg2) {
		logger.debug("{}: " + format, tag, arg1, arg2);
	}
	
	@Override
	public void d(String tag, String format, Object... args) {
		logger.debug(tag + ": " + format, args);
	}
	
	@Override
	public void d(String tag, String message, Throwable cause) {
		logger.debug(tag + ": " + message, cause);
	}
	
	
	@Override
	public void e(String message) {
		logger.error("{}: {}", DEFAULT_TAG, message);
	}
	
	@Override
	public void e(String tag, String message) {
		logger.error("{}: {}", tag, message);
	}
	
	@Override
	public void e(String format, Object arg) {
		logger.error(format, arg);
	}
	
	@Override
	public void e(String format, Object arg1, Object arg2) {
		logger.error(format, arg1, arg2);		
	}
	
	@Override
	public void e(String format, Object... args) {
		logger.error(format, args);
	}
	
	@Override
	public void e(String message, Throwable cause) {
		logger.error(message, cause);
	}
	
	@Override
	public void e(String tag, String format, Object arg) {
		logger.error("{}: " + format, tag, arg);
	}
	
	@Override
	public void e(String tag, String format, Object arg1, Object arg2) {
		logger.error("{}: " + format, tag, arg1, arg2);
	}
	
	@Override
	public void e(String tag, String format, Object... args) {
		logger.error(tag + ": " + format, args);
	}
	
	@Override
	public void e(String tag, String message, Throwable cause) {
		logger.error(tag + ": " + message, cause);
	}
	
	
	@Override
	public void w(String message) {
		logger.warn("{}: {}", DEFAULT_TAG, message);
	}
	
	@Override
	public void w(String tag, String message) {
		logger.warn("{}: {}", tag, message);
	}
	
	@Override
	public void w(String format, Object arg) {
		logger.warn(format, arg);
	}
	
	@Override
	public void w(String format, Object arg1, Object arg2) {
		logger.warn(format, arg1, arg2);		
	}
	
	@Override
	public void w(String format, Object... args) {
		logger.warn(format, args);
	}
	
	@Override
	public void w(String message, Throwable cause) {
		logger.warn(message, cause);
	}
	
	@Override
	public void w(String tag, String format, Object arg) {
		logger.warn("{}: " + format, tag, arg);
	}
	
	@Override
	public void w(String tag, String format, Object arg1, Object arg2) {
		logger.warn("{}: " + format, tag, arg1, arg2);
	}
	
	@Override
	public void w(String tag, String format, Object... args) {
		logger.warn(tag + ": " + format, args);
	}
	
	@Override
	public void w(String tag, String message, Throwable cause) {
		logger.warn(tag + ": " + message, cause);
	}
	
	
	@Override
	public void i(String message) {
		logger.info("{}: {}", DEFAULT_TAG, message);
	}
	
	@Override
	public void i(String tag, String message) {
		logger.info("{}: {}", tag, message);
	}

	@Override
	public void i(String format, Object arg) {
		logger.info(format, arg);
	}
	
	@Override
	public void i(String format, Object arg1, Object arg2) {
		logger.info(format, arg1, arg2);		
	}
	
	@Override
	public void i(String format, Object... args) {
		logger.info(format, args);
	}
	
	@Override
	public void i(String message, Throwable cause) {
		logger.info(message, cause);
	}
	
	@Override
	public void i(String tag, String format, Object arg) {
		logger.info("{}: " + format, tag, arg);
	}
	
	@Override
	public void i(String tag, String format, Object arg1, Object arg2) {
		logger.info("{}: " + format, tag, arg1, arg2);
	}
	
	@Override
	public void i(String tag, String format, Object... args) {
		logger.info(tag + ": " + format, args);
	}
	
	@Override
	public void i(String tag, String message, Throwable cause) {
		logger.info(tag + ": " + message, cause);
	}
	
	
	@Override
	public void logOnce(char loglevel, String id, String tag, String message) {
		// TODO: Implement
		// Map String to int: increment and only log if 0 (same with logN)
	}
	
}
