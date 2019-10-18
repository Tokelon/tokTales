package com.tokelon.toktales.core.engine.inject;

import java.lang.annotation.Annotation;

import javax.inject.Provider;

import com.google.inject.AbstractModule;
import com.tokelon.toktales.core.engine.inject.scope.EngineInstance;
import com.tokelon.toktales.core.engine.inject.scope.EngineScoped;
import com.tokelon.toktales.core.engine.inject.scope.GameInstance;
import com.tokelon.toktales.core.engine.inject.scope.GameScoped;
import com.tokelon.toktales.tools.core.sub.inject.scope.NotScoped;

public abstract class AbstractInjectModule extends AbstractModule {

	
	/*** Bind in Engine scope ***/

	/** Binds type <b>clazz</b> in scope {@link EngineScoped}.
	 * 
	 * @param clazz
	 */
	protected <T> void bindInEngineScope(Class<T> clazz) {
		bindInScope(clazz, EngineScoped.class);
	}
	
	/** Binds type <b>clazz</b> to <b>implementation</b> in scope {@link EngineScoped}.
	 * <p>
	 * Short for {@code bindInScope(clazz, implementation, EngineScoped.class)}.
	 * 
	 * @param clazz
	 * @param implementation
	 */
	protected <T> void bindInEngineScope(Class<T> clazz, Class<? extends T> implementation) {
		bindInScope(clazz, implementation, EngineScoped.class);
	}

	/** Binds type <b>clazz</b> to <b>provider</b> in scope {@link EngineScoped}.
	 * <p>
	 * Short for {@code bindToProviderInScope(clazz, provider, EngineScoped.class)}.
	 * 
	 * @param clazz
	 * @param provider
	 */
	protected <T> void bindToProviderInEngineScope(Class<T> clazz, Provider<? extends T> provider) {
		bindToProviderInScope(clazz, provider, EngineScoped.class);
	}
	
	/** Binds type <b>clazz</b> to <b>providerType</b> in scope {@link EngineScoped}.
	 * <p>
	 * Short for {@code bindToProviderInScope(clazz, providerType, EngineScoped.class)}.
	 * 
	 * @param clazz
	 * @param providerType
	 */
	protected <T> void bindToProviderInEngineScope(Class<T> clazz, Class<? extends Provider<? extends T>> providerType) {
		bindToProviderInScope(clazz, providerType, EngineScoped.class);
	}
	
	
	/*** Bind in Game scope ***/
	
	/** Binds type <b>clazz</b> in scope {@link GameScoped}.
	 * 
	 * @param clazz
	 */
	protected <T> void bindInGameScope(Class<T> clazz) {
		bindInScope(clazz, GameScoped.class);
	}
	
	/** Binds type <b>clazz</b> to <b>implementation</b> in scope {@link GameScoped}.
	 * <p>
	 * Short for {@code bindInScope(clazz, implementation, GameScoped.class)}.
	 * 
	 * @param clazz
	 * @param implementation
	 */
	protected <T> void bindInGameScope(Class<T> clazz, Class<? extends T> implementation) {
		bindInScope(clazz, implementation, GameScoped.class);
	}

	/** Binds type <b>clazz</b> to <b>provider</b> in scope {@link GameScoped}.
	 * <p>
	 * Short for {@code bindToProviderInScope(clazz, provider, GameScoped.class)}.
	 * 
	 * @param clazz
	 * @param provider
	 */
	protected <T> void bindToProviderInGameScope(Class<T> clazz, Provider<? extends T> provider) {
		bindToProviderInScope(clazz, provider, GameScoped.class);
	}
	
	/** Binds type <b>clazz</b> to <b>providerType</b> in scope {@link GameScoped}.
	 * <p>
	 * Short for {@code bindToProviderInScope(clazz, providerType, GameScoped.class)}.
	 * 
	 * @param clazz
	 * @param providerType
	 */
	protected <T> void bindToProviderInGameScope(Class<T> clazz, Class<? extends Provider<? extends T>> providerType) {
		bindToProviderInScope(clazz, providerType, GameScoped.class);
	}
	
	
	/*** Bind in scope ***/
	
