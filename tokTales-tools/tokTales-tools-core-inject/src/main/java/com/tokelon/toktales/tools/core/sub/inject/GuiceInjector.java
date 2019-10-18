package com.tokelon.toktales.tools.core.sub.inject;

import java.lang.reflect.Type;

import javax.inject.Inject;
import javax.inject.Provider;

import com.google.inject.ConfigurationException;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.OutOfScopeException;
import com.google.inject.ProvisionException;
import com.tokelon.toktales.tools.core.inject.IInjector;
import com.tokelon.toktales.tools.core.inject.IKey;
import com.tokelon.toktales.tools.core.inject.IProvider;
import com.tokelon.toktales.tools.core.inject.InjectionException;

public class GuiceInjector implements IInjector {


	private final Injector guiceInjector;

	@Inject
	public GuiceInjector(Injector guiceInjector) {
		this.guiceInjector = guiceInjector;
	}
	
	
	@Override
	public void injectMembers(Object instance) {
		try {
			guiceInjector.injectMembers(instance);
		} catch (ConfigurationException | OutOfScopeException | ProvisionException e) {
			throw new InjectionException(e);
		}
	}

	
	@Override
	public <T> T getInstance(IKey<T> key) {
		if(!(key instanceof IGuiceKey)) {
			throw new InjectionException("This injector only supports keys of type " + IGuiceKey.class.getName());
		}
		IGuiceKey<T> guiceKey = (IGuiceKey<T>) key;
		
		try {
			return guiceInjector.getInstance(guiceKey.unwrap());
		} catch (ConfigurationException | OutOfScopeException | ProvisionException e) {
			throw new InjectionException(e);
		}
	}

	@Override
	public <T> T getInstance(Class<T> type) {
		try {
			return guiceInjector.getInstance(type);
		} catch (ConfigurationException | OutOfScopeException | ProvisionException e) {
			throw new InjectionException(e);
		}
	}
	
	@Override
	public Object getInstance(Type type) {
		try {
			return guiceInjector.getInstance(Key.get(type));
		} catch (ConfigurationException | OutOfScopeException | ProvisionException e) {
			throw new InjectionException(e);
		}
	}
	

	@Override
	public <T> IProvider<T> getProvider(IKey<T> key) {
		if(!(key instanceof IGuiceKey)) {
			throw new InjectionException("This injector only supports keys of type " + IGuiceKey.class.getName());
		}
		IGuiceKey<T> guiceKey = (IGuiceKey<T>) key;
		
		Provider<T> guiceProvider;
		try {
			guiceProvider = guiceInjector.getProvider(guiceKey.unwrap());
		} catch (ConfigurationException ce) {
			throw new InjectionException(ce);
		}
		
		return wrapProvider(guiceProvider);
	}

	@Override
	public <T> IProvider<T> getProvider(Class<T> type) {
		Provider<T> guiceProvider;
		try {
			guiceProvider = guiceInjector.getProvider(type);
		} catch (ConfigurationException ce) {
			throw new InjectionException(ce);
		}
		
		return wrapProvider(guiceProvider);
	}
	
	@Override
	public IProvider<?> getProvider(Type type) {
		Provider<?> guiceProvider;
		try {
			guiceProvider = guiceInjector.getProvider(Key.get(type));
		} catch (ConfigurationException ce) {
			throw new InjectionException(ce);
		}
		
		return wrapProvider(guiceProvider);
	}
	

	private <T> IProvider<T> wrapProvider(Provider<T> provider) {
		return () -> {
			try {
				return provider.get();
			} catch (OutOfScopeException | ProvisionException e) {
				throw new InjectionException(e);
			}
		};
	}
	
}
