package com.tokelon.toktales.tools.core.inject.parameter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.tokelon.toktales.tools.core.inject.parameter.InheritedParameterInjectionTargets.InjectBaseTarget.KeyEqualMethodsPrivate;
import com.tokelon.toktales.tools.core.inject.parameter.InheritedParameterInjectionTargets.InjectBaseTarget.KeyWithAnnotationGeneric;
import com.tokelon.toktales.tools.core.inject.parameter.InheritedParameterInjectionTargets.InjectBaseTarget.KeyWithoutAnnotationGeneric;
import com.tokelon.toktales.tools.core.inject.parameter.InheritedParameterInjectionTargets.InjectInheritTarget;
import com.tokelon.toktales.tools.core.inject.parameter.InjectedObjects.InjectedObject;

public class ParameterInjectorInheritedInjectionTest {

	
	private InjectInheritTarget injectionTarget;
	
	private InjectedObject injectedObject;

	private ParameterInjector unkeyedInjector;

	
	@Before
	public void before() {
		injectionTarget = new InjectInheritTarget();
		injectedObject = new InjectedObject();
		
		unkeyedInjector = new ParameterInjector(InjectParameters.class, injectedObject);
	}
	
	
	
	@Test
	public void injectInto_BaseMethod_ShouldSucceed() {
		unkeyedInjector.injectInto(injectionTarget);
		
		assertSame(injectedObject, injectionTarget.injectedObjectInjectBase);
	}
	
	
	@Test
	public void injectInto_InheritedMethodWithAnnotation_ShouldInjectBaseAndInherited() {
		unkeyedInjector.injectInto(injectionTarget);
		
		assertSame(injectedObject, injectionTarget.injectedObjectOverrideWithAnnotation);
		assertSame(injectedObject, injectionTarget.injectedObjectInheritedOverrideWithAnnotation);
	}
	
	@Test
	public void injectInto_InheritedMethodWithAnnotationGeneric_ShouldInjectBaseAndInherited() {
		List<String> obj = new ArrayList<>();
		ParameterInjector injector = new ParameterInjector(InjectParams.forKey(KeyWithAnnotationGeneric.class), obj);
		injector.injectInto(injectionTarget);
		
		assertSame(obj, injectionTarget.injectedObjectOverrideWithAnnotationGeneric);
		assertSame(obj, injectionTarget.injectedObjectInheritedOverrideWithAnnotationGeneric);
	}

	
	@Test
	public void injectInto_InheritedMethodWithoutAnnotation_ShouldNotInject() {
		unkeyedInjector.injectInto(injectionTarget);
		
		assertNull(injectionTarget.injectedObjectOverrideWithoutAnnotation);
		assertNull(injectionTarget.injectedObjectInheritedOverrideWithoutAnnotation);
	}
	
	@Test
	public void injectInto_InheritedMethodWithoutAnnotationGeneric_ShouldNotInject() {
		List<String> obj = new ArrayList<>();
		ParameterInjector injector = new ParameterInjector(InjectParams.forKey(KeyWithoutAnnotationGeneric.class), obj);
		injector.injectInto(injectionTarget);
		
		assertNull(injectionTarget.injectedObjectOverrideWithoutAnnotationGeneric);
		assertNull(injectionTarget.injectedObjectInheritedOverrideWithoutAnnotationGeneric);
	}

	
	@Test
	public void injectInto_EqualMethods_ShouldInjectBaseAndInherited() {
		ParameterInjector injector = new ParameterInjector(InjectParams.forKey(KeyEqualMethodsPrivate.class), injectedObject);
		injector.injectInto(injectionTarget);
		
		assertSame(injectedObject, injectionTarget.injectedObjectEqualMethods);
		assertSame(injectedObject, injectionTarget.injectedObjectInheritedEqualMethods);
	}
	
	
	@Test
	public void injectInto_InheritedMethod_OnlyInvokesOnce() {
		unkeyedInjector.injectInto(injectionTarget);
		
		assertEquals(1, injectionTarget.injectBaseOverrideCallCount);
	}
	
}
