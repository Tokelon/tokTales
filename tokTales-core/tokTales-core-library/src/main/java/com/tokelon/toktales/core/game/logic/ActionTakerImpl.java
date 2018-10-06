package com.tokelon.toktales.core.game.logic;

public class ActionTakerImpl implements IActionTaker {

	private boolean hasAction = false;
	private long lastActionCode = 0;
	
	private long timeout = 0;
	
	private boolean cancel = false;
	
	
	@Override
	public boolean hasAction() {
		return hasAction;
	}

	@Override
	public void setAction(long actionCode) {
		lastActionCode = actionCode;
		hasAction = true;
	}

	@Override
	public void removeAction() {
		hasAction = false;
	}

	@Override
	public long getLastActionCode() {
		return lastActionCode;
	}

	
	
	@Override
	public boolean hasTimeout() {
		return timeout > 0;
	}
	@Override
	public long getTimeout() {
		return timeout;
	}
	@Override
	public void setTimeout(long millis) {
		if(millis < 0) {
			throw new IllegalArgumentException("millis must be >= 0");
		}
		
		this.timeout = millis;
	}

	
	@Override
	public boolean hasCancel() {
		return cancel;
	}

	@Override
	public void setCancel(boolean doCancel) {
		cancel = doCancel;
	}
	
}
