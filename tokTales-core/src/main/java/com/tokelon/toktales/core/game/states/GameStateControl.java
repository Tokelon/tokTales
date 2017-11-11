package com.tokelon.toktales.core.game.states;

import java.util.HashMap;
import java.util.Map;

import com.tokelon.toktales.core.engine.TokTales;

public class GameStateControl implements IGameStateControl {

	public static final String TAG = "GameStateControl";
	
	
	private boolean enableLogAccessState = false;
	private boolean enableLogAccessCurrentState = false;
	
	private boolean logAcessState = enableLogAccessState;
	private boolean logAcessCurrentState = enableLogAccessCurrentState;
	
	
	
	
	private final Map<String, IGameState> states = new HashMap<String, IGameState>();
	
	private IGameState currentState;
	private String currentStateName;
	


	@Override
	public void update(long timeMillis) {
		currentState.update(timeMillis);
		
		//game.getLogicManager().updateFrame(timeMillis);
	}

	@Override
	public void render() {
		logAcessState = false;
		logAcessCurrentState = false;
		
		
		currentState.render();

		
		logAcessState = enableLogAccessState;
		logAcessCurrentState = enableLogAccessCurrentState;

	}

	
	
	
	@Override
	public synchronized void changeState(String stateName) {
		if(!hasState(stateName)) {
			throw new IllegalArgumentException("No state for this name: " +stateName);
		}
		
		
		if(currentState != null) {
			currentState.onExit();
		}
		
		currentState = states.get(stateName);
		currentStateName = stateName;
		
		currentState.onEnter();
		
		TokTales.getLog().d(TAG, "State changed to: " +stateName);
	}
	
	
	@Override
	public synchronized void addState(String name, IGameState state) {
		if(name == null || state == null) {
			throw new IllegalArgumentException("name and state must not be null");
		}
		
		states.put(name, state);
		state.onEngage();
		
		TokTales.getLog().d(TAG, "State added: " +name);
	}
	
	
	@Override
	public synchronized boolean hasState(String stateName) {
		return states.containsKey(stateName);
	}
	
	
	@Override
	public synchronized IGameState removeState(String stateName) {
		IGameState state = states.get(stateName);
		if(state != null) {
			state.onDisengage();	
		}
		
		return states.remove(stateName);
	}
	
	
	@Override
	public synchronized IGameState getState(String stateName) {
		if(logAcessState) {
			TokTales.getLog().d(TAG, "Access to state: " +stateName);
			
			StringBuilder sb = new StringBuilder();
			for(StackTraceElement el: Thread.currentThread().getStackTrace()) {
				
				String els = el.toString();
				if(els.contains("com.tokelon.toktales")) {
					sb.append(els + "\n");
				}
			}
			
			TokTales.getLog().d(TAG, sb.toString());
		}
		
		
		return states.get(stateName);
	}
	
	
	
	@Override
	public IGameState getActiveState() {
		if(logAcessCurrentState) {
			TokTales.getLog().d(TAG, "Access to current state (" +currentStateName +")");
			
			StringBuilder sb = new StringBuilder();
			for(StackTraceElement el: Thread.currentThread().getStackTrace()) {
				
				String els = el.toString();
				if(els.contains("com.tokelon.toktales")) {
					sb.append(els + "\n");
				}
			}
			
			TokTales.getLog().d(TAG, sb.toString());
		}
		
		
		return currentState;
	}

	
	@Override
	public String getActiveStateName() {
		return currentStateName;
	}

	
}
