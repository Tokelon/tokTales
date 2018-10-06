package com.tokelon.toktales.core.game.logic.observers;

public interface IDirectObserver extends IObserver<ISelfObservation<?>> {

	
	@Override
	public IDirectObserver getObservationInterest(ISelfObservation<?> subject, String change);
	
}
