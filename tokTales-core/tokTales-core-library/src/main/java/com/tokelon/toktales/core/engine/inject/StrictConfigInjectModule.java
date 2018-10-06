package com.tokelon.toktales.core.engine.inject;

public class StrictConfigInjectModule extends AbstractInjectModule {
	// Maybe add switches for each setting
	
	@Override
	protected void configure() {
		// you can make 'inexact' annotated bindings - fallback for missing annotated bindings with parameter
		// e.g. bind(IControlScheme.class).annotatedWith(ForClass.class).to(IControlScheme.EmptyControlScheme.class);
		// -> This causes a dependency to (@ForClass(Example.class) IControlScheme controScheme) to succeed even if no For.forClass(Example.class) bound
		// you can also disable this feature
		binder().requireExactBindingAnnotations();
		
		// you can require explicit bindings
		// so JIT bindings would be disabled
		binder().requireExplicitBindings();
		
		// disable circular proxies - disable until there is a really good reason to enable
		// Cannot disable here - if users want to enable proxies there is no way!
		binder().disableCircularProxies();
	}

}
