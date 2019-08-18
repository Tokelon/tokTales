package com.tokelon.toktales.core.engine.log;

public class SLF4JLoggerFactory implements ILoggerFactory {


	@Override
	public ILogger create() {
		return new SLF4JLogger();
	}

	@Override
	public ILogger create(String name) {
		return new SLF4JLogger(name);
	}
	
	@Override
	public ILogger create(Class<?> clazz) {
		return new SLF4JLogger(clazz);
	}

}
