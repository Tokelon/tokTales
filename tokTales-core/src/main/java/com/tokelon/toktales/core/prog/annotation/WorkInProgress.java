package com.tokelon.toktales.core.prog.annotation;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/** Indicates that the target is not finished or polished.<br>
 * Targets with this indicator may not work as intended or cause bugs.
 * <p>
 * The value field can be used for comments.
 */
@Documented
@Retention(SOURCE)
@Target({ TYPE, FIELD, METHOD, CONSTRUCTOR, LOCAL_VARIABLE, PACKAGE })
public @interface WorkInProgress {

	String value() default "";
}
