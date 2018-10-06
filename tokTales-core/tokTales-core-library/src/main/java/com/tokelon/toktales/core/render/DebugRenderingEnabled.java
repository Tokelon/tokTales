package com.tokelon.toktales.core.render;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

/** Indicates that the default value for setting debug rendering should be injected.
 * <p>
 * Debug rendering should be enabled if the value is true, disabled if false.
 *
 */
@Qualifier
@Target({FIELD, PARAMETER, METHOD})
@Retention(RUNTIME)
public @interface DebugRenderingEnabled {

}
