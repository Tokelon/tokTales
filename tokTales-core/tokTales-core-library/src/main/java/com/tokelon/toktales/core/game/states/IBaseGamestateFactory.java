package com.tokelon.toktales.core.game.states;

import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import com.google.inject.Injector;

public interface IBaseGamestateFactory {

	
	public <T extends IGameScene> BaseGamestate<T> create(TypeToken<T> sceneTypeToken);
	
	public <T extends IGameScene> BaseGamestate<T> create(Class<T> sceneTypeClass);
	

	
	public static class BaseGamestateFactory implements IBaseGamestateFactory {
		private final Injector injector;

		@Inject
		public BaseGamestateFactory(Injector injector) {
			this.injector = injector;
		}
		
		@Override
		public <T extends IGameScene> BaseGamestate<T> create(TypeToken<T> sceneTypeToken) {
			BaseGamestate<T> gamestate = new BaseGamestate<T>(sceneTypeToken);
			injector.injectMembers(gamestate);
			return gamestate;
		}

		@Override
		public <T extends IGameScene> BaseGamestate<T> create(Class<T> sceneTypeClass) {
			return create(TypeToken.of(sceneTypeClass));
		}
	}
	
}
