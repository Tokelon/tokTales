package com.tokelon.toktales.core.game.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.tokelon.toktales.core.game.logic.observers.IObservation;
import com.tokelon.toktales.core.game.logic.observers.IObserver;
import com.tokelon.toktales.core.game.logic.observers.IParticipant;
import com.tokelon.toktales.core.game.logic.observers.IParticipation;
import com.tokelon.toktales.core.game.model.entity.IGameEntity;

public interface IActor extends IGameEntity {

	
	public static final String CHANGE_ACTOR_NAME = "change_actor_name";
	
	
	public static final String[] CHANGE_LIST_ACTOR = new String[]
	{
		CHANGE_ACTOR_NAME
	};
	
	public static final Set<String> CHANGE_LIST_ACTOR_SET = new HashSet<String>(Arrays.asList(CHANGE_LIST_ACTOR));
	
	
	

	public String getName();
	
	public void setName(String name);
	
	
	
	
	// NEW WAY
	public IObservation<IGameEntity, IObserver<IGameEntity>> getActorObservation();

	public interface IActorObserver extends IGameEntityObserver {
		
		public boolean hasActorInterest(IActor subject, String change);
		
		
		public default IActorObserver getActorObservationInterest(IActor subject, String change) { return this; }
		
		public default void subjectChangedActor(IActor subject, String change) { }
		
		
		public default void actorNameChanged(IActor actor) { }
	}
	
	
	
	public IParticipation<IGameEntity, IObserver<IGameEntity>, IParticipant<IGameEntity>> getActorParticipation();
	
	public interface IActorParticipant extends IGameEntityParticipant, IActorObserver {
		
		public default IActorParticipant getActorParticipationInterest(IActor subject, String change) { return this; }
		
		public default boolean onSubjectChangeActor(IActor subject, String change) { return false; }
		
		
		public default boolean onActorNameChange(IActor actor) { return false; }
	}
	
	
	
	/*	ALTERNATIVE WAY
	public IObservation<IActor, IObserver<IActor>> getActorObservation();

	public interface IActorObserver extends IObserver<IActor> {
		
		public void actorNameChanged(IActor actor);
	}
	
	
	public IParticipation<IActor, IObserver<IActor>, IParticipant<IActor>> getActorParticipation();
	
	public interface IActorParticipant extends IParticipant<IActor>, IActorObserver {

		public boolean onActorNameChange(IActor actor);
	}
	*/
	
}
