package com.tokelon.toktales.tools.core.inject;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.tokelon.toktales.tools.core.inject.InjectedObjects.InjectedObjectA;
import com.tokelon.toktales.tools.core.inject.InjectedObjects.InjectedObjectB;
import com.tokelon.toktales.tools.core.inject.InjectedObjects.InjectedObjectC;
import com.tokelon.toktales.tools.core.inject.MultiParameterInjectionTarget.KeyAdditionalParameters;
import com.tokelon.toktales.tools.core.inject.MultiParameterInjectionTarget.KeyDuplicateParameters;
import com.tokelon.toktales.tools.core.inject.MultiParameterInjectionTarget.KeyIndexedParameters;
import com.tokelon.toktales.tools.core.inject.MultiParameterInjectionTarget.KeyIndexedUnorderedParameters;

public class ParameterInjectorMultiParameterTest {


	private MultiParameterInjectionTarget injectionTarget;

	private InjectedObjectA injectedObjectA;
	private InjectedObjectB injectedObjectB;
	private InjectedObjectC injectedObjectC;
	
	private ParameterInjector unkeyedInjector;
	
	
	@Before
	public void before() {
		injectionTarget = new MultiParameterInjectionTarget();
		injectedObjectA = new InjectedObjectA();
		injectedObjectB = new InjectedObjectB();
		injectedObjectC = new InjectedObjectC();
		
		unkeyedInjector = new ParameterInjector(InjectParameters.class, injectedObjectA, injectedObjectB, injectedObjectC);
	}
	
	
	@Test
	public void injectInto_MultiParameters_ShouldSucceed() {
		unkeyedInjector.injectInto(injectionTarget);
		
		assertSame(injectedObjectA, injectionTarget.injectedObjectABa);
		assertSame(injectedObjectB, injectionTarget.injectedObjectABb);
	}
	
	
	@Test
	public void injectInto_MultiParameters_ShouldIgnoreOrder() {
		ParameterInjector injector = new ParameterInjector(InjectParameters.class, injectedObjectB, injectedObjectC, injectedObjectA);
		injector.injectInto(injectionTarget);
		
		assertSame(injectedObjectA, injectionTarget.injectedObjectIgnoreOrderA);
		assertSame(injectedObjectB, injectionTarget.injectedObjectIgnoreOrderB);
		assertSame(injectedObjectC, injectionTarget.injectedObjectIgnoreOrderC);
	}
	
	
	@Test(expected = ParameterInjectException.class)
	public void injectInto_DuplicateParameters_ShouldThrowException() {
		ParameterInjector injector = new ParameterInjector(InjectParams.forKey(KeyDuplicateParameters.class), injectedObjectA, injectedObjectB);
		injector.injectInto(injectionTarget);
	}
	
	@Test
	public void injectInto_AdditionalParameters_ShouldSucceed() {
		ParameterInjector injector = new ParameterInjector(InjectParams.forKey(KeyAdditionalParameters.class),
				new Object(), "str1", 12,
				injectedObjectB, "str2", injectedObjectC, 3.14,
				new Object(), injectedObjectA, "str3", "str4");
		
		injector.injectInto(injectionTarget);
		
		assertSame(injectedObjectA, injectionTarget.injectedObjectAdditionalParametersA);
		assertSame(injectedObjectB, injectionTarget.injectedObjectAdditionalParametersB);
		assertSame(injectedObjectC, injectionTarget.injectedObjectAdditionalParametersC);
	}
	
	
	@Test
	public void injectInto_IndexedParameters_ShouldRespectGivenParameterOrder() {
		InjectedObjectC c1 = new InjectedObjectC();
		InjectedObjectC c2 = new InjectedObjectC();
		
		ParameterInjector injector = new ParameterInjector(InjectParams.forKey(KeyIndexedParameters.class),
				 injectedObjectA, injectedObjectB, c1, c2);
		
		injector.injectInto(injectionTarget);
		
		assertSame(injectedObjectA, injectionTarget.injectedObjectIndexedParametersA);
		assertSame(injectedObjectB, injectionTarget.injectedObjectIndexedParametersB);
		assertSame(c1, injectionTarget.injectedObjectIndexedParametersC1);
		assertSame(c2, injectionTarget.injectedObjectIndexedParametersC2);
	}
	
	
	@Test
	public void injectInto_IndexedUnorderedParameters_ShouldInjectParametersInTheOrderTheyAreGiven() {
		List<Object[]> permutations = new ArrayList<>();
		permute(Arrays.asList(new Object[] {injectedObjectA, injectedObjectB, new InjectedObjectC(), new InjectedObjectC()}), 0, permutations);
		
		for(Object[] per: permutations) {
			ParameterInjector injector = new ParameterInjector(InjectParams.forKey(KeyIndexedUnorderedParameters.class), per);
			
			MultiParameterInjectionTarget target = new MultiParameterInjectionTarget();
			injector.injectInto(target);
			
			InjectedObjectA a = null;
			InjectedObjectB b = null;
			InjectedObjectC c1 = null;
			InjectedObjectC c2 = null;
			for(Object o: per) {
				if(o instanceof InjectedObjectA) {
					a = (InjectedObjectA) o;
				}
				else if(o instanceof InjectedObjectB) {
					b = (InjectedObjectB) o;
				}
				else if(c1 == null) {
					c1 = (InjectedObjectC) o;
				} else if(c2 == null) {
						c2 = (InjectedObjectC) o;
				}
				else {
					throw new RuntimeException();
				}
			}
			
			assertSame(Arrays.toString(per), a, target.injectedObjectIndexedUnorderedParametersA);
			assertSame(Arrays.toString(per), b, target.injectedObjectIndexedUnorderedParametersB);
			assertSame(Arrays.toString(per), c1, target.injectedObjectIndexedUnorderedParametersC1);
			assertSame(Arrays.toString(per), c2, target.injectedObjectIndexedUnorderedParametersC2);
		}
	}
	
	private static void permute(List<Object> arr, int k, List<Object[]> result) {
        for(int i = k; i < arr.size(); i++){
            Collections.swap(arr, i, k);
            permute(arr, k + 1, result);
            Collections.swap(arr, k, i);
        }
        
        if (k == arr.size() - 1){
        	result.add(Arrays.copyOf(arr.toArray(), arr.size()));
        }
    }
	
	
	@Test
	public void injectInto_IndexedSplittedSameTypeParameters_ShouldBeInjectedInTheOrderTheyAreGiven() {
		InjectedObjectC c1 = new InjectedObjectC();
		InjectedObjectC c2 = new InjectedObjectC();
		
		ParameterInjector injector = new ParameterInjector(InjectParams.forKey(KeyIndexedUnorderedParameters.class),
				 c1, injectedObjectA, injectedObjectB, c2);
		
		injector.injectInto(injectionTarget);
		
		assertSame(injectedObjectA, injectionTarget.injectedObjectIndexedUnorderedParametersA);
		assertSame(injectedObjectB, injectionTarget.injectedObjectIndexedUnorderedParametersB);
		assertSame(c1, injectionTarget.injectedObjectIndexedUnorderedParametersC1);
		assertSame(c2, injectionTarget.injectedObjectIndexedUnorderedParametersC2);
	}
	
}
