package com.tokelon.toktales.tools.core.annotations.condition;

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

/** Indicates that the target has not been maintained and that there is reason to believe it has no kept up with logic changes.<br>
 * Targets with this indicator may cause bugs or runtime exceptions.
 * <p>
 * The value field can be used for comments.
 */
@Documented
@Retention(SOURCE)
@Target({ TYPE, FIELD, METHOD, CONSTRUCTOR, LOCAL_VARIABLE, PACKAGE })
public @interface Unmaintained {

	String value() default "";
}
