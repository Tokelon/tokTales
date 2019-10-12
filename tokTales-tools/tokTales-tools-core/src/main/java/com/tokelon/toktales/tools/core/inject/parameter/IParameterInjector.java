package com.tokelon.toktales.tools.core.inject.parameter;

import java.lang.annotation.Annotation;

/** Can be used to inject parameters by invoking methods that are annotated in a certain way.
 * <p>
 * Which annotation should be considered can be defined by passing either a type or an instance upon initialization.
 * Annotation instances can be used to further specify a value for an annotation, which will act as a key when looking up annotations.
 * 
 * @see InjectParameters
 * @see InjectParams
 */
public interface IParameterInjector {
	
	
	/** Injects any matching parameters into the given object.
	 * <p>
	 * Notes:<br>
	 * - Superclass methods will be invoked first.<br>
	 * - For a method to be invoked it must be <i>directly</i> annotated with this injector's annotation. This also applies to overridden methods.<br>
	 * - This injector's parameters that do not match parameter types of any invoked method will be ignored.<br>
	 * - Matching parameters will be resolved in the order they have been given to this injector.<br>
	 * 
	 * 
	 * @param obj
	 * @throws ParameterInjectException In any of the following cases:<br>
	 * - If a matching parameter type can not be found.<br>
	 * - If there are methods with more parameters than given to this injector.<br>
	 * - If an invoked method throws an exception.<br>
	 */
	public void injectInto(Object object);
	
	
	
	public interface IParameterInjectorFactory {
		
		/** Creates a new injector for the given annotationClass and parameters.
		 * <p>
		 * <b>Be aware:</b> that for annotations with custom values you should always use {@link #create(Annotation, Object...)},
		 * otherwise the injector won't be able to consider your custom values and will invoke all methods annotated with the given class.
		 * The only exception to this is {@link InjectParameters}, which will be recognized and checked for it's key.
		 * <p>
		 * Note that each parameter will only be injected once per method.
		 * 
		 * 
		 * @param annotationClass The annotation class which marks the methods that should be invoked.
		 * @param parameters The parameters that should be injected, in the order they should be injected.
		 * 
		 * @return A new parameter injector.
		 * @throws NullPointerException If any parameters are null or annotationClass is null.
		 */
		public IParameterInjector create(Class<? extends Annotation> annotationClass, Object... parameters);
		
		
		/** Creates a new injector for the given annotation and parameters.
		 * <p>
		 * Note that each parameter will only be injected once per method.
		 * 
		 * 
		 * @param annotation The annotation instance being equal to the annotations which mark the methods that should be invoked.
		 * @param parameters The parameters that should be injected, in the order they should be injected.
		 * 
		 * @return A new parameter injector.
		 * @throws NullPointerException If any parameters are null or annotation is null.
		 * @see InjectParams InjectParams for creating annotation instances.
		 */
		public IParameterInjector create(Annotation annotation, Object... parameters);
	}

}