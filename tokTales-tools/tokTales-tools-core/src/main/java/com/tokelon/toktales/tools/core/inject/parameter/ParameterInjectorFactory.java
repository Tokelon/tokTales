package com.tokelon.toktales.tools.core.inject.parameter;

import java.lang.annotation.Annotation;

import com.tokelon.toktales.tools.core.inject.parameter.IParameterInjector.IParameterInjectorFactory;

public class ParameterInjectorFactory implements IParameterInjectorFactory {

	
	@Override
	public IParameterInjector create(Class<? extends Annotation> annotationClass, Object... parameters) {
		return new ParameterInjector(annotationClass, parameters);
	}

	@Override
	public IParameterInjector create(Annotation annotation, Object... parameters) {
		return new ParameterInjector(annotation, parameters);
	}

}
