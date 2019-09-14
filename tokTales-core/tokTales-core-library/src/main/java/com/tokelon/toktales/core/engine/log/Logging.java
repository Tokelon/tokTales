package com.tokelon.toktales.core.engine.log;

import javax.inject.Inject;

public class Logging implements ILogging {


	private final ILoggerFactory loggerFactory;
	private final org.slf4j.ILoggerFactory facadeLoggerFactory;
	private final ILoggerNamer loggerNamer;

	@Inject
	public Logging(ILoggerFactory loggerFactory, org.slf4j.ILoggerFactory facadeLoggerFactory, ILoggerNamer loggerNamer) {
		this.loggerFactory = loggerFactory;
		this.facadeLoggerFactory = facadeLoggerFactory;
		this.loggerNamer = loggerNamer;
	}
	
	
	@Override
	public ILogger getLogger(String name) {
		return loggerFactory.getLogger(name);
	}

	@Override
	public ILogger getLogger(Class<?> clazz) {
		return loggerFactory.getLogger(clazz); // Use logger namer?
	}

	@Override
	public ILoggerFactory getLoggerFactory() {
		return loggerFactory;
	}


	@Override
	public org.slf4j.Logger getFacadeLogger(String name) {
		return facadeLoggerFactory.getLogger(name);
	}

	@Override
	public org.slf4j.Logger getFacadeLogger(Class<?> clazz) {
		return facadeLoggerFactory.getLogger(loggerNamer.getNameFromClass(clazz));
	}

	@Override
	public org.slf4j.ILoggerFactory getFacadeLoggerFactory() {
		return facadeLoggerFactory;
	}

}
