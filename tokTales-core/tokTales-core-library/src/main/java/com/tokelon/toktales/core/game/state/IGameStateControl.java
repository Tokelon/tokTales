package com.tokelon.toktales.core.game.state;

public interface IGameStateControl {
	// Implement listener support?

	
	public void resumeState();
	public void pauseState();
	
	public void updateState(long timeMillis);
	public void renderState();
	
	

	public IGameState getActiveState();
	public String getActiveStateName();
	

	public boolean hasState(String stateName);
	
	public IGameState getState(String stateName);
	
	
	public void changeState(String stateName);

	public void addState(String name, IGameState state);
	
	public IGameState removeState(String stateName);
	
}
