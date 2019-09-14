package com.tokelon.toktales.core.engine.log;

import javax.inject.Inject;

public class SLF4JLoggerFactory implements ILoggerFactory {


	private final org.slf4j.ILoggerFactory facadeLoggerFactory;

	@Inject
	public SLF4JLoggerFactory(org.slf4j.ILoggerFactory facadeLoggerFactory) {
		this.facadeLoggerFactory = facadeLoggerFactory;
	}
	
	
	protected String getLoggerNameFromClass(Class<?> clazz) {
		return clazz.getName();
	}
	
	
	@Override
	public ILogger getLogger(String name) {
		return new SLF4JLogger(facadeLoggerFactory.getLogger(name));
	}

	@Override
	public ILogger getLogger(Class<?> clazz) {
		return new SLF4JLogger(facadeLoggerFactory.getLogger(getLoggerNameFromClass(clazz)));
	}


	@Override
	public org.slf4j.Logger getFacadeLogger(String name) {
		return facadeLoggerFactory.getLogger(name);
	}

	@Override
	public org.slf4j.Logger getFacadeLogger(Class<?> clazz) {
		return facadeLoggerFactory.getLogger(getLoggerNameFromClass(clazz));
	}
	
	@Override
	public org.slf4j.ILoggerFactory getFacadeLoggerFactory() {
		return facadeLoggerFactory;
	}

}
