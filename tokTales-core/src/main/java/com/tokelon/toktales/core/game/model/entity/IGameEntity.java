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
import com.tokelon.toktales.core.game.model.IPoint2f;
import com.tokelon.toktales.core.game.model.IRectangle2f;
import com.tokelon.toktales.core.game.model.IPoint2f.IMutablePoint2f;
import com.tokelon.toktales.core.game.model.IRectangle2f.IMutableRectangle2f;
import com.tokelon.toktales.core.game.world.IWorldspace;

public interface IGameEntity extends IParticipable<IGameEntity> {
	
	/* Add a generic argument that defines the actual underlying type?
	 * NOPE!! The problems arising do not outweigh the advantages of not having to cast
	 * 
	 * 
	public T asT();		// For subclasses (probably not useful)
	public T entity();	// For when wrapping the actual object !!	Implement!!
	 * 
	 * Also add class without type parameter that just extends the other with Object or something
	 * StaticGameEntity
	public GameEntityObj extends GameEntity<Object> {
	 */

	
	/*
	 * TODO: Add looking direction
	 * At some point maybe 360 support
	 * 
	 * Rotation?
	 */
	
	/*
	 * Add object holder like this for storing values simply ?
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
	
	
	/**
	 * @return True if this entity has a payload, false if not.
	 */
	public boolean hasPayload();
	
	/**
	 * @return This entity's payload, or null if there is none.
	 */
	public Object getPayload();
	
	/**
	 * 
	 * @param desiredClass The desired payload class.
	 * @return This entity's payload cast to the given class, or null if there is no payload or the payload is not compatible to the given class.
	 */
	public <T> T getPayloadAs(Class<T> desiredClass); // rename desiredClass to type ?
	

	public boolean isActive();
	public boolean isVisible();

	public float getHeight();
	public float getWidth();
	
	public float getSpeedX();
	public float getSpeedY();
	

	public float getVelocityX();
	public float getVelocityY();
	
	/** The velocity of an entity defines the speed and velocity currently applied.
	 * 
	 * @param result Where the result will be stored.
	 * @return The given object containing the entity velocity.
	 */
	public IMutablePoint2f getVelocity(IMutablePoint2f result);	// IPoint2f is not perfect, IVector2f would be more fitting
	
	
	
	// The reason I added the postfix 'back' to the "normal" getters, is to discourage wide use in favor of the parameterized versions
	
	/** The bounds of an entity designate the area it is covering. 
	 * 
	 * @param result Where the result will be stored.
	 * @return The given object containing the entity bounds.
	 */
	public IMutableRectangle2f getBounds(IMutableRectangle2f result);
	
	/** Useful when the result will not be assigned.
	 * <br><br>
	 * <b>Caution:</b> The result may be modified over time.
	 * <br>
	 * Preferable use of {@link #getBounds(IMutableRectangle2f)} is recommended.
	 * 
	 * @return The backing object for the entity bounds.
	 */
	public IRectangle2f getBoundsBack();


	/** The collision bounds of an entity designate the area where it's collision applies.
	 * 
	 * @param result Where the result will be stored.
	 * @return The given object containing the entity collision bounds.
	 */
	public IMutableRectangle2f getCollisionBounds(IMutableRectangle2f result);
	
	/** Useful when the result will not be assigned.
	 * <br><br>
	 * <b>Caution:</b> The result may be modified over time.
	 * <br>
	 * Preferable use of {@link #getCollisionBounds(IMutableRectangle2f)} is recommended.
	 * 
	 * @return The backing object for the entity collision bounds.
	 */
	public IRectangle2f getCollisionBoundsBack();
	
	
	/** The collision box of an entity designates the size and relative position where collision will be applied.
	 * 
	 * @param result Where the result will be stored.
	 * @return The given object containing the entity collision box.
	 */
	public IMutableRectangle2f getCollisionBox(IMutableRectangle2f result);
	
