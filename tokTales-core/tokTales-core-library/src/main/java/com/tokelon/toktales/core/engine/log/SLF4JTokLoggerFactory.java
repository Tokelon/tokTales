package com.tokelon.toktales.core.engine.log;

public class SLF4JTokLoggerFactory implements ITokLoggerFactory {


	@Override
	public ITokLogger create() {
		return new SLF4JTokLogger();
	}

	@Override
	public ITokLogger create(String name) {
		return new SLF4JTokLogger(name);
	}
	
	@Override
	public ITokLogger create(Class<?> clazz) {
		return new SLF4JTokLogger(clazz);
	}

}
