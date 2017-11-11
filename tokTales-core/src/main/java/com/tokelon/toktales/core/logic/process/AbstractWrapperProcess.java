package com.tokelon.toktales.core.logic.process;

/** Base class that allows wrapping around a parent process.
 * <br><br>
 * 
 * Will throw exception if state is not handled correctly.
 * Valid state changes [() means optional]:<br>
 * Start -> (Unpause -> Pause ->) End<br>
 * Pause -> Unpause<br>
 * Unpause -> Pause<br>
 * <br>
 * 
 * 
 * Ensures correct calling order (parent / child) for lifecycle methods.
 * <br>
 * Calling order:<br>
 * Start: Parent -> Child<br>
 * End: Child -> Parent<br>
 * Pause: Child -> Parent<br>
 * Unpause: Parent -> Child<br>
 * 
 *
 * @param <T> The type of process to wrap.
 */
public abstract class AbstractWrapperProcess<T extends IPauseableProcess> extends AbstractPauseableProcess {

	private final T mParentProcess;
	
	
	public AbstractWrapperProcess(T parentProcess) {
		if(parentProcess == null) {
			throw new NullPointerException();
		}
		
		this.mParentProcess = parentProcess;
	}
	
	
	protected void internalBeforeStartProcess() { }
	protected void internalAfterStartProcess() { }
	protected void internalBeforeEndProcess() { }
	protected void internalAfterEndProcess() { }
	protected void internalBeforePause() { }
	protected void internalAfterPause() { }
	protected void internalBeforeUnpause() { }
	protected void internalAfterUnpause() { }
	
	
	protected T getParentProcess() {
		return mParentProcess; 
	}
	

	@Override
	public void startProcess() {
		// Before
		internalBeforeStartProcess();
		
		mParentProcess.startProcess();
		
		// After
		internalAfterStartProcess();	// main

		
		internalClearVariables();
	}

	
	@Override
	public void endProcess() {
		
		// Before
		internalBeforeEndProcess();	// main

		mParentProcess.endProcess();
		
		// After
		internalAfterEndProcess();

		
		internalClearObjects();
	}
	
	
	@Override
	public void pause() {
		// Before
		internalBeforePause();	// main
		
		// Do anything additional BEFORE calling super
		mParentProcess.pause();
		
		// After
		internalAfterPause();
	}
	
	
	@Override
	public void unpause() {

		// Before
		internalBeforeUnpause();
		
		mParentProcess.unpause();

		// After
		internalAfterUnpause(); 	// main
	}
	
	
	
	

	/** Called after {@link startProcess()} has finished.
	 * 
	 */
	protected void internalClearVariables() { }

	/** Called after {@link endProcess()} has finished.
	 * 
	 */
	protected void internalClearObjects() { }
	
	
	
}
