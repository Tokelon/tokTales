package com.tokelon.toktales.core.game.logic.observers;

public interface IDirectParticipant extends IParticipant<ISelfParticipation<?, ?>> {

	
	public boolean onSubjectChange(ISelfObservation<?> subject, String change);
	
}
