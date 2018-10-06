package com.tokelon.toktales.core.game.controller;

public interface IController extends IControllerChangeListener {

	
	// TODO: Return boolean to signify something ?
	public default void action(String action, Object... args) {} // Keep default?
	
}
