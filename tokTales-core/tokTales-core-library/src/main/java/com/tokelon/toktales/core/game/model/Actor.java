package com.tokelon.toktales.core.game.model;

import com.tokelon.toktales.core.game.logic.observers.IObservation;
import com.tokelon.toktales.core.game.logic.observers.IObserver;
import com.tokelon.toktales.core.game.logic.observers.IParticipant;
import com.tokelon.toktales.core.game.logic.observers.IParticipation;
import com.tokelon.toktales.core.game.logic.observers.Participation;
import com.tokelon.toktales.core.game.logic.observers.IBaseParticipation.IParticipationHook;
import com.tokelon.toktales.core.game.model.entity.GameEntity;
import com.tokelon.toktales.core.game.model.entity.IGameEntity;

/** See {@link GameEntity} for subclassing this class.
 * 
 *
 */
public class Actor extends GameEntity implements IActor {

	
	private final IParticipation<IGameEntity, IObserver<IGameEntity>, IParticipant<IGameEntity>> mParticipation;

	
	private String mName;
	
	
	
	public Actor() {
		
		mParticipation = new Participation<IGameEntity, IObserver<IGameEntity>, IParticipant<IGameEntity>>(this, new ParticipationHook());
	}
	
	
	@Override
	public String getName() {
		return mName;
	}

	@Override
	public void setName(String name) {
		this.mName = name;
		
		getParticipation().notifyOfChange(CHANGE_ACTOR_NAME);
	}

	
	
	
	
	@Override
	public void addInheritedObserver(IObserver<IGameEntity> observer) {
		super.addInheritedObserver(observer);
		mParticipation.addObserver(observer);
	}
	
	@Override
	public void removeInheritedObserver(IObserver<IGameEntity> observer) {
		super.removeInheritedObserver(observer);
		mParticipation.removeObserver(observer);
	}
	
	@Override
	public void addInheritedParticipant(IParticipant<IGameEntity> participant) {
		super.addInheritedParticipant(participant);
		mParticipation.addParticipant(participant);
	}
	
	@Override
	public void removeInheritedParticipant(IParticipant<IGameEntity> participant) {
		super.removeInheritedParticipant(participant);
		mParticipation.removeParticipant(participant);
	}
	
	
	@Override
	public IObservation<IGameEntity, IObserver<IGameEntity>> getActorObservation() {
		return mParticipation;
	}
	
	@Override
	public IParticipation<IGameEntity, IObserver<IGameEntity>, IParticipant<IGameEntity>> getActorParticipation() {
		return mParticipation;
	}
	
	
	private class ParticipationHook implements IParticipationHook<IObserver<IGameEntity>, IParticipant<IGameEntity>> {

		@Override
		public boolean skipObservationNotificationHook(String change) {
			// The first and second set will be the same per default but not if one of the methods has been overriden
			return getActorObservation().getObservers().isEmpty()
					&& getActorParticipation().getObservers().isEmpty()
					&& getActorParticipation().getParticipants().isEmpty();
		}

		@Override
		public boolean handleObserverHook(String change, IObserver<IGameEntity> observer) {
			if(observer.isGeneric()) {
				return false;
			}
			else {
				return CHANGE_LIST_ACTOR_SET.contains(change);
			}
		}

		@Override
		public void notifyObserverHook(String change, IObserver<IGameEntity> observer) {
			if(!(observer instanceof IActorObserver)) {
				
				observer.subjectChanged(Actor.this, change);	// Call generic version
				return;
			}
			IActorObserver actorObserver = (IActorObserver) observer;
			
			
			switch (change) {
			case CHANGE_ACTOR_NAME:
				actorObserver.actorNameChanged(Actor.this);
				break;
			default:
				// Nothing
			}
		}

		@Override
		public boolean skipParticipationNotificationHook(String change) {
			return getActorParticipation().getParticipants().isEmpty();
		}

		@Override
		public boolean handleParticipantHook(String change, IParticipant<IGameEntity> participant) {
			if(participant.isGeneric()) {
				return false;
			}
			else {
				return CHANGE_LIST_ACTOR_SET.contains(change);
			}
		}

		@Override
		public boolean notifyParticipantHook(String change, IParticipant<IGameEntity> participant) {
			if(!(participant instanceof IActorParticipant)) {

				return participant.onSubjectChange(Actor.this, change);		// Call generic version
			}
			IActorParticipant actorParticipant = (IActorParticipant) participant;
			
			
			switch (change) {
			case CHANGE_ACTOR_NAME:
				return actorParticipant.onActorNameChange(Actor.this);
			default:
				return false;
			}
		}
		
	}
	
	
	
}
