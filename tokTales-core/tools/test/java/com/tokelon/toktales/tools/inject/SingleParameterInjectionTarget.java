package com.tokelon.toktales.tools.inject;

import com.tokelon.toktales.tools.inject.InjectedObjects.InjectedObject;
import com.tokelon.toktales.tools.inject.InjectedObjects.InjectedObjectA;
import com.tokelon.toktales.tools.inject.InjectedObjects.InjectedObjectB;

class SingleParameterInjectionTarget {

	
	protected InjectedObject injectedObjectPublic;
	protected InjectedObject injectedObjectProtected;
	protected InjectedObject injectedObjectPackage;
	protected InjectedObject injectedObjectPrivate;
	
	protected InjectedObject injectedObjectKeyed;
	protected InjectedObject injectedObjectKeyedDuplicate;
	
	protected InjectedObjectA injectedObjectMethodOverloadA;
	protected InjectedObjectB injectedObjectMethodOverloadB;
	
	protected InjectedObject injectedObjectDefaultKey;
	protected InjectedObject injectedObjectAdditionalParameters;
	protected InjectedObject injectedObjectMissing;
	protected InjectedObject injectedObjectMultipleKeys;
	
	protected boolean injectNothingWasCalled = false;
	
	
	
	class KeyException { }

	@InjectParameters(KeyException.class)
	public void injectThrowException(InjectedObject obj) {
		throw new RuntimeException("I threw this");
	}
	

	class KeyParametersMissing { }
	
	@InjectParameters(KeyParametersMissing.class)
	public void injectParametersMissing(InjectedObject obj) {
		this.injectedObjectMissing = obj;
	}

	
	class KeyNoParameters { }
	
	@InjectParameters(KeyNoParameters.class)
	public void injectNoParameters(InjectedObject obj) {
		this.injectedObjectMissing = obj;
	}
	
	
	class KeyCaseA { }
	class KeyCaseB { }
	
	@InjectParameters(KeyCaseA.class)
	@InjectParameters(KeyCaseB.class)
	public void injectMultipleKeys(InjectedObject obj) {
		this.injectedObjectMultipleKeys = obj;
	}
	
	
	
	class KeyMethodOverload { }
	
	@InjectParameters(KeyMethodOverload.class)
	public void injectOverloadMethod(InjectedObjectA a) {
		injectedObjectMethodOverloadA = a;
	}
	
	@InjectParameters(KeyMethodOverload.class)
	public void injectOverloadMethod(InjectedObjectB b) {
		injectedObjectMethodOverloadB = b;
	}
	

	@InjectParameters
	public void injectNothing() {
		this.injectNothingWasCalled = true;
	}
	
	@InjectParameters
	public void injectAdditionalParameters(InjectedObject obj) {
		this.injectedObjectAdditionalParameters = obj;
	}
	
	
	@InjectParameters(Object.class)
	public void injectObjectDefaultKey(InjectedObject obj) {
		this.injectedObjectDefaultKey = obj;
	}
	
	@InjectParameters(ParameterInjectorSingleParameterTest.class)
	public void injectObjectKeyed(InjectedObject obj) {
		this.injectedObjectKeyed = obj;
	}
	
	@InjectParameters(ParameterInjectorSingleParameterTest.class)
	@InjectParameters(ParameterInjectorSingleParameterTest.class)
	public void injectObjectKeyedDuplicate(InjectedObject obj) {
		this.injectedObjectKeyedDuplicate = obj;
	}
	
	
	@InjectParameters
	public void injectObjectPublic(InjectedObject obj) {
		this.injectedObjectPublic = obj;
	}
	
	@InjectParameters
	protected void injectObjectProtected(InjectedObject obj) {
		this.injectedObjectProtected = obj;
	}
	
	@InjectParameters
	void injectObjectPackage(InjectedObject obj) {
		this.injectedObjectPackage = obj;
	}
	
	@InjectParameters
	private void injectObjectPrivate(InjectedObject obj) {
		this.injectedObjectPrivate = obj;
	}
	
}
