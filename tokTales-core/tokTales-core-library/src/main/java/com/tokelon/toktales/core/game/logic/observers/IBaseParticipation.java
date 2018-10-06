package com.tokelon.toktales.core.game.logic.observers;

import java.util.Collection;

public interface IBaseParticipation<S, O extends IObserver<S>, T, P extends IParticipant<T>> extends IObservation<S, O> {

	
	//public void notifyParticipants(String change);
	
	public void addParticipant(P participant);
	public void removeParticipant(P participant);
	
	
	/** Warning: To guarantee thread safety when iterating over the returned Collections,
	 * you must synchronize on it.
	 * 
	 * @return
	 */
	public Collection<P> getParticipants();	//WeakReference

	
	
	public interface IParticipationHook<B extends IObserver<?>, A extends IParticipant<?>> extends IObservationHook<B> {
		
		// Maybe pass subject as the first parameter
		
		/**
		 * @param change
		 * @return True to skip the change, false to continue.
		 */
		public boolean skipParticipationNotificationHook(String change);
	

		/**
		 * 
		 * @param change
		 * @param participant
		 * @return True to handle notification of this participant yourself, false to continue.	//True if to skip notification (change consumed), false to continue.
		 */
		public boolean handleParticipantHook(String change, A participant);
		
		
		/**
		 * @param change
		 * @param participant
		 * @return True if the subject was changed, false if not.
		 */
		public boolean notifyParticipantHook(String change, A participant);

	}
	
}