	/** Binds type <b>clazz</b> in scope <b>scopeAnnotation</b>.
	 * 
	 * @param clazz
	 * @param scopeAnnotation
	 */
	protected <T> void bindInScope(Class<T> clazz, Class<? extends Annotation> scopeAnnotation) {
		bind(clazz).in(scopeAnnotation);
	}
	
	/** Binds type <b>clazz</b> to <b>implementation</b> in scope <b>scopeAnnotation</b>.
	 * 
	 * @param clazz
	 * @param implementation
	 * @param scopeAnnotation
	 */
	protected <T> void bindInScope(Class<T> clazz, Class<? extends T> implementation, Class<? extends Annotation> scopeAnnotation) {
		bind(clazz).to(implementation).in(scopeAnnotation);
	}
	
	/** Binds type <b>clazz</b> to <b>provider</b> in scope <b>scopeAnnotation</b>.
	 * 
	 * @param clazz
	 * @param provider
	 * @param scopeAnnotation
	 */
	protected <T> void bindToProviderInScope(Class<T> clazz, Provider<? extends T> provider, Class<? extends Annotation> scopeAnnotation) {
		bind(clazz).toProvider(provider).in(scopeAnnotation);
	}
	

	/** Binds type <b>clazz</b> to <b>providerType</b> in scope <b>scopeAnnotation</b>.
	 * 
	 * @param clazz
	 * @param provider
	 * @param scopeAnnotation
	 */
	protected <T> void bindToProviderInScope(Class<T> clazz, Class<? extends Provider<? extends T>> providerType, Class<? extends Annotation> scopeAnnotation) {
		bind(clazz).toProvider(providerType).in(scopeAnnotation);
	}
	

	/*** Bind in EngineScoped and for NotScoped ***/

	/** Binds the type <b>clazz</b> to <b>implementation</b> twice:<br>
	 * 1. In scope {@link EngineScoped}<br>
	 * 2. For no scope with annotation {@link NotScoped}<br>
	 * <p>
	 * Short for {@code bindInScopeAndForNotScoped(clazz, implementation, EngineScoped.class)}.
	 * 
	 * @param clazz
	 * @param implementation
	 */
	protected <T> void bindInEngineScopeAndForNotScoped(Class<T> clazz, Class<? extends T> implementation) {
		bindInScopeAndForNotScoped(clazz, implementation, EngineScoped.class);
	}
	
	/** Binds the type <b>clazz</b> to <b>provider</b> twice:<br>
	 * 1. In scope {@link EngineScoped}<br>
	 * 2. For no scope with annotation {@link NotScoped}<br>
	 * <p>
	 * Short for {@code bindInScopeAndForNotScoped(clazz, provider, EngineScoped.class)}.
	 * 
	 * @param clazz
	 * @param provider
	 */
	protected <T> void bindToProviderInEngineScopeAndForNotScoped(Class<T> clazz, Provider<? extends T> provider) {
		bindToProviderInScopeAndForNotScoped(clazz, provider, EngineScoped.class);
	}
	
	/** Binds the type <b>clazz</b> to <b>providerType</b> twice:<br>
	 * 1. In scope {@link EngineScoped}<br>
	 * 2. For no scope with annotation {@link NotScoped}<br>
	 * <p>
	 * Short for {@code bindInScopeAndForNotScoped(clazz, providerType, EngineScoped.class)}.
	 * 
	 * @param clazz
	 * @param providerType
	 */
	protected <T> void bindToProviderInEngineScopeAndForNotScoped(Class<T> clazz, Class<? extends Provider<? extends T>> providerType) {
		bindToProviderInScopeAndForNotScoped(clazz, providerType, EngineScoped.class);
	}
	
	
	/*** Bind in GameScoped and for NotScoped ***/
	
	/** Binds the type <b>clazz</b> to <b>implementation</b> twice:<br>
	 * 1. In scope {@link GameScoped}<br>
	 * 2. For no scope with annotation {@link NotScoped}<br>
	 * <p>
	 * Short for {@code bindInScopeAndForNotScoped(clazz, implementation, GameScoped.class)}.
	 * 
	 * @param clazz
	 * @param implementation
	 */
	protected <T> void bindInGameScopeAndForNotScoped(Class<T> clazz, Class<? extends T> implementation) {
		bindInScopeAndForNotScoped(clazz, implementation, GameScoped.class);
	}
	
