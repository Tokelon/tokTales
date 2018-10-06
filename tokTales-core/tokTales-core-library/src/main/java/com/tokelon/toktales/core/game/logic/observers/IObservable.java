package com.tokelon.toktales.core.game.logic.observers;

public interface IObservable<S> {

	public IObservation<S, ? extends IObserver<S>> getObservation();
	
}
