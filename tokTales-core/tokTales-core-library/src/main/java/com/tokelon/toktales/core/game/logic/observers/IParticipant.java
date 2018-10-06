package com.tokelon.toktales.core.game.logic.observers;

public interface IParticipant<S> extends IObserver<S> {

	
	public IParticipant<S> getParticipationInterest(S subject, String change);
	
	
	/**
	 * 
	 * @param subject
	 * @param change
	 * @return True if the subject was changed, false if not.
	 */
	public boolean onSubjectChange(S subject, String change);
	
}
