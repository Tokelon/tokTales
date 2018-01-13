package com.tokelon.toktales.core.engine.inject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scope;
import com.google.inject.TypeLiteral;
import com.google.inject.binder.AnnotatedBindingBuilder;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.binder.ScopedBindingBuilder;

/** Alternative to {@link AbstractInjectModule} with builder syntax.
 * 
 */
public abstract class ExtendedAbstractInjectModule extends AbstractModule {

	// Ignore
	@SuppressWarnings("unused")
	private void testing() {
		//bindExtended(IEngine.class).to(Engine.class).andForNoScope(NotScoped.class); // Not so useful but works
		
		//bindExtended(IEngine.class).to(Engine.class).in(EngineScoped.class).andForNoScope(NotScoped.class);
		//bindExtended(IEngine.class).to(Engine.class).andInScope(EngineScoped.class, EngineInstance.class);
	}
	
	
	
	/* Extended bind methods */
	
	protected <T> ExtendedAnnotatedBindingBuilder<T> bindExtended(Class<T> clazz) {
		return new ExtendedBindingBuilder<T>(clazz);
	}
	
	protected <T> ExtendedAnnotatedBindingBuilder<T> bindExtended(TypeLiteral<T> typeLiteral) {
		return new ExtendedBindingBuilder<T>(typeLiteral);
	}
	
	protected <T> ExtendedLinkedBindingBuilder<T> bindExtended(Key<T> key) {
		return new ExtendedBindingBuilder<T>(key);
	}
	
	
	
	/* Extended binding builders */
	
	public interface ExtendedAdditionalBindingBuilder {

		public void andForNoScope(Class<? extends Annotation> annotationType);
	}
	
	public interface ExtendedScopedBindingBuilder extends ExtendedAdditionalBindingBuilder {
		public void andInScope(Class<? extends Annotation> scopeAnnotation, Class<? extends Annotation> annotationType);

		public ExtendedAdditionalBindingBuilder in(Class<? extends Annotation> scopeAnnotation);
		public ExtendedAdditionalBindingBuilder in(Scope scope);
		public ExtendedAdditionalBindingBuilder asEagerSingleton();
	}
	
	public interface ExtendedLinkedBindingBuilder<T> extends ExtendedScopedBindingBuilder {

		public ExtendedScopedBindingBuilder to(Class<? extends T> implementation);
		public ExtendedScopedBindingBuilder to(TypeLiteral<? extends T> implementation);
		public ExtendedScopedBindingBuilder to(Key<? extends T> targetKey);
		public ExtendedScopedBindingBuilder toProvider(Provider<? extends T> provider);
		public ExtendedScopedBindingBuilder toProvider(javax.inject.Provider<? extends T> provider);
		public ExtendedScopedBindingBuilder toProvider(Class<? extends javax.inject.Provider<? extends T>> providerType);
		public ExtendedScopedBindingBuilder toProvider(TypeLiteral<? extends javax.inject.Provider<? extends T>> providerType);
		public ExtendedScopedBindingBuilder toProvider(Key<? extends javax.inject.Provider<? extends T>> providerKey);
		public <S extends T> ExtendedScopedBindingBuilder toConstructor(Constructor<S> constructor);
		
		// Not sure how to store immediate values currently, maybe do later
		//public <S extends T> ExtendedScopedBindingBuilder toConstructorExtended(Constructor<S> constructor, TypeLiteral<? extends S> type);
	}
	
	public interface ExtendedAnnotatedBindingBuilder<T> extends ExtendedLinkedBindingBuilder<T> {
		
		public ExtendedLinkedBindingBuilder<T> annotatedWith(Class<? extends Annotation> annotationType);
		public ExtendedLinkedBindingBuilder<T> annotatedWith(Annotation annotation);
	}
	
	
	
	/* Builder implementation */
	
	private class ExtendedBindingBuilder<T> implements ExtendedAnnotatedBindingBuilder<T> {
		private final AnnotatedBindingBuilder<T> annotatedBindingBuilder;
		private LinkedBindingBuilder<T> linkedBindingBuilder;
		private ScopedBindingBuilder scopedBindingBuilder;
		
		private final Object type;
		private Class<T> typeClass = null;
		private TypeLiteral<T> typeTypeLiteral = null;
		private Key<T> typeKey = null;
		
		private Object implementation;
		private Class<? extends T> implementationClass;
		private TypeLiteral<? extends T> implementationTypeLiteral;
		private Key<? extends T> implementationTargetKey;
		
