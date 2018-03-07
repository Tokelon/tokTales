package com.tokelon.toktales.tools.inject;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({METHOD, TYPE})
@Documented
@interface InjectParametersContainer {
	
	InjectParameters[] value();
}
