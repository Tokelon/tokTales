package com.tokelon.toktales.core.engine.log;

public interface ILogging {
	// Add ILoggerNamer property?


	public ILogger getLogger(String name);
	public ILogger getLogger(Class<?> clazz);
	
	public ILoggerFactory getLoggerFactory();

	
	public org.slf4j.Logger getFacadeLogger(String name);
	public org.slf4j.Logger getFacadeLogger(Class<?> clazz);
	
	public org.slf4j.ILoggerFactory getFacadeLoggerFactory();
	
}