	/** Useful when the result will not be assigned.
	 * <br><br>
	 * <b>Caution:</b> The result may be modified over time.
	 * <br>
	 * Preferable use of {@link #getCollisionBox(IMutableRectangle2f)} is recommended.
	 * 
	 * @return The backing object for the entity collision box.
	 */
	public IRectangle2f getCollisionBoxBack();
	
	
	public float getOriginX();
	public float getOriginY();
	
	/** The origin of an entity is the point which added to the raw coordinates,
	 * designates the center of the entity and it's world coordinates. 
	 * 
	 * @param result Where the result will be stored.
	 * @return The given object containing the entity origin.
	 */
	public IMutablePoint2f getOrigin(IMutablePoint2f result);
	
	
	public float getRawWorldX();
	public float getRawWorldY();
	
	/** The raw coordinates of an entity point to the top left corner of it's bounds.  
	 * 
	 * @param result Where the result will be stored.
	 * @return The given object containing the entity raw coordinates.
	 */
	public IMutablePoint2f getRawWorldCoordinates(IMutablePoint2f result);

	
	public float getWorldX();
	public float getWorldY();
	
	/** The world coordinates of an entity point to the center of it's bounds.  
	 * 
	 * @param result Where the result will be stored.
	 * @return The given object containing the entity world coordinates.
	 */
	public IMutablePoint2f getWorldCoordinates(IMutablePoint2f result);
	
	// TODO: Add *back version to the methods that do not have it?
	
	
	
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
	
	
	

	// Only points have *X and *Y methods because often these methods are used independently of each other
	// They are also needed because there are no 'back' methods for these

	// Add 'back' version to speed, velocity etc?
	
	
	public void setActive(boolean isActive);
	public void setVisible(boolean isVisible);
	
	
	public void setWidth(float width);
	public void setHeight(float height);
	public void setSize(float width, float height);


	public void setSpeedX(float sx);
	public void setSpeedY(float sy);
	public void setSpeed(float sx, float sy);
	
	
	public void setVelocityX(float vx);
	public void setVelocityY(float vy);
	public void setVelocity(float vx, float vy);

	/* Add these? What about the other speed, size etc?
	public void modifyVelocity(float dx, float dy);
	public float modifyVelocityX(float dx);
	public float modifyVelocityY(float dy);
	*/
	// IVelocityModifier?
	
	
	public void setOrigin(float originx, float originy);
	
	public void setCollisionBox(float left, float top, float right, float bottom);
	public void setCollisionBox(IRectangle2f box);
	
	public void setWorldCoordinates(float worldX, float worldY);
	public void setWorldCoordinates(IPoint2f worldCoords);
	
	
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
		public IGameEntityObserver getObservationInterest(IGameEntity subject, String change);
	
		
		
		/*
		 * Maybe make single method the default and accept IObserver<IGameEntity> as observers.
		 * Then check this method and cast if it returns false.
		 */
		//public boolean useSingleMethodOnly();
		
		//public void setUseSingleMethodOnly(boolean enable);
		
		
		
		public void entityActiveStatusChanged(IGameEntity entity);
		public void entityVisibleStatusChanged(IGameEntity entity);
		
		public void entitySizeChanged(IGameEntity entity);
		public void entityOriginChanged(IGameEntity entity);
		public void entityCollisionBoxChanged(IGameEntity entity);
		
		public void entitySpeedChanged(IGameEntity entity);
		public void entityVelocityChanged(IGameEntity entity);

		public void entityGraphicChanged(IGameEntity entity);
		public void entityGraphicBaseChanged(IGameEntity entity);
		
		//public void entityRotationChanged(IGameEntity entity);

		public void entityCoordinatesChanged(IGameEntity entity);

