package com.tokelon.toktales.tools.inject;

import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ParameterInjector implements IParameterInjector {

	
	private final Class<? extends Annotation> targetAnnotationContainerClass;
	private final Class<? extends Annotation> targetAnnotationType;

	private final Class<? extends Annotation> targetAnnotationClass;
	private final Annotation targetAnnotation;
	
	private final Object[] parameters;

	public ParameterInjector(Class<? extends Annotation> annotationClass, Object... parameters) {
		if(annotationClass == null) {
			throw new NullPointerException();
		}

		checkParameters(parameters);
		
		this.targetAnnotationClass = annotationClass;
		this.parameters = parameters;
		this.targetAnnotation = null;
		
		// Call these after setting the values
		this.targetAnnotationType = getTargetAnnotationType();
		this.targetAnnotationContainerClass = getPossibleContainerAnnotationClass();
	}

	public ParameterInjector(Annotation annotation, Object... parameters) {
		if(annotation == null) {
			throw new NullPointerException();
		}
		
		checkParameters(parameters);
		
		this.targetAnnotation = annotation;
		this.parameters = parameters;
		this.targetAnnotationClass = null;
		
		// Call these after setting the values
		this.targetAnnotationType = getTargetAnnotationType();
		this.targetAnnotationContainerClass = getPossibleContainerAnnotationClass();
	}
	
	private void checkParameters(Object[] parameters) {
		if(parameters == null) {
			throw new NullPointerException();
		}
		
		for(int i = 0; i < parameters.length; i++) {
			if(parameters[i] == null) {
				throw new NullPointerException(String.format("Injection parameter at index %d is null", i));
			}
		}
	}

	
	@Override
	public void injectInto(Object object) {
		injectIntoClassRecursive(object, object.getClass(), new HashSet<>(), new ArrayList<>());
	}

	
	private void injectIntoClassRecursive(Object object, Class<?> clazz, HashSet<Method> checkedMethods, ArrayList<Method> collectedMethods) throws ParameterInjectException {
		/* Iterate through methods of this class, collect the ones that should be invoked,
		 * then do the same for the superclass using recursion
		 */
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
			
			if(containsInheritedMethod(checkedMethods, method)) {
				// Skip already checked methods
				continue;
			}
						
			
			List<Annotation> methodAnnotations = getInjectMethodAnnotations(method);
			
			boolean shouldInjectIntoMethod = false;
			for(Annotation annotation: methodAnnotations) {
				if(matchesTargetAnnotation(annotation)) {
					shouldInjectIntoMethod = true;
					break;
				}
			}
			
			if(shouldInjectIntoMethod) {
				collectedMethods.add(method);
			}
		}
		
		/* Add that classMethods have been checked, so they're no considered in superclass
		 * Add them at the end to avoid checking same class methods against each other
		 */
		checkedMethods.addAll(Arrays.asList(classMethods));
		
		
		Class<?> superclass = clazz.getSuperclass();
		if(superclass == null || superclass == Object.class) {
			/* Top of the class hierarchy,
			 * invoke collected methods in the order they were collected
			 */
			for(Method method: collectedMethods) {
				injectIntoMethod(method, object);
			}
		}
		else {
			injectIntoClassRecursive(object, superclass, checkedMethods, collectedMethods);
		}
	}
	
	private void injectIntoMethod(Method method, Object object) {
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
	
	
	private List<Annotation> getInjectMethodAnnotations(Method method) {
		ArrayList<Annotation> injectAnnotations = new ArrayList<>();
		
		Annotation[] methodAnnotations = null;
		try {
			// Try to find the method we want to use
			method.getClass().getMethod("getDeclaredAnnotationsByType", Class.class);
			
			// If we get past here hopefully we can safely use the method
			methodAnnotations = method.getDeclaredAnnotationsByType(targetAnnotationType);
			for(Annotation methodAnnotation: methodAnnotations) {
				injectAnnotations.add(methodAnnotation);
			}
		} catch (NoSuchMethodException | SecurityException ex) {
			/* Some methods for getting annotations introduced in Java 8 do not work on this platform.
			 * This is mostly an Android issue (before API level 24).
			 * 
			 * To ensure compatibility we use a more complicated way of getting the annotations below.
			 */
		}
		
		
		if(methodAnnotations == null) {
			// Failed to get annotations, do things manually (compatibility mode)
			
			Annotation[] annotations = method.getDeclaredAnnotations(); // Inherited annotations will be ignored here
			for(Annotation annotation: annotations) {

				if(annotation.annotationType() == targetAnnotationType) {
					// This is simple, just add the annotation
					injectAnnotations.add(annotation);
				}
				else if(annotation.annotationType() == targetAnnotationContainerClass) {
					/* Here it gets complicated.
					 * Container annotations get checked by the compiler for a value method, that returns an array of the annotation that is being repeated.
					 * We need this array but we have no safe way of accessing it. Our only option is reflection.
					 */
					try {
						Method valueMethod = annotation.getClass().getMethod("value");

						valueMethod.setAccessible(true);
						Object valueResult = valueMethod.invoke(annotation);

						Annotation[] repeatedAnnotations = (Annotation[]) valueResult; // We can only try casting and catch the exception if it fails
						for(Annotation repeatedAnnotation: repeatedAnnotations) {
							injectAnnotations.add(repeatedAnnotation);
						}
					} catch (NoSuchMethodException e) {
						// The compiler should ensure this method exists
						System.err.println(String.format("Failed to find value() method for repeatable annotation of type %s: %s", targetAnnotationContainerClass, e.getMessage()));
					} catch (SecurityException | IllegalAccessException e) {
						System.err.println(String.format("Failed to access value() method for repeatable annotation of type %s: %s", targetAnnotationContainerClass, e.getMessage()));
					} catch (IllegalArgumentException | InvocationTargetException e) {
						System.err.println(String.format("Failed to invoke value() method for repeatable annotation of type %s: %s", targetAnnotationContainerClass, e.getMessage()));
					} catch (ClassCastException e) {
						// This means either the compiler screwed up really hard, or we did when checking the repeatable annotation
						System.err.println(String.format("Failed to cast value() method for repeatable annotation of type %s: %s", targetAnnotationContainerClass, e.getMessage()));
					}
				}
			}
		}
		
		return injectAnnotations;
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
	
	
	private Class<? extends Annotation> getPossibleContainerAnnotationClass() {
		Class<? extends Annotation> result = null;
		
		boolean repeatableSupported;
		try {
			// Try to find Repeatable
			Class.forName("java.lang.annotation.Repeatable");
			
			// If we get past here hopefully we can safely use the annotation
			Repeatable possibleRepeatableAnnotation = targetAnnotationType.getAnnotation(Repeatable.class);
			if(possibleRepeatableAnnotation != null) {
				result = possibleRepeatableAnnotation.value();
			}
			
			repeatableSupported = true;
		} catch (ClassNotFoundException e) {
			/* Repeatable is not supported on this platform.
			 * 
			 * Again this is mostly an Android issue (before API level 24),
			 * and the only way to work around this is by using our custom CompatRepeatable to define the container annotation. 
			 */
			repeatableSupported = false;
		}
		
		if(result == null) {
			// If lookup with Repeatable failed, try with CompatRepeatable as well
			CompatRepeatable possibleCompatAnnotation = targetAnnotationType.getAnnotation(CompatRepeatable.class);
			if(possibleCompatAnnotation != null) {
				result = possibleCompatAnnotation.value();
			}
		}
	
		
		if(result == null && !repeatableSupported) {
			// If lookup with Repeatable failed and there was no result for CompatRepeatable, output warning
			System.out.println(String.format(
					"(ParameterInjector) Warning: This platform does not support java.lang.annotation.Repeatable. "
					+ "Make sure to use CompatRepeatable on repeatable annotations. If you already do, you can ignore this message."
			));	
		}
		
		return result;
	}
	

	private static boolean containsInheritedMethod(Set<Method> methodSet, Method baseMethod) {
		for(Method methodInSet: methodSet) {
			if(isBaseMethodOfOtherMethod(baseMethod, methodInSet)) {
				return true;
			}
		}
		
		return false;
	}
	
	private static boolean isBaseMethodOfOtherMethod(Method baseMethod, Method otherMethod) {
		if(!otherMethod.getName().equals(baseMethod.getName())) {
			return false;
		}
		if(!Arrays.equals(otherMethod.getParameterTypes(), baseMethod.getParameterTypes())) {
			return false;
		}
		if(!baseMethod.getDeclaringClass().isAssignableFrom(otherMethod.getDeclaringClass())) {
			return false;
		}
		if(Modifier.isPrivate(baseMethod.getModifiers()) || Modifier.isPrivate(otherMethod.getModifiers())) {
			return false;
		}
		
		return true;
	}

}