	/** Binds the type <b>clazz</b> to <b>provider</b> twice:<br>
	 * 1. In scope {@link GameScoped}<br>
	 * 2. For no scope with annotation {@link NotScoped}<br>
	 * <p>
	 * Short for {@code bindInScopeAndForNotScoped(clazz, provider, GameScoped.class)}.
	 * 
	 * @param clazz
	 * @param provider
	 */
	protected <T> void bindToProviderInGameScopeAndForNotScoped(Class<T> clazz, Provider<? extends T> provider) {
		bindToProviderInScopeAndForNotScoped(clazz, provider, GameScoped.class);
	}
	
	/** Binds the type <b>clazz</b> to <b>providerType</b> twice:<br>
	 * 1. In scope {@link GameScoped}<br>
	 * 2. For no scope with annotation {@link NotScoped}<br>
	 * <p>
	 * Short for {@code bindInScopeAndForNotScoped(clazz, providerType, GameScoped.class)}.
	 * 
	 * @param clazz
	 * @param providerType
	 */
	protected <T> void bindToProviderInGameScopeAndForNotScoped(Class<T> clazz, Class<? extends Provider<? extends T>> providerType) {
		bindToProviderInScopeAndForNotScoped(clazz, providerType, GameScoped.class);
	}
	
	
	/*** Bind in scope and for NotScoped ***/
	
	/** Binds the type <b>clazz</b> to <b>implementation</b> twice:<br>
	 * 1. In scope <b>scopeAnnotation</b>
	 * 2. For no scope with annotation {@link NotScoped}<br>
	 * <p>
	 * Short for {@code bindInScopeAndForAnnotatedScope(clazz, implementation, scopeAnnotation, NotScoped.class)}.
	 * 
	 * @param clazz
	 * @param implementation
	 * @param scopeAnnotation
	 */
	protected <T> void bindInScopeAndForNotScoped(Class<T> clazz, Class<? extends T> implementation, Class<? extends Annotation> scopeAnnotation) {
		bindInScopeAndForAnnotatedNoScope(clazz, implementation, scopeAnnotation, NotScoped.class);
	}
	
	/** Binds the type <b>clazz</b> to <b>provider</b> twice:<br>
	 * 1. In scope <b>scopeAnnotation</b>
	 * 2. For no scope with annotation {@link NotScoped}<br>
	 * <p>
	 * Short for {@code bindToProviderInScopeAndForAnnotatedNoScope(clazz, provider, scopeAnnotation, NotScoped.class)}.
	 * 
	 * @param clazz
	 * @param provider
	 * @param scopeAnnotation
	 */
	protected <T> void bindToProviderInScopeAndForNotScoped(Class<T> clazz, Provider<? extends T> provider, Class<? extends Annotation> scopeAnnotation) {
		bindToProviderInScopeAndForAnnotatedNoScope(clazz, provider, scopeAnnotation, NotScoped.class);
	}
	
	/** Binds the type <b>clazz</b> to <b>providerType</b> twice:<br>
	 * 1. In scope <b>scopeAnnotation</b>
	 * 2. For no scope with annotation {@link NotScoped}<br>
	 * <p>
	 * Short for {@code bindToProviderInScopeAndForAnnotatedNoScope(clazz, providerType, scopeAnnotation, NotScoped.class)}.
	 * 
	 * @param clazz
	 * @param providerType
	 * @param scopeAnnotation
	 */
	protected <T> void bindToProviderInScopeAndForNotScoped(Class<T> clazz, Class<? extends Provider<? extends T>> providerType, Class<? extends Annotation> scopeAnnotation) {
		bindToProviderInScopeAndForAnnotatedNoScope(clazz, providerType, scopeAnnotation, NotScoped.class);
	}
	
	
	/*** Bind in scope and for annotated no scope ***/
	
	/** Binds the type <b>clazz</b> to <b>implementation</b> twice:<br>
	 * 1. In scope <b>scopeAnnotation</b><br>
	 * 2. For no scope with annotation <b>annotationType</b>
	 * 
	 * @param clazz
	 * @param implementation
	 * @param scopeAnnotation
	 * @param annotationType
	 */
	protected <T> void bindInScopeAndForAnnotatedNoScope(Class<T> clazz, Class<? extends T> implementation, Class<? extends Annotation> scopeAnnotation, Class<? extends Annotation> annotationType) {
		bind(clazz).to(implementation).in(scopeAnnotation);
		bind(clazz).annotatedWith(annotationType).to(implementation);
	}
	
