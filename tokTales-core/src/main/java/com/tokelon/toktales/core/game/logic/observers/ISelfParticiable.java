package com.tokelon.toktales.core.game.logic.observers;

public interface ISelfParticiable<O extends IDirectObserver, P extends IDirectParticipant> extends ISelfObservable<O> {

	
	/* An interface ISelfParticipable does not make sense since the object would implement ISelfParticipation in that case
	 * -> public ISelfParticipation<O, P> getSelfParticipation();
	 * 
	 * It is only here to remind you of that and for completions sake.
	 */
	
	
	public ISelfParticipation<O, P> getSelfParticipation();
	
}
