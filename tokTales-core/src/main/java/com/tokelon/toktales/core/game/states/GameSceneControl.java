package com.tokelon.toktales.core.game.states;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.game.states.IGameSceneControl.IModifiableGameSceneControl;

public class GameSceneControl<T extends IGameScene> implements IModifiableGameSceneControl<T> {
	// TODO: Important - Inject log
	
	public static final String TAG = "GameSceneControl";
	
	
	// synchronize?
	private final Map<String, T> scenes;
	private final Map<String, T> unmodifiableScenes;
	
	private T activeScene;
	private String activeSceneName;
	
	public GameSceneControl() {
		this.scenes = new HashMap<String, T>();
		this.unmodifiableScenes = Collections.unmodifiableMap(scenes);
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
		

		//TokTales.getLog().d(TAG, String.format("Scene change in [%s]", this));
		TokTales.getLog().i(TAG, String.format("Scene was changed: from %s [%s] to %s [%s]", activeSceneName, activeScene, name, newScene));

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
		scene.onAssign();
		
		//TokTales.getLog().d(TAG, String.format("Scene addition in [%s]", this));
		TokTales.getLog().i(TAG, String.format("Scene was added: %s [%s]", name, scene));
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
	
}
