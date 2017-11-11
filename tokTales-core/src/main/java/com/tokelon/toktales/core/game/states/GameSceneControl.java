package com.tokelon.toktales.core.game.states;

import java.util.HashMap;
import java.util.Map;

import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.game.states.IGameSceneControl.IModifiableGameSceneControl;

public class GameSceneControl<T extends IGameScene> implements IModifiableGameSceneControl<T> {

	public static final String TAG = "GameSceneControl";
	
	
	// synchronize?
	private final Map<String, T> scenes;
	
	private T activeScene;
	private String activeSceneName;
	
	public GameSceneControl() {
		this.scenes = new HashMap<String, T>();
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
			TokTales.getLog().e(TAG, "Cannot change scene: no scene for name " + name);
			assert false : "no scene for name " + name;
			return;
		}

		// Call onStop for active scene
		if(activeScene != null) {	// Maybe just have empty scene with implementation in here
			activeScene.onPause();
			activeScene.onStop();
		}
		
		activeScene = newScene;
		activeSceneName = name;
		
		TokTales.getLog().d(TAG, "Scene changed to: " + name);

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
		scene.onAssign();
		
		TokTales.getLog().d(TAG, "Scene added: " + name);
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

	
}