		public void entityStateWasAdjusted(IGameEntity entity);
		
		
	}
	

	public abstract class GameEntityObserver implements IGameEntityObserver {

		
		/* Overriding these methods is needed to do anything
		@Override
		public boolean hasInterest(IGameEntity subject, String change) {
		*/
		
		@Override
		public boolean isGeneric() {
			return false;
		}
		
		@Override
		public IGameEntityObserver getObservationInterest(IGameEntity subject, String change) {
			return this;
		}
		
		@Override
		public void subjectChanged(IGameEntity subject, String change) {
			// Nothing
		}

		
		
		@Override
		public void entityActiveStatusChanged(IGameEntity entity) { }

		@Override
		public void entityVisibleStatusChanged(IGameEntity entity) { }

		@Override
		public void entitySizeChanged(IGameEntity entity) { }

		@Override
		public void entityOriginChanged(IGameEntity entity) { }
		
		@Override
		public void entityCollisionBoxChanged(IGameEntity entity) {	}

		@Override
		public void entitySpeedChanged(IGameEntity entity) { }

		@Override
		public void entityVelocityChanged(IGameEntity entity) { }
		
		@Override
		public void entityGraphicChanged(IGameEntity entity) { }
		
		@Override
		public void entityGraphicBaseChanged(IGameEntity entity) { }
		
		@Override
		public void entityCoordinatesChanged(IGameEntity entity) { }
		
		@Override
		public void entityStateWasAdjusted(IGameEntity entity) { }
		
	}
	
	
	
	/* Participation */
	
	@Override
	public IParticipation<IGameEntity, IObserver<IGameEntity>, IParticipant<IGameEntity>> getParticipation();
	
	public void addInheritedParticipant(IParticipant<IGameEntity> participant);
	public void removeInheritedParticipant(IParticipant<IGameEntity> participant);
	
	
	public interface IGameEntityParticipant extends IGameEntityObserver, IParticipant<IGameEntity> {
		
		@Override
		public IGameEntityParticipant getParticipationInterest(IGameEntity subject, String change);
		
		
		
		public boolean onEntityActiveStatusChange(IGameEntity entity);
		public boolean onEntityVisibleStatusChange(IGameEntity entity);
		
		public boolean onEntitySizeChange(IGameEntity entity);
		public boolean onEntityOriginChange(IGameEntity entity);
		
		public boolean onEntityCollisionBoxChange(IGameEntity entity);
		
		public boolean onEntitySpeedChange(IGameEntity entity);
		public boolean onEntityVelocityChange(IGameEntity entity);
		
		public boolean onEntityGraphicChange(IGameEntity entity);
		public boolean onEntityGraphicBaseChange(IGameEntity entity);
		
		public boolean onEntityCoordinatesChange(IGameEntity entity);

		//public boolean onEntityRotationChange(IGameEntity entity);

		
		public boolean onEntityStateAdjust(IGameEntity entity);
		
		
	}
	
	
	
	public abstract class GameEntityParticipant extends GameEntityObserver implements IGameEntityParticipant {

		// Overriding this method is needed to do anything
		/*
		@Override
		public boolean hasInterest(IGameEntity subject, String change) {
		*/
		

		@Override
		public IGameEntityParticipant getParticipationInterest(IGameEntity subject, String change) {
			return this;
		}
		
		@Override
		public boolean onSubjectChange(IGameEntity subject, String change) {
			return false;
		}


		

		@Override
		public boolean onEntityActiveStatusChange(IGameEntity entity) { return false; }

		@Override
		public boolean onEntityVisibleStatusChange(IGameEntity entity) { return false; }

		@Override
		public boolean onEntitySizeChange(IGameEntity entity) { return false; }

		@Override
		public boolean onEntityOriginChange(IGameEntity entity) { return false; }
		
		@Override
		public boolean onEntityCollisionBoxChange(IGameEntity entity) { return false; }
		
		@Override
		public boolean onEntitySpeedChange(IGameEntity entity) { return false; }

		@Override
		public boolean onEntityVelocityChange(IGameEntity entity) {	return false; }
		
		@Override
		public boolean onEntityGraphicChange(IGameEntity entity) { return false; }
		
		@Override
		public boolean onEntityGraphicBaseChange(IGameEntity entity) { return false; }
		
		@Override
		public boolean onEntityCoordinatesChange(IGameEntity entity) { return false; }

		@Override
		public boolean onEntityStateAdjust(IGameEntity entity) { return false; }
		
	}
	
	
}
