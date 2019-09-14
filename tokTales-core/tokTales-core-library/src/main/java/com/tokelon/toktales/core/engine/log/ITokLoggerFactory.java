package com.tokelon.toktales.core.engine.log;

public interface ITokLoggerFactory {


	public ITokLogger create();
	public ITokLogger create(String name);
	public ITokLogger create(Class<?> clazz);
	
}
