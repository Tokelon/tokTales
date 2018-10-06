package com.tokelon.toktales.core.game.logic.observers;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Participation<S, O extends IObserver<S>, P extends IParticipant<S>> implements IParticipation<S, O, P> {

	// Debug / Implementation switch
	private final boolean manageConcurrentChanges = true;		// TODO: Test concurrent changes
	
	
	private final Set<O> observerSet;
	private final Set<O> immutableObserverSet;
	
	private final Set<P> participantSet;
	private final Set<P> immutableParticipantSet;
	
	
	private final Set<P> notifyParticipantSet;
	
	private final Set<String> pendingChangeSet;
	private boolean changeOngoing = false;
	
	private final S subject;
	private final IParticipationHook<O, P> hook;
	
	
	public Participation(S subject) {
		this(subject, null);
	}
	
	public Participation(S subject, IParticipationHook<O, P> hook) {
		this.subject = subject;
		this.hook = hook;
		
		//observerSet = Collections.newSetFromMap(new WeakHashMap<O, Boolean>());
		observerSet = new HashSet<O>();
		immutableObserverSet = Collections.unmodifiableSet(observerSet);
		
		//participantSet = Collections.newSetFromMap(new WeakHashMap<P, Boolean>());
		participantSet = new HashSet<P>();
		immutableParticipantSet = Collections.unmodifiableSet(participantSet);
		
		notifyParticipantSet = new HashSet<P>();
		pendingChangeSet = new HashSet<String>();
	}
	
	
	
	@Override
	public void notifyOfChange(String change) {
		// Do NOT call super.notifyOfChange()
		
		
		/* Consistency for observerSet and participantSet is not secured here,
		 * that means that an observer or participant could register while a change call is running (but not while subjectChanged() or onSubjectChange())
		 * 
		 * TODO: Maybe Fix (How ? Would need to synchronize on both observerSet and participantSet)
		 * 
		 */
		
		synchronized (pendingChangeSet) {
			if(manageConcurrentChanges && changeOngoing) {
				pendingChangeSet.add(change);
				return;
			}

			
			// Start change
			changeOngoing = true;
			
			// Notify participants and make changes
			notifyParticipants(change);
			
			// Notify observers of the final result
			notifyObservers(change);
			
			
			
			/* Work through pending changes
			 * The order is not considered ; There is no reason to use a list, as the order can never be predicted since the participants have unknown firing conditions
			 */
			for(String pendChange: pendingChangeSet) {
				notifyParticipants(pendChange);
				notifyObservers(pendChange);
			}
		
			// Finish change
			changeOngoing = false;
		}

	}
	
	
	
	/* Do not expose notifyObservers() and notifyParticipants() because synchronizing them will end in a clusterfuck
	 * Rather if it will be needed to call them separately, add two boolean parameters to notifyOfChange(String change, boolean notifyObservers, boolean notifyParticipants)
	 */
	
	
	// public ?
	/** Notifies all observers (participants too) of the finished change.
	 * 
	 * @param change
	 */
	private void notifyObservers(String change) {
		// Notifies both observers and participants of the finished change

		if(hook != null && hook.skipObservationNotificationHook(change)) {
			// Skip the change
			return;
		}


		
		/* Participants are treated like Observers in this part
		 */
		onlyNotifyParticipants(change);

		
		onlyNotifyObservers(change);

	}
	
	
	
	@SuppressWarnings("unchecked")
	/** Notifies exclusively observers of the finished change.
	 * 
	 * @param change
	 */
	private void onlyNotifyParticipants(String change) {
		
		synchronized (immutableParticipantSet) {
			
			for(Iterator<P> it = participantSet.iterator(); it.hasNext();) {
				/* For some reason a cast is needed here !
				 * IParticipant<S> --> IObserver<S>
				 */
				O participant = (O) it.next();
				
				
				if(hook != null && hook.handleObserverHook(change, participant)) {
					hook.notifyObserverHook(change, participant);
					
					// Skip notifying this participant
					continue;
				}

				
				if(participant.hasInterest(subject, change)) {
					IObserver<S> interest = participant.getObservationInterest(subject, change);
					if(interest != null) {
						interest.subjectChanged(subject, change);
					}	
				}
			}
			
		}		
	}
	
	/** Notifies exclusively participants of the finished change.
	 * 
	 * @param change
	 */
	private void onlyNotifyObservers(String change) {

		synchronized (immutableObserverSet) {

			for(Iterator<O> it = observerSet.iterator(); it.hasNext();) {
				O observer = it.next();

				if(hook != null && hook.handleObserverHook(change, observer)) {
					hook.notifyObserverHook(change, observer);
					
					// Skip notifying this observer
					return;
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

	
	
	// public ?
	/** Notifies participants of the ongoing change.
	 * 
	 * @param change
	 */
	private void notifyParticipants(String change) {
		
		if(hook != null && hook.skipParticipationNotificationHook(change)) {
			// Skip the change
			return;
		}
		
		
		synchronized (immutableParticipantSet) {
			
			boolean restartNotification;
			do {
				
				restartNotification = false;
				for(Iterator<P> it = participantSet.iterator(); it.hasNext();) {
					
					P participant = it.next();
					if(notifyParticipantSet.contains(participant)) {
						// Skip any participants that have changed the subject
						continue;
					}
					
					
					if(hook != null && hook.handleParticipantHook(change, participant)) {
						boolean subjectChanged = hook.notifyParticipantHook(change, participant);
						if(subjectChanged) {
							notifyParticipantSet.add(participant);
							restartNotification = true;
							break;
						}
						
						// Skip notifying this participant
						continue;
					}
					
					
					if(participant.hasInterest(subject, change)) {

						IParticipant<S> interest = participant.getParticipationInterest(subject, change);
						if(interest != null) {
							
							/* Update: This has been implemented in notifyOfChange()
							 * 
							 * Any changes to the subject happen in this call
							 * If a different change from the current "change" id happens, this will cause a chain reaction which could result in an infinite loop
							 * 
							 * If a change happens, either notifyOfChange(), notifyObservers() or notifyParticipants will be called.
							 * If one of those detect that we are currently inside a notification call,
							 * a) the new change will be added to a list "pending changes" and after the call finishes, we will go through that list and subsequently notify of every change made.
							 * b) If the change is already in pending changes, it will not be readded (but possibly move to the end of the list?) 
							 * 
							 */
							boolean subjectChanged = interest.onSubjectChange(subject, change);
							
							if(subjectChanged) {
								notifyParticipantSet.add(participant);
								restartNotification = true;
								break;
							}
						}	
					}
				}
				// for loop ended
				
				
			} while(restartNotification);
			
			
			notifyParticipantSet.clear();
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


	@Override
	public void addParticipant(P participant) {
		synchronized (participantSet) {
			participantSet.add(participant);
		}
	}

	@Override
	public void removeParticipant(P participant) {
		synchronized (participantSet) {
			participantSet.remove(participant);
		}
	}

	@Override
	public Collection<P> getParticipants() {
		return immutableParticipantSet;
	}

	
	
}
