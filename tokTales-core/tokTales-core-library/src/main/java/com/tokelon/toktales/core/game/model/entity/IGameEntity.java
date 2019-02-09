package com.tokelon.toktales.core.game.model.entity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.tokelon.toktales.core.game.graphic.IBaseGraphic;
import com.tokelon.toktales.core.game.logic.entity.IGraphicsImage;
import com.tokelon.toktales.core.game.logic.observers.IObservation;
import com.tokelon.toktales.core.game.logic.observers.IObserver;
import com.tokelon.toktales.core.game.logic.observers.IParticipable;
import com.tokelon.toktales.core.game.logic.observers.IParticipant;
import com.tokelon.toktales.core.game.logic.observers.IParticipation;
import com.tokelon.toktales.core.game.world.IWorldspace;

public interface IGameEntity extends IGameEntityState, IParticipable<IGameEntity> {
	
	/* Add a generic argument that defines the actual underlying type?
	 * NOPE!! The problems arising do not outweigh the advantages of not having to cast
	 * 
	public T asT();		// For subclasses (probably not useful)
	public T entity();	// For when wrapping the actual object !!	Implement!!
	 * Also add class without type parameter that just extends the other with Object or something
	 * StaticGameEntity
	public GameEntityObj extends GameEntity<Object> {
	 */

	
	/* TODO: Add object holder like this for storing values simply ?
	 * -> IPropertyHolder with typed helper methods
	 */
	//public Map<Object, Object> getCustomValues();

	
	// Maybe implement:
	//public IMutableRectangle2f getPreviousBounds();
	//public void getPreviousWorldCoordinates(IMutablePoint2f result);

	
	
	public static final String CHANGE_ENTITY_ACTIVE_STATUS = "change_entity_active_status";
	public static final String CHANGE_ENTITY_VISIBLE_STATUS = "change_entity_visible_status";
	public static final String CHANGE_ENTITY_SIZE = "change_entity_size";
	public static final String CHANGE_ENTITY_ORIGIN = "change_entity_origin";
	public static final String CHANGE_ENTITY_SPEED = "change_entity_speed";
	public static final String CHANGE_ENTITY_COORDINATES = "change_entity_coordinates";
	public static final String CHANGE_ENTITY_STATE_ADJUST = "change_entity_state_adjust";
	public static final String CHANGE_ENTITY_COLLISION_BOX = "change_entity_collision_box";
	public static final String CHANGE_ENTITY_VELOCITY = "change_entity_velocity";
	public static final String CHANGE_ENTITY_GRAPHIC = "change_entity_graphic";
	public static final String CHANGE_ENTITY_GRAPHIC_BASE = "change_entity_graphic_base";
	//public static final String CHANGE_ENTITY_ROTATION = "change_entity_rotation";		// Add if needed
	//CHANGE_ENTITY_BOUNDS

	public static final String[] CHANGE_LIST_ENTITY = new String[]
	{
		CHANGE_ENTITY_ACTIVE_STATUS, CHANGE_ENTITY_VISIBLE_STATUS, CHANGE_ENTITY_SIZE, CHANGE_ENTITY_ORIGIN, CHANGE_ENTITY_SPEED,
		CHANGE_ENTITY_COORDINATES, CHANGE_ENTITY_STATE_ADJUST, CHANGE_ENTITY_COLLISION_BOX, CHANGE_ENTITY_VELOCITY,
		CHANGE_ENTITY_GRAPHIC, CHANGE_ENTITY_GRAPHIC_BASE
	};
	
	public static final Set<String> CHANGE_LIST_ENTITY_SET = new HashSet<String>(Arrays.asList(CHANGE_LIST_ENTITY));
	

	
	

	public void adjustState(long timeMillis);
	
	
	public void assignWorldspace(IWorldspace worldspace);
	public boolean hasWorldspaceAssigned();
	public IWorldspace getAssignedWorldspace();
	public boolean deassignWorldspace();
	
	
	public IGameEntityModel getModel();
	public void setModel(IGameEntityModel model);
	
	
	/**
	 * @return True if this entity has a payload, false if not.
	 */
	public boolean hasPayload();
	
