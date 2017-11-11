package com.tokelon.toktales.core.game.logic.observers;

public interface IObserver<S> {

	
	public boolean isGeneric();
	
	
	public boolean hasInterest(S subject, String change);
	
	public IObserver<S> getObservationInterest(S subject, String change);
	
	
	
	public void subjectChanged(S subject, String change);
	//public void subjectChanged(S subject, String... changeList);	// changeArray
	
}
