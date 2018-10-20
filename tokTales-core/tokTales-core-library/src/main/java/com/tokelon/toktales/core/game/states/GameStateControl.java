package com.tokelon.toktales.core.game.states;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.log.ILogger;

public class GameStateControl implements IGameStateControl {

	public static final String TAG = "GameStateControl";
	
	
	private boolean enableLogAccessState = false;
	private boolean enableLogAccessCurrentState = false;
	
	private boolean logAcessState = enableLogAccessState;
	private boolean logAcessCurrentState = enableLogAccessCurrentState;
	
	
	private final Map<String, IGameState> states = new HashMap<String, IGameState>();
	
	private IGameState currentState;
	private String currentStateName;
	
	private final ILogger logger;

	@Inject
	public GameStateControl(ILogger logger) {
		this.logger = logger;
	}
	
	
	
	@Override
	public void resumeState() {
		getActiveState().onResume();
	}
	
	@Override
	public void pauseState() {
		getActiveState().onPause();
	}
	

	@Override
	public void updateState(long timeMillis) {
		getActiveState().onUpdate(timeMillis);
	}

	@Override
	public void renderState() {
		logAcessState = false;
		logAcessCurrentState = false;
		
		
		getActiveState().onRender();

		
		logAcessState = enableLogAccessState;
		logAcessCurrentState = enableLogAccessCurrentState;
	}

	
	
	@Override
	public synchronized void changeState(String stateName) {
		if(!hasState(stateName)) {
			String error = "Failed to change state. No state for name: " + stateName;
			logger.e(TAG, error);
			assert false : error;
			return;
		}
		
		if(currentState != null) {
			currentState.onExit();
		}
		
		IGameState newState = states.get(stateName);

		//logger.d(TAG, String.format("State change in [%s]", this));
		logger.i(TAG, String.format("State was changed: '%s' -> '%s' | [%s] -> [%s]", currentStateName, stateName, currentState, newState));
		
		currentState = newState;
		currentStateName = stateName;
		
		currentState.onEnter();
	}
	
	
	@Override
	public synchronized void addState(String name, IGameState state) {
		if(name == null || state == null) {
			throw new NullPointerException("name and state must not be null");
		}
		
		states.put(name, state);
		state.onEngage();
		
		//logger.d(TAG, String.format("State addition in [%s]", this));
		logger.i(TAG, String.format("State was added: '%s' | [%s]", name, state));
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
			logger.d(TAG, "Access to state: " +stateName);
			
			StringBuilder sb = new StringBuilder();
			for(StackTraceElement el: Thread.currentThread().getStackTrace()) {
				
				String els = el.toString();
				if(els.contains("com.tokelon.toktales")) {
					sb.append(els + "\n");
				}
			}
			
			logger.d(TAG, sb.toString());
		}
		
		
		return states.get(stateName);
	}
	
	
	
	@Override
	public IGameState getActiveState() {
		if(logAcessCurrentState) {
			logger.d(TAG, "Access to current state (" +currentStateName +")");
			
			StringBuilder sb = new StringBuilder();
			for(StackTraceElement el: Thread.currentThread().getStackTrace()) {
				
				String els = el.toString();
				if(els.contains("com.tokelon.toktales")) {
					sb.append(els + "\n");
				}
			}
			
			logger.d(TAG, sb.toString());
		}
		
		
		return currentState;
	}

	
	@Override
	public String getActiveStateName() {
		return currentStateName;
	}
	
}