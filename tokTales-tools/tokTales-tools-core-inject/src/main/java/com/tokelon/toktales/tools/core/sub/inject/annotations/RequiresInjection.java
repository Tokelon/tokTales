package com.tokelon.toktales.tools.core.sub.inject.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/** Indicates that the annotated type requires one or more dependencies to be passed via injection.
 * <p>
 * This annotation is applied to inform the user of a type,
 * that they need to satisfy the type's dependency requirements for it to work correctly.
 * This is especially important for classes that use method or field injection but no constructor injection. For all others it is just nice to have.
 */
@Documented
@Retention(SOURCE)
@Target(TYPE)
public @interface RequiresInjection {

}
