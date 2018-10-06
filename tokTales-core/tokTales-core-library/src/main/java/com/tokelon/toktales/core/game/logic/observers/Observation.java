package com.tokelon.toktales.core.game.logic.observers;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Observation<S, O extends IObserver<S>> implements IObservation<S, O> {

	
	private final Set<O> observerSet;
	private final Set<O> immutableObserverSet;
	
	
	private final S subject;
	private final IObservationHook<O> hook;
	
	
	public Observation(S subject) {
		this(subject, null);
	}
	
	public Observation(S subject, IObservationHook<O> hook) {
		this.subject = subject;
		this.hook = hook;
		
		observerSet = new HashSet<O>();
		immutableObserverSet = Collections.unmodifiableSet(observerSet);
	}

	

	@Override
	public void notifyOfChange(String change) {
		
		if(hook != null && hook.skipObservationNotificationHook(change)) {
			// Skip the change
			return;
		}
		
		
		synchronized (immutableObserverSet) {

			for(Iterator<O> it = observerSet.iterator(); it.hasNext();) {
				O observer = it.next();

				if(hook != null && hook.handleObserverHook(change, observer)) {
					hook.notifyObserverHook(change, observer);
					
					// Skip notifying this observer
					continue;
				}
				
				
				if(observer.hasInterest(subject, change)) {
					IObserver<S> interest = observer.getObservationInterest(subject, change);
					if(interest != null) {
						interest.subjectChanged(subject, change);
					}	
				}
			}
		}

	}

	@Override
	public void addObserver(O observer) {
		synchronized (immutableObserverSet) {
			observerSet.add(observer);	
		}
	}

	@Override
	public void removeObserver(O observer) {
		synchronized (immutableObserverSet) {
			observerSet.remove(observer);
		}
	}

	@Override
	public Collection<O> getObservers() {
		return immutableObserverSet;
	}

	

	public void runChange(ChangeCall<S, O> call) {
		
		synchronized (immutableObserverSet) {

			for(Iterator<O> it = observerSet.iterator(); it.hasNext();) {
				O observer = it.next();
				
				call.run(observer);
			}
		}
	}
	
	public interface ChangeCall<D, B extends IObserver<D>> {
		
		public void run(B observer);
		
	}
	
}
