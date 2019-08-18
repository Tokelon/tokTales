package com.tokelon.toktales.core.engine.log;

public interface ILoggerFactory {


	public ILogger create();
	public ILogger create(String name);
	public ILogger create(Class<?> clazz);

}
