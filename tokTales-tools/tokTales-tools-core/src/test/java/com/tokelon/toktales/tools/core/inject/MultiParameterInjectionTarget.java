package com.tokelon.toktales.tools.core.inject;

import com.tokelon.toktales.tools.core.inject.InjectedObjects.InjectedObjectA;
import com.tokelon.toktales.tools.core.inject.InjectedObjects.InjectedObjectB;
import com.tokelon.toktales.tools.core.inject.InjectedObjects.InjectedObjectC;

public class MultiParameterInjectionTarget {

	
	protected InjectedObjectA injectedObjectABa;
	protected InjectedObjectB injectedObjectABb;
	
	protected InjectedObjectA injectedObjectIgnoreOrderA;
	protected InjectedObjectB injectedObjectIgnoreOrderB;
	protected InjectedObjectC injectedObjectIgnoreOrderC;
	
	protected InjectedObjectA injectedObjectDuplicateParametersA1;
	protected InjectedObjectA injectedObjectDuplicateParametersA2;
	
	protected InjectedObjectA injectedObjectAdditionalParametersA;
	protected InjectedObjectB injectedObjectAdditionalParametersB;
	protected InjectedObjectC injectedObjectAdditionalParametersC;
	
	protected InjectedObjectA injectedObjectIndexedParametersA;
	protected InjectedObjectB injectedObjectIndexedParametersB;
	protected InjectedObjectC injectedObjectIndexedParametersC1;
	protected InjectedObjectC injectedObjectIndexedParametersC2;
	
	protected InjectedObjectA injectedObjectIndexedUnorderedParametersA;
	protected InjectedObjectB injectedObjectIndexedUnorderedParametersB;
	protected InjectedObjectC injectedObjectIndexedUnorderedParametersC1;
	protected InjectedObjectC injectedObjectIndexedUnorderedParametersC2;
	
	
	
	@InjectParameters
	public void inject(InjectedObjectA a, InjectedObjectB b) {
		this.injectedObjectABa = a;
		this.injectedObjectABb = b;
	}
	
	@InjectParameters
	public void injectIgnoreOrder(InjectedObjectA a, InjectedObjectB b, InjectedObjectC c) {
		this.injectedObjectIgnoreOrderA = a;
		this.injectedObjectIgnoreOrderB = b;
		this.injectedObjectIgnoreOrderC = c;
	}
	
	
	class KeyDuplicateParameters { }
	
	@InjectParameters(KeyDuplicateParameters.class)
	public void injectDuplicateParameters(InjectedObjectA a1, InjectedObjectA a2) {
		this.injectedObjectDuplicateParametersA1 = a1;
		this.injectedObjectDuplicateParametersA2 = a2;
	}
	
	
	class KeyAdditionalParameters { }
	
	@InjectParameters(KeyAdditionalParameters.class)
	public void injectAdditionalParameters(InjectedObjectB b, InjectedObjectA a, InjectedObjectC c) {
		this.injectedObjectAdditionalParametersA = a;
		this.injectedObjectAdditionalParametersB = b;
		this.injectedObjectAdditionalParametersC = c;
	}
	
	
	class KeyIndexedParameters { }
	
	@InjectParameters(KeyIndexedParameters.class)
	public void injectIndexedParameters(InjectedObjectA a, InjectedObjectB b, InjectedObjectC c1, InjectedObjectC c2) {
		this.injectedObjectIndexedParametersA = a;
		this.injectedObjectIndexedParametersB = b;
		this.injectedObjectIndexedParametersC1 = c1;
		this.injectedObjectIndexedParametersC2 = c2;
	}
	
	
	class KeyIndexedUnorderedParameters { }

	@InjectParameters(KeyIndexedUnorderedParameters.class)
	public void injectIndexedUnorderedParameters(InjectedObjectB b, InjectedObjectA a, InjectedObjectC c1, InjectedObjectC c2) {
		this.injectedObjectIndexedUnorderedParametersA = a;
		this.injectedObjectIndexedUnorderedParametersB = b;
		this.injectedObjectIndexedUnorderedParametersC1 = c1;
		this.injectedObjectIndexedUnorderedParametersC2 = c2;
	}
	
}
