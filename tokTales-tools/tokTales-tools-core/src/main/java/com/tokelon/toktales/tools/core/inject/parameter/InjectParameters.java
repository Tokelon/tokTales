package com.tokelon.toktales.tools.core.inject.parameter;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.tokelon.toktales.tools.core.annotations.CompatRepeatable;

/** Can be used to annotated methods that should be invoked by an {@link IParameterInjector}.
 * <p>
 * When applied to a type, the purpose is purely informative and will be ignored by injectors.
 * <p>
 * When applied to a method, the annotation should also be applied to the enclosing type to indicate the injection requirement to the user.
 * When using a class key, the key should be mirrored in a type annotation.
 * <p>
 * Types using this annotation should document their required dependencies.
 * <p>
 * As a convention, methods with this annotation may be prefixed with <i>"inject"</>.
 * 
 * @see IParameterInjector
 */
@Retention(RUNTIME)
@Target({METHOD, TYPE})
@Repeatable(InjectParametersContainer.class)
@CompatRepeatable(InjectParametersContainer.class)
@Documented
public @interface InjectParameters {

	/**
	 * @return A key to indicate who should inject.
	 */
	Class<?> value() default Object.class;
	
}
