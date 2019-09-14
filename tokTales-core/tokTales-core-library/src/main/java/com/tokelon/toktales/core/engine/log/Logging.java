package com.tokelon.toktales.core.engine.log;

import javax.inject.Inject;

public class Logging implements ILogging {


	private final ILoggerFactory loggerFactory;

	@Inject
	public Logging(ILoggerFactory loggerFactory) {
		this.loggerFactory = loggerFactory;
	}
	
	
	@Override
	public ILogger getLogger(String name) {
		return loggerFactory.getLogger(name);
	}

	@Override
	public ILogger getLogger(Class<?> clazz) {
		return loggerFactory.getLogger(clazz);
	}

	@Override
	public ILoggerFactory getLoggerFactory() {
		return loggerFactory;
	}


	@Override
	public org.slf4j.Logger getFacadeLogger(String name) {
		return loggerFactory.getFacadeLogger(name);
	}

	@Override
	public org.slf4j.Logger getFacadeLogger(Class<?> clazz) {
		return loggerFactory.getFacadeLogger(clazz);
	}

	@Override
	public org.slf4j.ILoggerFactory getFacadeLoggerFactory() {
		return loggerFactory.getFacadeLoggerFactory();
	}

}
