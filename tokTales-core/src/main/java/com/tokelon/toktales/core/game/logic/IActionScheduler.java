package com.tokelon.toktales.core.game.logic;

public interface IActionScheduler {

	
	// Maybe move related classes into .tools
	
	/**
	 * 
	 * @param actionTaker
	 * @throws ActionRequestException If we fail to get the action. This could be because of a timeout or a cancel.
	 */
	public void requestActionOrError(IActionTaker actionTaker);
	public boolean requestAction(IActionTaker actionTaker);
	
	public void finishAction(IActionTaker actionTaker);
	
}
