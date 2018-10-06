package com.tokelon.toktales.core.render.opengl;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

/** Indicates that the default value for setting error checking on GL calls should be injected.
 * <p>
 * Error checking should be enabled if the value is true, disabled if false.
 *
 */
@Qualifier
@Target({FIELD, PARAMETER, METHOD})
@Retention(RUNTIME)
public @interface GLErrorCheckingEnabled {

}