		private Provider<? extends T> providerGuice;
		private javax.inject.Provider<? extends T> providerJavax;
		private Class<? extends javax.inject.Provider<? extends T>> providerTypeClass;
		private TypeLiteral<? extends javax.inject.Provider<? extends T>> providerTypeTypeLiteral;
		private Key<? extends javax.inject.Provider<? extends T>> providerKey;
		private Constructor<? extends T> constructorSingle;
		
		
		public ExtendedBindingBuilder(Class<T> clazz) {
			AnnotatedBindingBuilder<T> annotatedBindingBuilder = ExtendedAbstractInjectModule.this.bind(clazz);
			this.annotatedBindingBuilder = annotatedBindingBuilder;
			this.linkedBindingBuilder = annotatedBindingBuilder;
			this.scopedBindingBuilder = annotatedBindingBuilder;
			
			this.typeClass = clazz;
			this.type = clazz;
		}
		
		public ExtendedBindingBuilder(TypeLiteral<T> typeLiteral) {
			AnnotatedBindingBuilder<T> annotatedBindingBuilder = ExtendedAbstractInjectModule.this.bind(typeLiteral);
			this.annotatedBindingBuilder = annotatedBindingBuilder;
			this.linkedBindingBuilder = annotatedBindingBuilder;
			this.scopedBindingBuilder = annotatedBindingBuilder;
			
			this.typeTypeLiteral = typeLiteral;
			this.type = typeLiteral;
		}
		
		public ExtendedBindingBuilder(Key<T> key) {
			LinkedBindingBuilder<T> linkedBindingBuilder = ExtendedAbstractInjectModule.this.bind(key);
			this.annotatedBindingBuilder = null;
			this.linkedBindingBuilder = linkedBindingBuilder;
			this.scopedBindingBuilder = linkedBindingBuilder;
			
			this.typeKey = key;
			this.type = key;
		}

		
		
		@Override
		public ExtendedLinkedBindingBuilder<T> annotatedWith(Class<? extends Annotation> annotationType) {
			linkedBindingBuilder = annotatedBindingBuilder.annotatedWith(annotationType);
			return this;
		}

		@Override
		public ExtendedLinkedBindingBuilder<T> annotatedWith(Annotation annotation) {
			linkedBindingBuilder = annotatedBindingBuilder.annotatedWith(annotation);
			return this;
		}
		
		
		@Override
		public ExtendedScopedBindingBuilder to(Class<? extends T> implementation) {
			scopedBindingBuilder = linkedBindingBuilder.to(implementation);
			
			this.implementationClass = implementation;
			this.implementation = implementation;
			return this;
		}

		@Override
		public ExtendedScopedBindingBuilder to(TypeLiteral<? extends T> implementation) {
			scopedBindingBuilder = linkedBindingBuilder.to(implementation);
			
			this.implementationTypeLiteral = implementation;
			this.implementation = implementation;
			return this;
		}

		@Override
		public ExtendedScopedBindingBuilder to(Key<? extends T> targetKey) {
			scopedBindingBuilder = linkedBindingBuilder.to(targetKey);
			
			this.implementationTargetKey = targetKey;
			this.implementation = targetKey;
			return this;
		}

		@Override
		public ExtendedScopedBindingBuilder toProvider(Provider<? extends T> provider) {
			scopedBindingBuilder = linkedBindingBuilder.toProvider(provider);
			
			this.providerGuice = provider;
			this.implementation = provider;
			return this;
		}

		@Override
		public ExtendedScopedBindingBuilder toProvider(javax.inject.Provider<? extends T> provider) {
			scopedBindingBuilder = linkedBindingBuilder.toProvider(provider);
			
			this.providerJavax = provider;
			this.implementation = provider;
			return this;
		}

		@Override
		public ExtendedScopedBindingBuilder toProvider(Class<? extends javax.inject.Provider<? extends T>> providerType) {
			scopedBindingBuilder = linkedBindingBuilder.toProvider(providerType);
			
			this.providerTypeClass = providerType;
			this.implementation = providerType;
			return this;
		}

		@Override
		public ExtendedScopedBindingBuilder toProvider(TypeLiteral<? extends javax.inject.Provider<? extends T>> providerType) {
			scopedBindingBuilder = linkedBindingBuilder.toProvider(providerType);
			
			this.providerTypeTypeLiteral = providerType;
			this.implementation = providerType;
			return this;
		}

		@Override
		public ExtendedScopedBindingBuilder toProvider(Key<? extends javax.inject.Provider<? extends T>> providerKey) {
			scopedBindingBuilder = linkedBindingBuilder.toProvider(providerKey);
			
			this.providerKey = providerKey;
			this.implementation = providerKey;
			return this;
		}

		@Override
		public <S extends T> ExtendedScopedBindingBuilder toConstructor(Constructor<S> constructor) {
			scopedBindingBuilder = linkedBindingBuilder.toConstructor(constructor);
			
			this.constructorSingle = constructor;
			this.implementation = constructor;
			return this;
		}


