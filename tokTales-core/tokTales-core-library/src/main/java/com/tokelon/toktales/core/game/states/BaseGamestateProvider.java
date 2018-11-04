package com.tokelon.toktales.core.game.states;

import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;

public class BaseGamestateProvider<T extends IGameScene> implements Provider<BaseGamestate<T>> {

	
	private Injector injector;
	
	private final TypeToken<T> sceneTypeToken;

	public BaseGamestateProvider(TypeToken<T> sceneTypeToken) {
		this.sceneTypeToken = sceneTypeToken;
	}
	
	@Inject
	protected void injectInjector(Injector injector) {
		this.injector = injector;
	}

	
	@Override
	public BaseGamestate<T> get() {
		BaseGamestate<T> baseGamestate = new BaseGamestate<T>(sceneTypeToken);
		injector.injectMembers(baseGamestate);
		return baseGamestate;
	}
	
}
