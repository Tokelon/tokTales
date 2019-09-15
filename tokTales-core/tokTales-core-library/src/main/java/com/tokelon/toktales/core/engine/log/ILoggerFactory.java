package com.tokelon.toktales.core.engine.log;

public interface ILoggerFactory extends org.slf4j.ILoggerFactory {


	@Override
	public ILogger getLogger(String name);
	
	public ILogger getLogger(Class<?> clazz);
	
}
