package com.tokelon.toktales.tools.inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.tokelon.toktales.tools.inject.InjectedObjects.InjectedObject;
import com.tokelon.toktales.tools.inject.InjectedObjects.InjectedObjectA;
import com.tokelon.toktales.tools.inject.InjectedObjects.InjectedObjectB;
import com.tokelon.toktales.tools.inject.SingleParameterInjectionTarget.KeyCaseA;
import com.tokelon.toktales.tools.inject.SingleParameterInjectionTarget.KeyCaseB;
import com.tokelon.toktales.tools.inject.SingleParameterInjectionTarget.KeyException;
import com.tokelon.toktales.tools.inject.SingleParameterInjectionTarget.KeyMethodOverload;
import com.tokelon.toktales.tools.inject.SingleParameterInjectionTarget.KeyNoParameters;

public class ParameterInjectorSingleParameterTest {
	
	
	private SingleParameterInjectionTarget injectionTarget;
	private InjectedObject injectedObject;
	
	private ParameterInjector unkeyedInjector;
	private ParameterInjector keyedInjector;
	
	
	@Before
	public void before() {
		injectionTarget = new SingleParameterInjectionTarget();
		injectedObject = new InjectedObject();
		unkeyedInjector = new ParameterInjector(InjectParameters.class, injectedObject);
		keyedInjector = new ParameterInjector(InjectParams.forKey(ParameterInjectorSingleParameterTest.class), injectedObject);
	}
	
	
	@Test(expected = NullPointerException.class)
	public void parameterInjector_CreationWithNullParameter_ShouldThrowException() {
		@SuppressWarnings("unused")
		ParameterInjector injector = new ParameterInjector(InjectParams.forKey(Object.class), injectedObject, null);
	}
	
	@Test(expected = NullPointerException.class)
	public void parameterInjector_CreationWithNullArray_ShouldThrowException() {
		@SuppressWarnings("unused")
		ParameterInjector injector = new ParameterInjector(InjectParams.forKey(Object.class), null);
	}
	
	
	@Test
	public void injectInto_ThrowsException_ShouldThrowException() {
		ParameterInjector injector = new ParameterInjector(InjectParams.forKey(KeyException.class), injectedObject);
		
		try {
			injector.injectInto(injectionTarget);
			fail("Expected ParameterInjectException");
		} catch(ParameterInjectException e) {
			assertEquals("I threw this", e.getCause().getMessage());
		}
		
		assertNull(injectionTarget.injectedObjectMissing);
	}
	

	@Test(expected = ParameterInjectException.class)
	public void injectInto_ParametersMissing_ShouldThrowException() {
		ParameterInjector injector = new ParameterInjector(InjectParams.forKey(KeyNoParameters.class));
		injector.injectInto(injectionTarget);
	}
	
	
	@Test
	public void injectInto_OverloadMethod_ShouldInvokeAllOverloads() {
		InjectedObjectA a = new InjectedObjectA();
		InjectedObjectB b = new InjectedObjectB();
		
		ParameterInjector injector = new ParameterInjector(InjectParams.forKey(KeyMethodOverload.class), a, b);
		injector.injectInto(injectionTarget);
		
		assertSame(a, injectionTarget.injectedObjectMethodOverloadA);
		assertSame(b, injectionTarget.injectedObjectMethodOverloadB);
	}
	
	
	@Test
	public void injectInto_NoParameters_ShouldSucceed() {
		unkeyedInjector.injectInto(injectionTarget);
		
		assertTrue(injectionTarget.injectNothingWasCalled);
	}
	
	
	@Test
	public void injectInto_MultipleKeysCaseA_ShouldSucceed() {
		ParameterInjector injector = new ParameterInjector(InjectParams.forKey(KeyCaseA.class), injectedObject);
		injector.injectInto(injectionTarget);
		
		assertSame(injectedObject, injectionTarget.injectedObjectMultipleKeys);
	}
	@Test
	public void injectInto_MultipleKeysCaseB_ShouldSucceed() {
		ParameterInjector injector = new ParameterInjector(InjectParams.forKey(KeyCaseB.class), injectedObject);
		injector.injectInto(injectionTarget);
		
		assertSame(injectedObject, injectionTarget.injectedObjectMultipleKeys);
	}
	
	
	
	@Test
	public void injectInto_WithKey_ShouldIgnoreDefaultKeyed() {
		keyedInjector.injectInto(injectionTarget);
		assertNull(injectionTarget.injectedObjectDefaultKey);
	}
	
	@Test
	public void injectInto_WithKey_ShouldIgnoreNonKeyed() {
		keyedInjector.injectInto(injectionTarget);
		assertNull(injectionTarget.injectedObjectPublic);
	}
	
	@Test
	public void injectInto_WithDefaultKey_ShouldIgnoreOtherKeyed() {
		ParameterInjector injector = new ParameterInjector(InjectParams.forKey(Object.class), injectedObject);
		injector.injectInto(injectionTarget);
		
		assertNull(injectionTarget.injectedObjectKeyed);
	}
	
	
	@Test
	public void injectInto_WithKey_ShouldSucceed() {
		keyedInjector.injectInto(injectionTarget);
		assertSame(injectedObject, injectionTarget.injectedObjectKeyed);
	}
	
	@Test
	public void injectInto_WithDuplicateKey_ShouldSucceed() {
		keyedInjector.injectInto(injectionTarget);
		assertSame(injectedObject, injectionTarget.injectedObjectKeyedDuplicate);
	}
	
	@Test
	public void injectInto_WithDefaultKey_ShouldInvokeUnkeyedMethods() {
		ParameterInjector injector = new ParameterInjector(InjectParams.forKey(Object.class), injectedObject);
		injector.injectInto(injectionTarget);
		
		assertSame(injectedObject, injectionTarget.injectedObjectPublic);
	}


	@Test
	public void injectInto_Unkeyed_ShouldInvokeDefaultKeyedMethods() {
		unkeyedInjector.injectInto(injectionTarget);
		assertSame(injectedObject, injectionTarget.injectedObjectDefaultKey);
	}
	
	@Test
	public void injectInto_Unkeyed_ShouldInvokeAllUnkeyedMethods() {
		unkeyedInjector.injectInto(injectionTarget);
		assertSame(injectedObject, injectionTarget.injectedObjectPublic);
		assertSame(injectedObject, injectionTarget.injectedObjectProtected);
		assertSame(injectedObject, injectionTarget.injectedObjectPackage);
		assertSame(injectedObject, injectionTarget.injectedObjectPrivate);
	}
	
	@Test
	public void injectInto_PublicMethod_ShouldSucceed() {
		unkeyedInjector.injectInto(injectionTarget);
		assertSame(injectedObject, injectionTarget.injectedObjectPublic);
	}
	@Test
	public void injectInto_ProtectedMethod_ShouldSucceed() {
		unkeyedInjector.injectInto(injectionTarget);
		assertSame(injectedObject, injectionTarget.injectedObjectProtected);
	}
	@Test
	public void injectInto_PackageMethod_ShouldSucceed() {
		unkeyedInjector.injectInto(injectionTarget);
		assertSame(injectedObject, injectionTarget.injectedObjectPackage);
	}
	@Test
	public void injectInto_PrivateMethod_ShouldSucceed() {
		unkeyedInjector.injectInto(injectionTarget);
		assertSame(injectedObject, injectionTarget.injectedObjectPrivate);
	}
	
	
}
