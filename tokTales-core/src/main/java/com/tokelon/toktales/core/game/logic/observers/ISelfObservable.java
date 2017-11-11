package com.tokelon.toktales.core.game.logic.observers;

public interface ISelfObservable<O extends IDirectObserver> {


	/* An interface ISelfObservable does not make sense since the object would implement ISelfObservation in that case
	 * -> public ISelfObservation<O> getSelfObservation();
	 * 
	 * It is only here to remind you of that and for completions sake.
	 */
	
	public ISelfObservation<O> getSelfObservation();
	
}
