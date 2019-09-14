package com.tokelon.toktales.core.engine.log;

interface ILoggerFactory extends org.slf4j.ILoggerFactory {


	@Override
	public ILogger getLogger(String name);
	
	public ILogger getLogger(Class<?> clazz);
	
	
	public org.slf4j.Logger getFacadeLogger(String name);
	public org.slf4j.Logger getFacadeLogger(Class<?> clazz);
	
	public org.slf4j.ILoggerFactory getFacadeLoggerFactory();
	
}
