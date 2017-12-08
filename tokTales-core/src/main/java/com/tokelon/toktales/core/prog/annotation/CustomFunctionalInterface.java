package com.tokelon.toktales.core.prog.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/** An informative annotation used to indicate that a custom interface is intended to be a functional interface.
 * <br><br>
 * Note that compilers will not generate any errors if requirements for an annotated type are not met.
 * This annotation is intended to be used alongside FuntionalInterface, or as a replacement when backwards compatibility is needed.
 * 
 *
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface CustomFunctionalInterface {
    
}
