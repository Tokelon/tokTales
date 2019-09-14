package com.tokelon.toktales.core.engine.log;

public class LoggingManager {
	// TODO: Add facade methods?


	protected LoggingManager() {}

	private static ILoggingFactory loggingFactory = new SLF4JLoggingFactory(new DefaultLoggerNamer());
	private static ILogging logging = loggingFactory.create();
	
	
	public static ILogging getLogging() {
		return logging;
	}

	public static ILoggingFactory getLoggingFactory() {
		return loggingFactory;
	}
	
	
	public static ILogger getLogger(String name) {
		return logging.getLogger(name);
	}
	
	public static ILogger getLogger(Class<?> clazz) {
		return logging.getLogger(clazz);
	}
	
	public static ILoggerFactory getLoggerFactory() {
		return logging.getLoggerFactory();
	}
	
	
	public static class Setter {
		protected Setter() {}
		
		public static void setLogging(ILoggingFactory loggingFactory) {
			if(loggingFactory == null) {
				throw new NullPointerException();
			}
			
			LoggingManager.loggingFactory = loggingFactory;
			LoggingManager.logging = loggingFactory.create();
		}
	}

}
