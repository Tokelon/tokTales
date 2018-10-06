package com.tokelon.toktales.core.game.states;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.game.states.IGameSceneControl.IModifiableGameSceneControl;

public class GameSceneControl<T extends IGameScene> implements IModifiableGameSceneControl<T> {
	
	public static final String TAG = "GameSceneControl";
	
	
	// synchronize?
	private final Map<String, T> scenes;
	private final Map<String, T> unmodifiableScenes;
	
	private T activeScene;
	private String activeSceneName;
	
	private final ILogger logger;
	
	@Inject
	public GameSceneControl(ILogger logger) {
		this.logger = logger;
		
		this.scenes = new HashMap<String, T>();
		this.unmodifiableScenes = Collections.unmodifiableMap(scenes);
	}
	
	
	@Override
	public void resumeScene() {
		getActiveScene().onResume();
	}
	
	@Override
	public void pauseScene() {
		getActiveScene().onPause();
	}
	
	@Override
	public void updateScene(long timeMillis) {
		getActiveScene().onUpdate(timeMillis);		
	}
	
	
	@Override
	public T getActiveScene() {
		return activeScene;
	}

	@Override
	public String getActiveSceneName() {
		return activeSceneName;
	}

	
	@Override
	public void changeScene(String name) {
		T newScene = scenes.get(name);
		if(newScene == null) {
			logger.e(TAG, "Cannot change scene: no scene for name " + name);
			assert false : "no scene for name " + name;
			return;
		}

		// Call onStop for active scene
		if(activeScene != null) {	// Maybe just have empty scene with implementation in here
			activeScene.onPause();
			activeScene.onStop();
		}
		

		//logger.d(TAG, String.format("Scene change in [%s]", this));
		logger.i(TAG, String.format("Scene was changed: '%s' -> '%s' | [%s] -> [%s]", activeSceneName, name, activeScene, newScene));

		activeScene = newScene;
		activeSceneName = name;
		
		// Call onStart for new scene
		activeScene.onStart();
		activeScene.onResume();
	}

	@Override
	public void addScene(String name, T scene) {
		if(name == null || scene == null) {
			throw new IllegalArgumentException("name and scene must not be null");
		}
		
		scenes.put(name, scene);
		//scene.onAssign(); // Do this in BaseGamestate, right?
		
		//logger.d(TAG, String.format("Scene addition in [%s]", this));
		logger.i(TAG, String.format("Scene was added: '%s' | [%s]", name, scene));
	}

	@Override
	public boolean hasScene(String name) {
		return scenes.containsKey(name);
	}

	@Override
	public T removeScene(String name) {
		IGameScene scene = scenes.get(name);
		if(scene != null) {
			scene.onDeassign();
		}
		
		return scenes.remove(name);
	}

	@Override
	public T getScene(String name) {
		return scenes.get(name);
	}

	@Override
	public Map<String, T> getSceneMap() {
		return unmodifiableScenes;
	}
	
	
	public static class GameSceneControlFactory implements IGameSceneControlFactory {
		private final Provider<ILogger> loggerProvider;

		@Inject
		public GameSceneControlFactory(Provider<ILogger> loggerProvider) {
			this.loggerProvider = loggerProvider;
		}
		
		@Override
		public <T extends IGameScene> IGameSceneControl<T> create() {
			return new GameSceneControl<T>(loggerProvider.get());
		}

		@Override
		public <T extends IGameScene> IModifiableGameSceneControl<T> createModifiable() {
			return new GameSceneControl<T>(loggerProvider.get());
		}
	}
	
}
