package com.tokelon.toktales.tools.core.inject.parameter;

import java.util.List;

import com.tokelon.toktales.tools.core.inject.parameter.InjectParameters;
import com.tokelon.toktales.tools.core.inject.parameter.InjectedObjects.InjectedObject;

public class InheritedParameterInjectionTargets {

	private InheritedParameterInjectionTargets() {}
	
	
	static class InjectBaseTarget {
		
		protected InjectedObject injectedObjectInjectBase;

		protected InjectedObject injectedObjectOverrideWithAnnotation;
		protected List<String> injectedObjectOverrideWithAnnotationGeneric;

		protected InjectedObject injectedObjectOverrideWithoutAnnotation;
		protected List<String> injectedObjectOverrideWithoutAnnotationGeneric;
		
		protected InjectedObject injectedObjectEqualMethods;
		
		protected int injectBaseOverrideCallCount = 0;

		
		@InjectParameters
		private void injectBase(InjectedObject obj) {
			this.injectedObjectInjectBase = obj;
		}
		
		
		@InjectParameters
		public void injectBaseOverrideWithAnnotation(InjectedObject obj) {
			this.injectedObjectOverrideWithAnnotation = obj;
		}
		
		static class KeyWithAnnotationGeneric { }
		
		@InjectParameters(KeyWithAnnotationGeneric.class)
		public void injectBaseOverrideWithAnnotationGeneric(List<String> obj) {
			this.injectedObjectOverrideWithAnnotationGeneric = obj;
		}
		
		
		@InjectParameters
		public void injectBaseOverrideWithoutAnnotation(InjectedObject obj) {
			this.injectedObjectOverrideWithoutAnnotation = obj;
		}
		
		
		static class KeyWithoutAnnotationGeneric { }
		
		@InjectParameters(KeyWithoutAnnotationGeneric.class)
		public void injectBaseOverrideWithoutAnnotationGeneric(List<String> obj) {
			this.injectedObjectOverrideWithoutAnnotationGeneric = obj;
		}
		
		
		static class KeyEqualMethodsPrivate { }
		
		@InjectParameters(KeyEqualMethodsPrivate.class)
		private void injectEqualMethods(InjectedObject obj) {
			this.injectedObjectEqualMethods = obj;
		}
		
		
		@InjectParameters
		public void injectBaseOverrideCallCount(InjectedObject obj) {
			injectBaseOverrideCallCount++;
		}
		
	}
	
	
	static class InjectInheritTarget extends InjectBaseTarget {
		
		protected InjectedObject injectedObjectInheritedOverrideWithAnnotation;
		protected List<String> injectedObjectInheritedOverrideWithAnnotationGeneric;

		protected InjectedObject injectedObjectInheritedOverrideWithoutAnnotation;
		protected List<String> injectedObjectInheritedOverrideWithoutAnnotationGeneric;
		
		protected InjectedObject injectedObjectInheritedEqualMethods;

		
		@InjectParameters
		@Override
		public void injectBaseOverrideWithAnnotation(InjectedObject obj) {
			super.injectBaseOverrideWithAnnotation(obj);
			
			this.injectedObjectInheritedOverrideWithAnnotation = obj;
		}
		
		@InjectParameters(KeyWithAnnotationGeneric.class)
		@Override
		public void injectBaseOverrideWithAnnotationGeneric(List<String> obj) {
			super.injectBaseOverrideWithAnnotationGeneric(obj);
			
			this.injectedObjectInheritedOverrideWithAnnotationGeneric = obj;
		}
		
		
		@Override
		public void injectBaseOverrideWithoutAnnotation(InjectedObject obj) {
			super.injectBaseOverrideWithoutAnnotation(obj);
			
			this.injectedObjectInheritedOverrideWithoutAnnotation = obj;
		}
		
		@Override
		public void injectBaseOverrideWithoutAnnotationGeneric(List<String> obj) {
			super.injectBaseOverrideWithoutAnnotationGeneric(obj);
			
			this.injectedObjectInheritedOverrideWithoutAnnotationGeneric = obj;
		}
		
		
		@InjectParameters(KeyEqualMethodsPrivate.class)
		private void injectEqualMethods(InjectedObject obj) {
			this.injectedObjectInheritedEqualMethods = obj;
		}
		
		
		@InjectParameters
		@Override
		public void injectBaseOverrideCallCount(InjectedObject obj) {
			super.injectBaseOverrideCallCount(obj);
		}
		
	}
	
}
