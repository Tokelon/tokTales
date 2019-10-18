package com.tokelon.toktales.core.engine.inject.scope;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.inject.Scope;

@Target({TYPE, METHOD})
@Retention(RUNTIME)
@Scope // @ScopeAnnotation
public @interface EngineScoped {
	
}