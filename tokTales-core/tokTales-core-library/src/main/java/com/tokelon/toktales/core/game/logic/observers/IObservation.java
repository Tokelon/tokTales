package com.tokelon.toktales.core.game.logic.observers;

import java.util.Collection;

public interface IObservation<S, O extends IObserver<S>> {


	public void notifyOfChange(String change);

	//public void notifyObservers(String change);
	//public void notifyObservers(String... changeList);
	
	
	public void addObserver(O observer);
	public void removeObserver(O observer);
	
	// TODO: Implement or if trying to make sure, just call addObserver() again
	//public boolean hasObserver(O observer);
	
	
	/** Warning: To guarantee thread safety when iterating over the returned Collections,
	 * you must synchronize on it.
	 * 
	 * @return
	 */
	public Collection<O> getObservers();

	
	
	
	public interface IObservationHook<B extends IObserver<?>> {
	
		// Maybe pass subject as the first parameter
		
		/**
		 * @param change
		 * @return True to skip the change, false to continue.
		 */
		public boolean skipObservationNotificationHook(String change);
		

		/**
		 * 
		 * @param change
		 * @param observer
		 * @return True to handle notification of this observers yourself, false to continue.	//True to skip notification (change consumed), false to continue.
		 */
		public boolean handleObserverHook(String change, B observer);
		
		public void notifyObserverHook(String change, B observer);

	}
	
	
}
