package com.tokelon.toktales.core.game.state;

import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;

public class GenericSceneGamestateProvider<G extends BaseGamestate<S>, S extends IGameScene> implements Provider<G> {

	
	private Injector injector;
	
	private final IGenericSceneGamestateFactory<G, S> gamestateFactory;
	private final TypeToken<S> sceneTypeToken;

	public GenericSceneGamestateProvider(IGenericSceneGamestateFactory<G, S> gamestateFactory, TypeToken<S> sceneTypeToken) {
		this.gamestateFactory = gamestateFactory;
		this.sceneTypeToken = sceneTypeToken;
	}
	
	@Inject
	protected void injectInjector(Injector injector) {
		this.injector = injector;
	}

	
	@Override
	public G get() {
		G gamestate = gamestateFactory.create(sceneTypeToken);
		injector.injectMembers(gamestate);
		return gamestate;
	}

	
	public interface IGenericSceneGamestateFactory<G extends BaseGamestate<S>, S extends IGameScene> {
		
		public G create(TypeToken<S> sceneTypeToken);
	}

}