		@Override
		public void andInScope(Class<? extends Annotation> scopeAnnotation, Class<? extends Annotation> annotationType) {

			AnnotatedBindingBuilder<T> annotatedBuilder = null;
			if(type == typeClass) {
				annotatedBuilder = ExtendedAbstractInjectModule.this.bind(typeClass);
			}
			else if(type == typeTypeLiteral) {
				annotatedBuilder = ExtendedAbstractInjectModule.this.bind(typeTypeLiteral);
			}
			else if(type == typeKey) {
				annotatedBuilder = ExtendedAbstractInjectModule.this.bind(typeKey.getTypeLiteral());
			}
			else {
				assert false : "No type was provided!";
			}
			
			LinkedBindingBuilder<T> linkedBindingBuilder = annotatedBuilder.annotatedWith(annotationType);
			
			
			ScopedBindingBuilder scopedBindingBuilder = null;
			if(implementation == implementationClass) {
				scopedBindingBuilder = linkedBindingBuilder.to(implementationClass);
			}
			else if(implementation == implementationTypeLiteral) {
				scopedBindingBuilder = linkedBindingBuilder.to(implementationTypeLiteral);
			}
			else if(implementation == implementationTargetKey) {
				scopedBindingBuilder = linkedBindingBuilder.to(implementationTargetKey);
			}
			else if(implementation == providerGuice) {
				scopedBindingBuilder = linkedBindingBuilder.toProvider(providerGuice);
			}
			else if(implementation == providerJavax) {
				scopedBindingBuilder = linkedBindingBuilder.toProvider(providerJavax);
			}
			else if(implementation == providerTypeClass) {
				scopedBindingBuilder = linkedBindingBuilder.toProvider(providerTypeClass);
			}
			else if(implementation == providerTypeTypeLiteral) {
				scopedBindingBuilder = linkedBindingBuilder.toProvider(providerTypeTypeLiteral);
			}
			else if(implementation == providerKey) {
				scopedBindingBuilder = linkedBindingBuilder.toProvider(providerKey);
			}
			else if(implementation == constructorSingle) {
				scopedBindingBuilder = linkedBindingBuilder.toConstructor(constructorSingle);
			}
			else {
				assert false : "No type was provided!";
			}

			scopedBindingBuilder.in(scopeAnnotation);
		}

		@Override
		public ExtendedAdditionalBindingBuilder in(Class<? extends Annotation> scopeAnnotation) {
			scopedBindingBuilder.in(scopeAnnotation);
			return this;
		}

		@Override
		public ExtendedAdditionalBindingBuilder in(Scope scope) {
			scopedBindingBuilder.in(scope);
			return this;
		}

		@Override
		public ExtendedAdditionalBindingBuilder asEagerSingleton() {
			scopedBindingBuilder.asEagerSingleton();
			return this;
		}

		@Override
		public void andForNoScope(Class<? extends Annotation> annotationType) {

			AnnotatedBindingBuilder<T> annotatedBuilder = null;
			if(type == typeClass) {
				annotatedBuilder = ExtendedAbstractInjectModule.this.bind(typeClass);
			}
			else if(type == typeTypeLiteral) {
				annotatedBuilder = ExtendedAbstractInjectModule.this.bind(typeTypeLiteral);
			}
			else if(type == typeKey) {
				annotatedBuilder = ExtendedAbstractInjectModule.this.bind(typeKey.getTypeLiteral());
			}
			else {
				assert false : "No type was provided!";
			}
			
			LinkedBindingBuilder<T> linkedBindingBuilder = annotatedBuilder.annotatedWith(annotationType);
			
			
			if(implementation == implementationClass) {
				linkedBindingBuilder.to(implementationClass);
			}
			else if(implementation == implementationTypeLiteral) {
				linkedBindingBuilder.to(implementationTypeLiteral);
			}
			else if(implementation == implementationTargetKey) {
				linkedBindingBuilder.to(implementationTargetKey);
			}
			else if(implementation == providerGuice) {
				linkedBindingBuilder.toProvider(providerGuice);
			}
			else if(implementation == providerJavax) {
				linkedBindingBuilder.toProvider(providerJavax);
			}
			else if(implementation == providerTypeClass) {
				linkedBindingBuilder.toProvider(providerTypeClass);
			}
			else if(implementation == providerTypeTypeLiteral) {
				linkedBindingBuilder.toProvider(providerTypeTypeLiteral);
			}
			else if(implementation == providerKey) {
				linkedBindingBuilder.toProvider(providerKey);
			}
			else if(implementation == constructorSingle) {
				linkedBindingBuilder.toConstructor(constructorSingle);
			}
			else {
				assert false : "No type was provided!";
			}
		}
	}

	
}
