package com.tokelon.toktales.tools.inject;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class ParameterInjector implements IParameterInjector {


	private final Class<? extends Annotation> targetAnnotationClass;
	private final Annotation targetAnnotation;
	private final Object[] parameters;

	public ParameterInjector(Class<? extends Annotation> annotationClass, Object... parameters) {
		if(annotationClass == null) {
			throw new NullPointerException();
		}
		
		this.targetAnnotationClass = annotationClass;
		this.parameters = parameters;
		
		this.targetAnnotation = null;
	}

	public ParameterInjector(Annotation annotation, Object... parameters) {
		if(annotation == null) {
			throw new NullPointerException();
		}
		
		this.targetAnnotation = annotation;
		this.parameters = parameters;
		
		this.targetAnnotationClass = null;
	}


	@Override
	public void injectInto(Object object) {
		injectIntoClass(object, object.getClass(), new HashSet<>());
	}

	
	private void injectIntoClass(Object object, Class<?> clazz, HashSet<Method> invokedMethods) throws ParameterInjectException {
		// Iterate through all superclasses and invoke their methods first, using recursion
		Class<?> superclass = clazz.getSuperclass();
		if(superclass != null && superclass != Object.class) {
			injectIntoClass(object, superclass, invokedMethods);
		}
		
		
		Method[] classMethods;
		try {
			classMethods = clazz.getDeclaredMethods();
		} catch (SecurityException e) {
			// Not much we can do here
			System.err.println(String.format(
					"Error injecting into object %s, could not get declared methods due to SecurityException: %s",
					object.getClass(),
					e.getMessage()
					));
			e.printStackTrace();
			return;
		}
		
		for(Method method: classMethods) {
			// Check if method static? Do not inject into static methods?
			
			if(invokedMethods.contains(method)) {
				// Skip already invoked methods
				continue;
			}
			
			
			// Inherited annotations will be ignored here
			Annotation[] methodAnnotations = method.getDeclaredAnnotationsByType(getTargetAnnotationType());
			
			boolean shouldInjectIntoMethod = false;
			for(Annotation annotation: methodAnnotations) {
				if(matchesTargetAnnotation(annotation)) {
					shouldInjectIntoMethod = true;
					break;
				}
			}
			
			if(shouldInjectIntoMethod) {
				ArrayList<Object> args = new ArrayList<>();
				
				Class<?>[] parameterTypes = method.getParameterTypes(); // In declaration order
				if(parameterTypes.length > parameters.length) {
					// More parameters requested than supplied
					throw new ParameterInjectException(String.format(
							"Not enough parameters (%d) to inject into method %s (%d) of object %s",
							parameters.length,
							method.getName(),
							parameterTypes.length,
							object.getClass()
							));
				}
				
				
				// We will use this as our working copy, and remove (null) parameters when they are used
				Object[] availableParameters = Arrays.copyOf(parameters, parameters.length);
				
				for(Class<?> parameterType: parameterTypes) {
					// Find one distinct parameter for each type
					
					Object targetParameter = null;
					for(int i = 0; i < availableParameters.length; i++) {
						
						Object parameter = availableParameters[i];
						if(parameter != null && parameterType.isInstance(parameter)) { // parameter can be null if used already
							targetParameter = parameter;
							availableParameters[i] = null; // remove parameter from available
							break;
						}
					}
					
					if(targetParameter == null) { // No compatible parameter was found
						throw new ParameterInjectException(String.format(
								"No matching parameter found for type %s while injecting into method %s of object %s",
								parameterType,
								method.getName(),
								object.getClass()
								));
					}
					else {
						args.add(targetParameter);
					}
				}
				
				
				try {
					// We need to inject into inaccessible methods as well
					method.setAccessible(true);
					
					// This will still call an overriding method even if it's discovered in the superclass first
					method.invoke(object, args.toArray());
				} catch (IllegalAccessException e) {
					// Should not happen, we set accessible to true
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// Should not happen, we match the types
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// forward any exceptions thrown by the method
					throw new ParameterInjectException(e.getTargetException());
				}
				
				
				// Lastly add the method, so it does not get invoked again
				invokedMethods.add(method);
			}
		}
		
	}
	
	private Class<? extends Annotation> getTargetAnnotationType() {
		Class<? extends Annotation> result;
		if(targetAnnotationClass != null) {
			result = targetAnnotationClass;
		}
		else if(targetAnnotation != null) {
			result = targetAnnotation.annotationType();
		}
		else {
			assert false : "This should never happen";
			throw new RuntimeException();
		}
		
		return result;
	}
	
	private boolean matchesTargetAnnotation(Annotation annotation) {
		boolean matches;
		if(targetAnnotationClass != null) {
			matches = targetAnnotationClass == annotation.annotationType(); // Compare with annotationType() not getClass()
			
			// Special check for InjectParameters
			if(matches && annotation instanceof InjectParameters) {
				matches = ((InjectParameters) annotation).value() == Object.class;
			}
		}
		else if(targetAnnotation != null) {
			// order could be important - compare ours to theirs
			matches = targetAnnotation.equals(annotation); // Compare with equals to make sure values are considered
		}
		else {
			assert false : "Again, this should never happen";
			throw new RuntimeException();
		}
		
		return matches;
	}

}
