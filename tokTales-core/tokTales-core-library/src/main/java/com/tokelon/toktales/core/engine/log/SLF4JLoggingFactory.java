package com.tokelon.toktales.core.engine.log;

import javax.inject.Inject;

public class SLF4JLoggingFactory implements ILoggingFactory {


	private final ILoggerNamer loggerNamer;

	@Inject
	public SLF4JLoggingFactory(ILoggerNamer loggerNamer) { // Use provider?
		this.loggerNamer = loggerNamer;
	}

	@Override
	public ILogging create() {
		return new Logging(
				new SLF4JLoggerFactory(org.slf4j.LoggerFactory.getILoggerFactory(), loggerNamer),
				org.slf4j.LoggerFactory.getILoggerFactory(),
				loggerNamer
		);
	}

}
