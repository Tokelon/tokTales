package com.tokelon.toktales.core.engine.log;

public class SLF4JLoggingFactory implements ILoggingFactory {


	@Override
	public ILogging create() {
		return new Logging(new SLF4JLoggerFactory(org.slf4j.LoggerFactory.getILoggerFactory()));
	}

}
