package com.tokelon.toktales.core.game.logic.observers;

public interface IParticipable<S> extends IObservable<S> {

	
	public IParticipation<S, ? extends IObserver<S>, ? extends IParticipant<S>> getParticipation();
	//public IParticipation<? extends S, ? extends IObserver<? extends S>, ? extends IParticipant<? extends S>> getParticipation();
	
}
