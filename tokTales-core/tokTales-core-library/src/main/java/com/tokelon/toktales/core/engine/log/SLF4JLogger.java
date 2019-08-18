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
	public void d(String message) {
		logger.debug("{}: {}", DEFAULT_TAG, message);
	}
	
	@Override
	public void d(String tag, String message) {
		logger.debug("{}: {}", tag, message);
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
	public void w(String message) {
		logger.warn("{}: {}", DEFAULT_TAG, message);
	}
	
	@Override
	public void w(String tag, String message) {
		logger.warn("{}: {}", tag, message);
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
	public void logOnce(char loglevel, String id, String tag, String message) {
		// TODO: Implement
		// Map String to int: increment and only log if 0 (same with logN)
	}
	
}
