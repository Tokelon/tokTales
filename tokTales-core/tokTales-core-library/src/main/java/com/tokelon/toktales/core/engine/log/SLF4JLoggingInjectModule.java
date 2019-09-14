package com.tokelon.toktales.core.engine.log;

import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;

public class SLF4JLoggingInjectModule extends AbstractInjectModule {


	@Override
	protected void configure() {
		super.configure();
		
		bindInGameScopeAndForNotScoped(ITokLogger.class, SLF4JTokLogger.class);
		bindInGameScopeAndForNotScoped(ITokLoggerFactory.class, SLF4JTokLoggerFactory.class);

		
		bindInGameScopeAndForNotScoped(ILogging.class, Logging.class);
		bindInGameScopeAndForNotScoped(ILoggingFactory.class, SLF4JLoggingFactory.class);
		
		bindInGameScopeAndForNotScoped(ILoggerFactory.class, SLF4JLoggerFactory.class);
		
		
		bind(org.slf4j.ILoggerFactory.class).toInstance(org.slf4j.LoggerFactory.getILoggerFactory()); // Bind as provider?
	}
	
}
