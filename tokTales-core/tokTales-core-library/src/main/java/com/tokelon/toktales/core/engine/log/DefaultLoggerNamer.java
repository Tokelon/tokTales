package com.tokelon.toktales.core.engine.log;

public class DefaultLoggerNamer implements ILoggerNamer {


	@Override
	public String getNameFromClass(Class<?> clazz) {
		return clazz.getName();
	}

}
