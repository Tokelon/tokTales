package com.tokelon.toktales.core.game.logic.observers;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;

public class SelfObservationWrapper<O extends IDirectObserver> implements ISelfObservation<O> {

	private final Set<O> observerSet;
	private final Set<O> immutableObserverSet;
	
	private final ISelfObservation<O> subject;
	
	
	public SelfObservationWrapper(ISelfObservation<O> subject) {
		this.subject = subject;
		
		observerSet = Collections.newSetFromMap(new WeakHashMap<O, Boolean>());
		immutableObserverSet = Collections.unmodifiableSet(observerSet);
	}
	
	/* Remember to synchronize all changes to the observer set by synchronizing to immutableObserverSet.
	 * 
	 */
	
	
	@Override
	public void notifyOfChange(String change) {
		
		synchronized (immutableObserverSet) {

			for(Iterator<O> it = observerSet.iterator(); it.hasNext();) {
				O observer = it.next();
				
				IDirectObserver interest = observer.getObservationInterest(subject, change);
				if(interest != null) {
					interest.subjectChanged(subject, change);
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
	
	
	
	public void runChange(ChangeCall<O> call) {
		
		synchronized (immutableObserverSet) {

			for(Iterator<O> it = observerSet.iterator(); it.hasNext();) {
				O observer = it.next();
				
				call.run(observer);
			}
		}
	}
	
	public interface ChangeCall<B extends IDirectObserver> {
		
		public void run(B observer);
		
	}

	
}
