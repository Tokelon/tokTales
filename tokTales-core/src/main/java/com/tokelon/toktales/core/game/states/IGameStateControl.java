package com.tokelon.toktales.core.game.states;

public interface IGameStateControl {

	// TODO: Implement listener support


	// Remove these two ?
	public void update(long timeMillis);
	public void render();
	
	

	public IGameState getActiveState();
	
	public String getActiveStateName();
	
	
	public void changeState(String stateName);
	
	public void addState(String name, IGameState state);
	
	public boolean hasState(String stateName);
	
	public IGameState removeState(String stateName);
	
	public IGameState getState(String stateName);
	
	

	
}
