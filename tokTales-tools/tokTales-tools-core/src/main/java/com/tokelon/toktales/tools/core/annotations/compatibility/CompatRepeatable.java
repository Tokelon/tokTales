package com.tokelon.toktales.tools.core.annotations.compatibility;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/** Compatibility version of java.lang.annotation.Repeatable.
 * <p>
 * Make sure to use this <b>in addition</b> to Repeatable.
 * <p>
 * This annotation exists for platforms on which Repeatable is not supported.
 * On those platforms it will be used at runtime to identify the container annotation for repeatable annotations.
 * <p>
 * Known platforms this is needed on: Android (before API level 24).
 */
@Documented
@Retention(RUNTIME)
@Target(ANNOTATION_TYPE)
public @interface CompatRepeatable {
	
	Class<? extends Annotation> value();
}