	/** Binds the type <b>clazz</b> to <b>provider</b> twice:<br>
	 * 1. In scope <b>scopeAnnotation</b><br>
	 * 2. For no scope with annotation <b>annotationType</b>
	 * 
	 * @param clazz
	 * @param provider
	 * @param scopeAnnotation
	 * @param annotationType
	 */
	protected <T> void bindToProviderInScopeAndForAnnotatedNoScope(Class<T> clazz, Provider<? extends T> provider, Class<? extends Annotation> scopeAnnotation, Class<? extends Annotation> annotationType) {
		bind(clazz).toProvider(provider).in(scopeAnnotation);
		bind(clazz).annotatedWith(annotationType).toProvider(provider);
	}
	
	/** Binds the type <b>clazz</b> to <b>providerType</b> twice:<br>
	 * 1. In scope <b>scopeAnnotation</b><br>
	 * 2. For no scope with annotation <b>annotationType</b>
	 * 
	 * @param clazz
	 * @param providerType
	 * @param scopeAnnotation
	 * @param annotationType
	 */
	protected <T> void bindToProviderInScopeAndForAnnotatedNoScope(Class<T> clazz, Class<? extends Provider<? extends T>> providerType, Class<? extends Annotation> scopeAnnotation, Class<? extends Annotation> annotationType) {
		bind(clazz).toProvider(providerType).in(scopeAnnotation);
		bind(clazz).annotatedWith(annotationType).toProvider(providerType);
	}
	
	
	/*** Bind for no scope and in EngineScoped ***/
	
	/*** Binds the type <b>clazz</b> to <b>implementation</b> twice:<br>
	 * 1. For no scope<br>
	 * 2. In scope {@link EngineScoped} with annotation {@link EngineInstance}<br>
	 * <p>
	 * Short for {@code bindForNoScopeAndInAnnotatedScope(clazz, implementation, EngineInstance.class, EngineScoped.class)}.
	 * 
	 * @param clazz
	 * @param implementation
	 */
	protected <T> void bindForNoScopeAndInEngineScope(Class<T> clazz, Class<? extends T> implementation) {
		bindForNoScopeAndInAnnotatedScope(clazz, implementation, EngineInstance.class, EngineScoped.class);
	}
	
	/*** Binds the type <b>clazz</b> to <b>provider</b> twice:<br>
	 * 1. For no scope<br>
	 * 2. In scope {@link EngineScoped} with annotation {@link EngineInstance}<br>
	 * <p>
	 * Short for {@code bindForNoScopeAndInAnnotatedScope(clazz, provider, EngineInstance.class, EngineScoped.class)}.
	 * 
	 * @param clazz
	 * @param provider
	 */
	protected <T> void bindToProviderForNoScopeAndInEngineScope(Class<T> clazz, Provider<? extends T> provider) {
		bindToProviderForNoScopeAndInAnnotatedScope(clazz, provider, EngineInstance.class, EngineScoped.class);
	}
	
	/*** Binds the type <b>clazz</b> to <b>providerType</b> twice:<br>
	 * 1. For no scope<br>
	 * 2. In scope {@link EngineScoped} with annotation {@link EngineInstance}<br>
	 * <p>
	 * Short for {@code bindForNoScopeAndInAnnotatedScope(clazz, providerType, EngineInstance.class, EngineScoped.class)}.
	 * 
	 * @param clazz
	 * @param providerType
	 */
	protected <T> void bindToProviderForNoScopeAndInEngineScope(Class<T> clazz, Class<? extends Provider<? extends T>> providerType) {
		bindToProviderForNoScopeAndInAnnotatedScope(clazz, providerType, EngineInstance.class, EngineScoped.class);
	}
	
	
	/*** Bind for no scope and in EngineScoped ***/
	
