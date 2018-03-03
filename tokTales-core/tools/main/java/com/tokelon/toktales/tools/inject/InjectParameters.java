package com.tokelon.toktales.tools.inject;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/** Can be used to annotated methods that should be invoked by an {@link IParameterInjector}.
 * 
 * @see IParameterInjector
 */
@Retention(RUNTIME)
@Target(METHOD)
@Repeatable(InjectParametersContainer.class)
@Documented
public @interface InjectParameters {

	/**
	 * @return A key to indicate who should inject.
	 */
	Class<?> value() default Object.class;
	
}
