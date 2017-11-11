package com.tokelon.toktales.core.game.logic;

public interface IActionTaker {

	public boolean hasAction();
	
	public void setAction(long actionCode);
	
	public void removeAction();
	
	public long getLastActionCode();
	
	
	public boolean hasTimeout();
	public long getTimeout();
	public void setTimeout(long millis);
	
	public boolean hasCancel();
	public void setCancel(boolean doCancel);

}