	/*** Binds the type <b>clazz</b> to <b>implementation</b> twice:<br>
	 * 1. For no scope<br>
	 * 2. In scope {@link GameScoped} with annotation {@link GameInstance}<br>
	 * <p>
	 * Short for {@code bindForNoScopeAndInAnnotatedScope(clazz, implementation, GameInstance.class, GameScoped.class)}.
	 * 
	 * @param clazz
	 * @param implementation
	 */
	protected <T> void bindForNoScopeAndInGameScope(Class<T> clazz, Class<? extends T> implementation) {
		bindForNoScopeAndInAnnotatedScope(clazz, implementation, GameInstance.class, GameScoped.class);
	}
	
	/*** Binds the type <b>clazz</b> to <b>provider</b> twice:<br>
	 * 1. For no scope<br>
	 * 2. In scope {@link GameScoped} with annotation {@link GameInstance}<br>
	 * <p>
	 * Short for {@code bindForNoScopeAndInAnnotatedScope(clazz, provider, GameInstance.class, GameScoped.class)}.
	 * 
	 * @param clazz
	 * @param provider
	 */
	protected <T> void bindToProviderForNoScopeAndInGameScope(Class<T> clazz, Provider<? extends T> provider) {
		bindToProviderForNoScopeAndInAnnotatedScope(clazz, provider, GameInstance.class, GameScoped.class);
	}
	
	/*** Binds the type <b>clazz</b> to <b>providerType</b> twice:<br>
	 * 1. For no scope<br>
	 * 2. In scope {@link GameScoped} with annotation {@link GameInstance}<br>
	 * <p>
	 * Short for {@code bindForNoScopeAndInAnnotatedScope(clazz, providerType, GameInstance.class, GameScoped.class)}.
	 * 
	 * @param clazz
	 * @param providerType
	 */
	protected <T> void bindToProviderForNoScopeAndInGameScope(Class<T> clazz, Class<? extends Provider<? extends T>> providerType) {
		bindToProviderForNoScopeAndInAnnotatedScope(clazz, providerType, GameInstance.class, GameScoped.class);
	}
	
	
	/*** Bind for no scope and in annotated scope ***/
	
	/** Binds the type <b>clazz</b> to <b>implementation</b> twice:<br>
	 * 1. For no scope<br>
	 * 2. In scope <b>scopeAnnotation</b> with annotation <b>annotationType</b>
	 * 
	 * @param clazz
	 * @param implementation
	 * @param annotationType
	 * @param scopeAnnotation
	 */
	protected <T> void bindForNoScopeAndInAnnotatedScope(Class<T> clazz, Class<? extends T> implementation, Class<? extends Annotation> annotationType, Class<? extends Annotation> scopeAnnotation) {
		bind(clazz).to(implementation);
		bind(clazz).annotatedWith(annotationType).to(implementation).in(scopeAnnotation);
	}

	/** Binds the type <b>clazz</b> to <b>provider</b> twice:<br>
	 * 1. For no scope<br>
	 * 2. In scope <b>scopeAnnotation</b> with annotation <b>annotationType</b>
	 * 
	 * @param clazz
	 * @param provider
	 * @param annotationType
	 * @param scopeAnnotation
	 */
	protected <T> void bindToProviderForNoScopeAndInAnnotatedScope(Class<T> clazz, Provider<? extends T> provider, Class<? extends Annotation> annotationType, Class<? extends Annotation> scopeAnnotation) {
		bind(clazz).toProvider(provider);
		bind(clazz).annotatedWith(annotationType).toProvider(provider).in(scopeAnnotation);
	}
	
	/** Binds the type <b>clazz</b> to <b>providerType</b> twice:<br>
	 * 1. For no scope<br>
	 * 2. In scope <b>scopeAnnotation</b> with annotation <b>annotationType</b>
	 * 
	 * @param clazz
	 * @param providerType
	 * @param annotationType
	 * @param scopeAnnotation
	 */
	protected <T> void bindToProviderForNoScopeAndInAnnotatedScope(Class<T> clazz, Class<? extends Provider<? extends T>> providerType, Class<? extends Annotation> annotationType, Class<? extends Annotation> scopeAnnotation) {
		bind(clazz).toProvider(providerType);
		bind(clazz).annotatedWith(annotationType).toProvider(providerType).in(scopeAnnotation);
	}
	
	
}
