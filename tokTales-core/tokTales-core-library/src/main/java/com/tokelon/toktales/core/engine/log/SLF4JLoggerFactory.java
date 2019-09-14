package com.tokelon.toktales.core.engine.log;

import javax.inject.Inject;

public class SLF4JLoggerFactory implements ILoggerFactory {


	private final org.slf4j.ILoggerFactory facadeLoggerFactory;
	private final ILoggerNamer loggerNamer;

	@Inject
	public SLF4JLoggerFactory(org.slf4j.ILoggerFactory facadeLoggerFactory, ILoggerNamer loggerNamer) {
		this.facadeLoggerFactory = facadeLoggerFactory;
		this.loggerNamer = loggerNamer;
	}
	
	
	@Override
	public ILogger getLogger(String name) {
		return new SLF4JLogger(facadeLoggerFactory.getLogger(name));
	}

	@Override
	public ILogger getLogger(Class<?> clazz) {
		return new SLF4JLogger(facadeLoggerFactory.getLogger(loggerNamer.getNameFromClass(clazz)));
	}
	
}
