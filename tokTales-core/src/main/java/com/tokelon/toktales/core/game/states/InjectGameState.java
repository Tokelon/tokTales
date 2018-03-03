package com.tokelon.toktales.core.game.states;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/** Used to annotate methods into which an {@link IGameState} should be injected.
 * 
 */
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface InjectGameState {

}
