package com.tokelon.toktales.core.logic.process;

/**
 * 
 * Will throw exception if state is not handled correctly.<br><br>
 * Valid state changes [() means optional]:<br>
 * Start -> (Unpause -> Pause ->) End<br>
 * Pause -> Unpause<br>
 * Unpause -> Pause<br>
 * <br>
 *
 */
public abstract class AbstractPauseableProcess implements IPauseableProcess {

	public static final int STATE_NOT_STARTED = 1;
	public static final int STATE_UNPAUSED = 2;
	public static final int STATE_PAUSED = 3;
	public static final int STATE_ENDED = 4;
	
	
	/* Maybe introduce a STATE_ERROR which can be set by a method with an error string,
	 * and when activated skips any call to other methods.
	 * 
	 */
	
	
	private int state = STATE_NOT_STARTED;
	
	
	@Override
	public void startProcess() {
		if(state != STATE_NOT_STARTED || state != STATE_ENDED) {
			throw new IllegalStateException("Cannot start process that has been started");
		}
		
		state = STATE_PAUSED;
	}

	@Override
	public void endProcess() {
		if(state != STATE_PAUSED) {
			throw new IllegalStateException("Cannot end process that has not been paused");
		}
		
		state = STATE_ENDED;
	}

	
	@Override
	public void pause() {
		if(state != STATE_UNPAUSED) {
			throw new IllegalStateException("Cannot pause process that is not running");
		}
		
		state = STATE_PAUSED;
	}

	@Override
	public void unpause() {
		if(state != STATE_PAUSED) {
			throw new IllegalStateException("Cannot unpause process that has not been paused");
		}
		
		state = STATE_UNPAUSED;
	}

	
	public int getState() {
		return state;
	}

	
}