	/**
	 * @return This entity's payload, or null if there is none.
	 */
	public Object getPayload();
	
	/**
	 * @param desiredClass The desired payload class.
	 * @return This entity's payload cast to the given class, or null if there is no payload or the payload is not compatible to the given class.
	 */
	public <T> T getPayloadAs(Class<T> desiredClass); // rename desiredClass to type ?
	

	
	
	public long getCurrentUpdateTime();
	public long getPreviousUpdateTime();
	public long getDeltaUpdateTime();
	

	public IBaseGraphic getGraphic();
	
	/**
	 * @param name
	 * @return A graphic for the given name, or null if there is none.
	 */
	public IBaseGraphic getGraphic(String name);
	
	
	public IBaseGraphic getGraphicBase();

	public IGraphicsImage getGraphicsImage();
	
	
	public void setGraphicBase(IBaseGraphic graphic);
	//public void setGraphicBasis(IBaseGraphic graphic);
	//public void setBasicGraphic(IBaseGraphic graphic);
	

	
	
	/* Observation */
	
	@Override
	public IObservation<IGameEntity, IObserver<IGameEntity>> getObservation();

	public void addInheritedObserver(IObserver<IGameEntity> observer);
	public void removeInheritedObserver(IObserver<IGameEntity> observer);
	
	
	public interface IGameEntityObserver extends IObserver<IGameEntity> {

		@Override
		public default boolean isGeneric() { return false;	}
		
		@Override
		public default IGameEntityObserver getObservationInterest(IGameEntity subject, String change) { return this; }
	
		@Override
		public default void subjectChanged(IGameEntity subject, String change) { }
		
		
		
		public default void entityActiveStatusChanged(IGameEntity entity) { }
		public default void entityVisibleStatusChanged(IGameEntity entity) { }
		
		public default void entitySizeChanged(IGameEntity entity) { }
		public default void entityOriginChanged(IGameEntity entity) { }
		public default void entityCollisionBoxChanged(IGameEntity entity) { }
		
		public default void entitySpeedChanged(IGameEntity entity) { }
		public default void entityVelocityChanged(IGameEntity entity) { }

		public default void entityGraphicChanged(IGameEntity entity) { }
		public default void entityGraphicBaseChanged(IGameEntity entity) { }
		
		//public default void entityRotationChanged(IGameEntity entity) { }

		public default void entityCoordinatesChanged(IGameEntity entity) { }

		public default void entityStateWasAdjusted(IGameEntity entity) { }
	}
	
	
	/* Participation */
	
	@Override
	public IParticipation<IGameEntity, IObserver<IGameEntity>, IParticipant<IGameEntity>> getParticipation();
	
	public void addInheritedParticipant(IParticipant<IGameEntity> participant);
	public void removeInheritedParticipant(IParticipant<IGameEntity> participant);
	
	
	public interface IGameEntityParticipant extends IGameEntityObserver, IParticipant<IGameEntity> {
		
		@Override
		public default IGameEntityParticipant getParticipationInterest(IGameEntity subject, String change) { return this; }
		
		@Override
		public default boolean onSubjectChange(IGameEntity subject, String change) { return false; }
		
		
		
		public default boolean onEntityActiveStatusChange(IGameEntity entity) { return false; }
		public default boolean onEntityVisibleStatusChange(IGameEntity entity) { return false; }
		
		public default boolean onEntitySizeChange(IGameEntity entity) { return false; }
		public default boolean onEntityOriginChange(IGameEntity entity) { return false; }
		
		public default boolean onEntityCollisionBoxChange(IGameEntity entity) { return false; }
		
		public default boolean onEntitySpeedChange(IGameEntity entity) { return false; }
		public default boolean onEntityVelocityChange(IGameEntity entity) { return false; }
		
		public default boolean onEntityGraphicChange(IGameEntity entity) { return false; }
		public default boolean onEntityGraphicBaseChange(IGameEntity entity) { return false; }
		
		public default boolean onEntityCoordinatesChange(IGameEntity entity) { return false; }

		//public default boolean onEntityRotationChange(IGameEntity entity) { return false; }

		
		public default boolean onEntityStateAdjust(IGameEntity entity) { return false; }
	}
	
}
