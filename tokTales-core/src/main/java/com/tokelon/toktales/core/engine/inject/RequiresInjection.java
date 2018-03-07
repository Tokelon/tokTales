package com.tokelon.toktales.core.engine.inject;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/** Indicates that the annotated type requires one or more dependencies to be passed via injection.
 * <p>
 * This annotation is applied to inform the user of a type
 * that they need to satisfy the type's dependency requirements for it to work correctly.
 * 
 */
@Documented
@Retention(SOURCE)
@Target(TYPE)
public @interface RequiresInjection {

}
