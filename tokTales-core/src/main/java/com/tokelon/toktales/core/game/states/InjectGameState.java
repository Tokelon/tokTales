package com.tokelon.toktales.core.game.states;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.tokelon.toktales.tools.inject.InjectParameters;

/** When applied to methods, indicates that the method should be invoked and an {@link IGameState} should be injected.
 * <p>
 * When applied to types, indicates that an {@link IGameState} dependency must be injected for it to work correctly.
 * <p>
 * When used the annotation should also be applied to the enclosing type to indicate the dependency requirement to the user.
 * 
 * @see InjectParameters
 */
@Documented
@Retention(RUNTIME)
@Target({METHOD, TYPE})
public @interface InjectGameState {

}
