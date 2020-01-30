package com.tokelon.toktales.core.game.state.scene;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.tokelon.toktales.tools.core.inject.parameter.InjectParameters;

/** When applied to methods, indicates that the method should be invoked and an {@link IGameScene} should be injected.
 * <p>
 * When applied to types, indicates that an {@link IGameScene} dependency must be injected for it to work correctly.
 * <p>
 * When used the annotation should also be applied to the enclosing type to indicate the dependency requirement to the user.
 * <p>
 * A method is valid if it has one parameter of type or subtype of IGameScene. This will usually look something like:
 * <pre>
 * {@code @InjectGamescene}
 * protected void injectGamescene(IGameScene gamescene) {
 *     this.gamescene = gamescene; // assign the injected parameter
 * }
 * </pre>
 * It is important to note that the gamescene parameter can be a subtype of IGameScene,
 * however injection will fail if an incompatible type is supplied.
 * In practice this should not be a problem as long as dependencies are only passed to those gamescene types which they require.
 * 
 * @see InjectParameters
 */
@Documented
@Retention(RUNTIME)
@Target({METHOD, TYPE})
public @interface InjectGameScene {

	//Class<? extends IGameState> value(); // TODO: Add class key and make repeatable?
}
