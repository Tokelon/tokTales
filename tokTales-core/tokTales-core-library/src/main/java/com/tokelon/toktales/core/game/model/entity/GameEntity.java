package com.tokelon.toktales.core.game.model.entity;

import com.tokelon.toktales.core.game.graphic.IBaseGraphic;
import com.tokelon.toktales.core.game.logic.entity.GraphicsImage;
import com.tokelon.toktales.core.game.logic.entity.IGraphicsImage;
import com.tokelon.toktales.core.game.logic.observers.IObservation;
import com.tokelon.toktales.core.game.logic.observers.IObserver;
import com.tokelon.toktales.core.game.logic.observers.IParticipant;
import com.tokelon.toktales.core.game.logic.observers.IParticipation;
import com.tokelon.toktales.core.game.logic.observers.Participation;
import com.tokelon.toktales.core.game.logic.observers.IBaseParticipation.IParticipationHook;
import com.tokelon.toktales.core.game.model.IPoint2f;
import com.tokelon.toktales.core.game.model.IRectangle2f;
import com.tokelon.toktales.core.game.model.Point2fImpl;
import com.tokelon.toktales.core.game.model.Rectangle2fImpl;
import com.tokelon.toktales.core.game.model.IPoint2f.IMutablePoint2f;
import com.tokelon.toktales.core.game.model.IRectangle2f.IMutableRectangle2f;
import com.tokelon.toktales.core.game.world.IWorldspace;

/**
 * How to subclass this class:<br>
 * - If you have custom observation and participation objects,
 *   you must override {@link GameEntity#addInheritedObserver(IObserver)}, {@link GameEntity#removeInheritedObserver(IObserver)}
 *   , {@link GameEntity#addInheritedParticipant(IParticipant)}, {@link GameEntity#removeInheritedParticipant(IParticipant)} to fulfill the interface contract.
 * 
 *
 */
public class GameEntity implements IGameEntity {
	/* IParticipationHook (or any similar function interface) should never be implemented directly by this class,
	 * because it breaks it's subclasses. Rather make it a separate (private) class.
	 * 
	 */

	
	// TODO: Important - implement equals and hashCode


	private final Participation<IGameEntity, IObserver<IGameEntity>, IParticipant<IGameEntity>> mParticipation;
	

	// TODO: Default as true or false?
	private boolean entityActive = false;
	private boolean entityVisible = false;

	private float entityWidth = 0.0f;
	private float entityHeight = 0.0f;

	private float entitySpeedX = 0.0f;
	private float entitySpeedY = 0.0f;
	
	//private float mRotation = 0.0f;

	
	private final Point2fImpl entityOrigin = new Point2fImpl();
	private final Point2fImpl entityCoordinates = new Point2fImpl();
	private final Point2fImpl entityVelocity = new Point2fImpl();

	private final Rectangle2fImpl entityBounds = new Rectangle2fImpl();	
	private final Rectangle2fImpl entityCollisionBox = new Rectangle2fImpl();
	private final Rectangle2fImpl entityCollisionBounds = new Rectangle2fImpl();

	
	private IBaseGraphic entityGraphic;
	private IBaseGraphic entityGraphicBase;
	
	private final IGraphicsImage entityGraphicsImage;
	
	

	private IWorldspace assignedWorldspace;
	
	private final Object mPayload;
	
	
	private long currentUpdateTime = 0L;
	private long previousUpdateTime = 0L;


	/* Objects reused for calc */
	private final Point2fImpl calculatedCoordinatesRe = new Point2fImpl();
	private final Point2fImpl calculatedVelocityRe = new Point2fImpl();
	private final Point2fImpl calculatedCollisionRe = new Point2fImpl();
	
	
	
	public GameEntity() {
		this(null);
	}
	
	public GameEntity(Object payload) {
		this.mPayload = payload;

		mParticipation = new Participation<IGameEntity, IObserver<IGameEntity>, IParticipant<IGameEntity>>(this, new ParticipationHook());
		
		entityGraphicsImage = new GraphicsImage();
	}
	
	
	
	
	/* TODO: Next steps
	 * 
	 * - Remove all Motion methods - done
	 * - Refactor and fix Animations - done
	 * - Add physics support
	 * 
	 */
	
	
	
	@Override
	public void adjustState(long timeMillis) {

		long timePassedMillis = timeMillis - currentUpdateTime;
		
		
		// coordinates should be adjusted before the graphic state
		entityAdjustCoordinateState(timeMillis, timePassedMillis);		

		entityAdjustGraphicState(timeMillis, timePassedMillis);

		
		
		previousUpdateTime = currentUpdateTime;
		currentUpdateTime = timeMillis;
		
		
		getParticipation().notifyOfChange(CHANGE_ENTITY_STATE_ADJUST);
	}
	
	
	
	/** Adjusts the entity coordinates according to the given time.
	 * <br><br>
	 * The default implementation is as follows<br>
	 * 1. Calculate the current velocity with {@link #entityCalculateVelocity(long)}<br>
	 * 2. Calculate the current coordinates with {@link #entityCalculateCoordinates(long, IPoint2f)}<br>
	 * 3. Calculate the coordinates after collision with {@link #entityCalculateCollision(IPoint2f)}<br>
	 * 4. Assign the resulting coordinates with {@link #entityMove(IPoint2f)}<br>
	 * 
	 * @param timeMillis
	 * @param timePassedMillis
	 */
	public void entityAdjustCoordinateState(long timeMillis, long timePassedMillis) {
		// 1. Calculate velocity
		
		// pass current time or dt?
		IPoint2f calculatedVelocity = entityCalculateVelocity(timePassedMillis);
		
		
		// 2 Calculate new coordinates ?
		IPoint2f calculatedCoordinates = entityCalculateCoordinates(timePassedMillis, calculatedVelocity);
		
		
		// 2. Check collision for new coordinates
		IPoint2f coordinatesAfterCollision = entityCalculateCollision(calculatedCoordinates);
		
		
		// 3. Move entity
		entityMove(coordinatesAfterCollision);
		
	}
	
	
	/** Calculates the entity velocity according to the given time change.
	 * <br>
	 * The default implementation simply calls #getVelocity()
	 * 
	 * @return The new entity velocity after time.
	 */
	public IPoint2f entityCalculateVelocity(long dt) {
		// TODO: If has IPhysicsBody calculate physics?
		
		getVelocity(calculatedVelocityRe);
		return calculatedVelocityRe;
	}
	

	/** Calculates the entity coordinates according to the given time change and velocity.
	 * 
	 * @param dt
	 * @param velocity
	 * @return The new entity coordinates after time.
	 */
	public IPoint2f entityCalculateCoordinates(long dt, IPoint2f velocity) {
		float dtSec = dt / 1000f;
		
		float dx = dtSec * velocity.x();
		float dy = dtSec * velocity.y();
		
		return calculatedCoordinatesRe.set(getWorldX() + dx, getWorldY() + dy);
	}
	
	
	/** Calculates the entity collision and the resulting coordinates,
	 * according to the given destination coordinates.
	 * 
	 * @param coordinates
	 * @return The new entity coordinates after collision.
	 */
	public IPoint2f entityCalculateCollision(IPoint2f coordinates) {
		boolean canMove = checkCollisionInWorldspace(coordinates);

		if(canMove) {
			//calculatedCollisionRe.set(coordinates);
			return coordinates;
		}
		else {
			getWorldCoordinates(calculatedCollisionRe);
			return calculatedCollisionRe;
		}
	}
	
	
	// Collision with what though? -> Simple Rectangle?
	private void entityAdjustCollision(IPoint2f coordinates, IRectangle2f collision) {
		// Use?
	}
	
	
	/** Moves the entity to the given coordinates.
	 * 
	 * @param coordinates
	 */
	public void entityMove(IPoint2f coordinates) {

		// should use point.equals
		if(getWorldX() != coordinates.x() || getWorldY() != coordinates.y()) {
			setWorldCoordinates(coordinates);
		}
	}
	

	protected boolean checkCollisionInWorldspace(IPoint2f destinationCoordinates) {
		boolean canMove = true;
		
		IWorldspace entityWorldspace = getAssignedWorldspace();
		if(entityWorldspace != null) {
			
			canMove = entityWorldspace.entityCanMoveThereCheck(this, destinationCoordinates);
		}
		
		return canMove;
	}
	
	
	
	/** Adjusts the entity graphics according to the given time.
	 * <br><br>
	 * The default implementation works as follows<br>
	 * 1. If the image is enabled, it will be asked for a graphic ({@link #getGraphicsImage()}).<br>
	 * 2. If the image is disabled or the returned graphic is null, graphic base will be used.<br>
	 * 3. If the graphic base is null as well, the graphic value will be null.<br>
	 * <br> 
	 * Note: The graphic will only be set if it is different to the previous one.
	 * 
	 * @param timeMillis
	 * @param timePassedMillis
	 */
	public void entityAdjustGraphicState(long timeMillis, long timePassedMillis) {
		IBaseGraphic graphic = null;
		if(getGraphicsImage().isEnabled()) {
			graphic = getGraphicsImage().getImageGraphic(timeMillis);
		}
		
		if(graphic == null) {
			graphic = getGraphicBase();
		}
		
		if(graphic != getGraphic()) {
			setGraphic(graphic);
			
			getParticipation().notifyOfChange(CHANGE_ENTITY_GRAPHIC);
		}
	}
	
	
	/** Sets the graphic.
	 * 
	 * @param graphic
	 */
	public void setGraphic(IBaseGraphic graphic) {
		this.entityGraphic = graphic;
	}

	

	@Override
	public void assignWorldspace(IWorldspace worldspace) {
		this.assignedWorldspace = worldspace;
	}
	
	@Override
	public boolean hasWorldspaceAssigned() {
		return assignedWorldspace != null;
	}
	
	@Override
	public IWorldspace getAssignedWorldspace() {
		return assignedWorldspace;
	}
	
	@Override
	public boolean deassignWorldspace() {
		boolean hadWorldspace = assignedWorldspace != null;
		
		this.assignedWorldspace = null;
		return hadWorldspace;
	}
	
	
	@Override
	public boolean hasPayload() {
		return mPayload != null;
	}
	
	@Override
	public Object getPayload() {
		return mPayload;
	}
	
	@Override
	public <T> T getPayloadAs(Class<T> desiredClass) {
		if(desiredClass.isInstance(mPayload)) {
			return desiredClass.cast(mPayload); 
		}
		else {
			return null;
		}
	}
	
	
	@Override
	public boolean isActive() {
		return entityActive;
	}

	@Override
	public boolean isVisible() {
		return entityVisible;
	}
	
	@Override
	public float getHeight() {
		return entityHeight;
	}
	
	@Override
	public float getWidth() {
		return entityWidth;
	}
	
		
	@Override
	public IMutableRectangle2f getBounds(IMutableRectangle2f result) {
		return result.set(entityBounds);
	}
	
	@Override
	public IRectangle2f getBoundsBack() {
		return entityBounds;
	}

	
	@Override
	public IMutableRectangle2f getCollisionBounds(IMutableRectangle2f result) {
		return result.set(entityCollisionBounds);
	}
	
	@Override
	public IRectangle2f getCollisionBoundsBack() {
		return entityCollisionBounds;
	}
	
	
	@Override
	public IMutableRectangle2f getCollisionBox(IMutableRectangle2f result) {
		return result.set(entityCollisionBox);
	}
	
	@Override
	public IRectangle2f getCollisionBoxBack() {
		return entityCollisionBox;
	}

	
	@Override
	public float getOriginX() {
		return entityOrigin.x;
	}
	
	@Override
	public float getOriginY() {
		return entityOrigin.y;
	}
	
	@Override
	public IMutablePoint2f getOrigin(IMutablePoint2f result) {
		return result.set(entityOrigin);
	}
	
	
	@Override
	public float getRawWorldX() {
		return entityCoordinates.x - entityOrigin.x;
	}
	
	@Override
	public float getRawWorldY() {
		return entityCoordinates.y - entityOrigin.y;
	}
	
	@Override
	public IMutablePoint2f getRawWorldCoordinates(IMutablePoint2f result) {
		return result.set(getRawWorldX(), getRawWorldY());
	}
	

	@Override
	public float getWorldX() {
		return entityCoordinates.x;
	}

	@Override
	public float getWorldY() {
		return entityCoordinates.y;
	}

	@Override
	public IMutablePoint2f getWorldCoordinates(IMutablePoint2f result) {
		return result.set(entityCoordinates);
	}

	
	
	@Override
	public float getSpeedX() {
		return entitySpeedX;
	}
	
	@Override
	public float getSpeedY() {
		return entitySpeedY;
	}
	
	@Override
	public long getCurrentUpdateTime() {
		return currentUpdateTime;
	}
	
	@Override
	public long getPreviousUpdateTime() {
		return previousUpdateTime;
	}
	
	@Override
	public long getDeltaUpdateTime() {
		return currentUpdateTime - previousUpdateTime;
	}
	
	
	@Override
	public float getVelocityX() {
		return entityVelocity.x;
	}
	
	@Override
	public float getVelocityY() {
		return entityVelocity.y;
	}

	@Override
	public IMutablePoint2f getVelocity(IMutablePoint2f result) {
		return result.set(entityVelocity);		
	}
	

	@Override
	public void setVelocityX(float vx) {
		entityVelocity.setX(vx);
		
		getParticipation().notifyOfChange(CHANGE_ENTITY_VELOCITY);
	}
	
	@Override
	public void setVelocityY(float vy) {
		entityVelocity.setY(vy);
		
		getParticipation().notifyOfChange(CHANGE_ENTITY_VELOCITY);
	}
	
	@Override
	public void setVelocity(float vx, float vy) {
		entityVelocity.set(vx, vy);
		
		getParticipation().notifyOfChange(CHANGE_ENTITY_VELOCITY);
	}
	
	

	
	@Override
	public IBaseGraphic getGraphic() {
		return entityGraphic;
	}
	
	@Override
	public IBaseGraphic getGraphic(String name) {
		// TODO: Implement with IGraphicsImage or remove completely?
		return null;
	}
	
	@Override
	public void setGraphicBase(IBaseGraphic graphic) {
		this.entityGraphicBase = graphic;
		
		getParticipation().notifyOfChange(CHANGE_ENTITY_GRAPHIC_BASE);
	}
	
	@Override
	public IBaseGraphic getGraphicBase() {
		return entityGraphicBase;
	}

	@Override
	public IGraphicsImage getGraphicsImage() {
		return entityGraphicsImage;
	}
	

	
	@Override
	public void setActive(boolean isActive) {
		boolean notify = this.entityActive != isActive;
		this.entityActive = isActive;
		
		if(notify) {
			getParticipation().notifyOfChange(CHANGE_ENTITY_ACTIVE_STATUS);
		}
	}
	
	@Override
	public void setVisible(boolean isVisible) {
		boolean notify = this.entityVisible != isVisible;
		this.entityVisible = isVisible;
		
		if(notify) {
			getParticipation().notifyOfChange(CHANGE_ENTITY_VISIBLE_STATUS);
		}
	}

	
	@Override
	public void setWidth(float width) {
		this.entityWidth = width;
		entityBounds.setWidth(width);
		
		getParticipation().notifyOfChange(CHANGE_ENTITY_SIZE);
	}
	
	@Override
	public void setHeight(float height) {
		this.entityHeight = height;
		entityBounds.setHeight(height);
		
		getParticipation().notifyOfChange(CHANGE_ENTITY_SIZE);
	}
	
	@Override
	public void setSize(float width, float height) {
		this.entityWidth = width;
		this.entityHeight = height;
		
		entityBounds.setWidth(width);
		entityBounds.setHeight(height);
		
		getParticipation().notifyOfChange(CHANGE_ENTITY_SIZE);
	}

	
	@Override
	public void setOrigin(float originx, float originy) {
		this.entityOrigin.set(originx, originy);
		
		getParticipation().notifyOfChange(CHANGE_ENTITY_ORIGIN);
	}
	
	/*
	@Override
	public void setRotation(float rotation) {
		this.mRotation = rotation % 360f;
		
		/*
		// It does change your coordinates
		float rotLeft = 
		float rotX = mCoords.x - mOrigin.x Math.
		
		Math.toRadians(rotation);
		Math.sin
		mBounds.moveTo(mCoords.x - mOrigin.x, mCoords.y - mOrigin.y);
		mCollisionBounds.set(mCollisionBox.left + mBounds.left, mCollisionBox.top + mBounds.top, mCollisionBox.right + mBounds.left, mCollisionBox.bottom + mBounds.top);
		*//*
		
		//TODO: How to do rotation
		
		getParticipation().notifyOfChange(CHANGE_ENTITY_ROTATION);
		// WHat about bounds and collision bounds ? they need notifications too ?
	}
	*/
	
	
	@Override
	public void setCollisionBox(float left, float top, float right, float bottom) {
		this.entityCollisionBox.set(left, top, right, bottom);
		
		entityCollisionBounds.set(entityCollisionBox.left() + entityBounds.left(), entityCollisionBox.top() + entityBounds.top(), entityCollisionBox.right() + entityBounds.left(), entityCollisionBox.bottom() + entityBounds.top());
		
		getParticipation().notifyOfChange(CHANGE_ENTITY_COLLISION_BOX);
	}
	
	@Override
	public void setCollisionBox(IRectangle2f box) {
		this.entityCollisionBox.set(box);
		
		entityCollisionBounds.set(entityCollisionBox.left() + entityBounds.left(), entityCollisionBox.top() + entityBounds.top(), entityCollisionBox.right() + entityBounds.left(), entityCollisionBox.bottom() + entityBounds.top());
		
		getParticipation().notifyOfChange(CHANGE_ENTITY_COLLISION_BOX);
	}
	
	
	
	
	@Override
	public void setWorldCoordinates(IPoint2f worldCoords) {
		setWorldCoordinates(worldCoords.x(), worldCoords.y());
	}
	
	@Override
	public void setWorldCoordinates(float worldX, float worldY) {
		entityCoordinates.set(worldX, worldY);
		
		
		entityBounds.moveTo(entityCoordinates.x - entityOrigin.x, entityCoordinates.y - entityOrigin.y);
		entityCollisionBounds.set(entityCollisionBox.left() + entityBounds.left(), entityCollisionBox.top() + entityBounds.top(), entityCollisionBox.right() + entityBounds.left(), entityCollisionBox.bottom() + entityBounds.top());
		
		getParticipation().notifyOfChange(CHANGE_ENTITY_COORDINATES);
	}
	
	
	@Override
	public void setSpeed(float sx, float sy) {
		entitySpeedX = sx;
		entitySpeedY = sy;
		
		getParticipation().notifyOfChange(CHANGE_ENTITY_SPEED);
	}
	
	@Override
	public void setSpeedX(float sx) {
		entitySpeedX = sx;
		
		getParticipation().notifyOfChange(CHANGE_ENTITY_SPEED);
	}
	
	@Override
	public void setSpeedY(float sy) {
		entitySpeedY = sy;
		
		getParticipation().notifyOfChange(CHANGE_ENTITY_SPEED);
	}

	

	@Override
	public IObservation<IGameEntity, IObserver<IGameEntity>> getObservation() {
		return mParticipation;
	}
	
	@Override
	public void addInheritedObserver(IObserver<IGameEntity> observer) {
		mParticipation.addObserver(observer);
	}
	
	@Override
	public void removeInheritedObserver(IObserver<IGameEntity> observer) {
		mParticipation.removeObserver(observer);
	}
	

	@Override
	public IParticipation<IGameEntity, IObserver<IGameEntity>, IParticipant<IGameEntity>> getParticipation() {
		return mParticipation;
	}
	
	@Override
	public void addInheritedParticipant(IParticipant<IGameEntity> participant) {
		mParticipation.addParticipant(participant);
	}
	
	@Override
	public void removeInheritedParticipant(IParticipant<IGameEntity> participant) {
		mParticipation.removeParticipant(participant);
	}
	
	
	
	
	private class ParticipationHook implements IParticipationHook<IObserver<IGameEntity>, IParticipant<IGameEntity>> {
		
		
		@Override
		public boolean skipObservationNotificationHook(String change) {
			// If both the observer and participant lists are empty, skip the notification
			
			return getObservation().getObservers().isEmpty()
					&& getParticipation().getObservers().isEmpty()
					&& getParticipation().getParticipants().isEmpty();
		}
		
		@Override
		public boolean handleObserverHook(String change, IObserver<IGameEntity> observer) {
			if(observer.isGeneric()) {
				return false;
			}
			else {
				return CHANGE_LIST_ENTITY_SET.contains(change);	
			}
		}
		
		@Override
		public void notifyObserverHook(String change, IObserver<IGameEntity> observer) {
			if(!(observer instanceof IGameEntityObserver)) {
				/* Either
				 * 1. throw exception
				 * 2. simply return
				 * 3. call the generic version (current)
				 * 
				 */
				observer.subjectChanged(GameEntity.this, change);
				
				return;
			}
			IGameEntityObserver entityObserver = (IGameEntityObserver) observer;
			
			
			switch (change) {
			case CHANGE_ENTITY_ACTIVE_STATUS:
				entityObserver.entityActiveStatusChanged(GameEntity.this);
				break;
			case CHANGE_ENTITY_COORDINATES:
				entityObserver.entityCoordinatesChanged(GameEntity.this);
				break;
			case CHANGE_ENTITY_ORIGIN:
				entityObserver.entityOriginChanged(GameEntity.this);
				break;
			case CHANGE_ENTITY_SIZE:
				entityObserver.entitySizeChanged(GameEntity.this);
				break;
			case CHANGE_ENTITY_SPEED:
				entityObserver.entitySpeedChanged(GameEntity.this);
				break;
			case CHANGE_ENTITY_STATE_ADJUST:
				entityObserver.entityStateWasAdjusted(GameEntity.this);
				break;
			case CHANGE_ENTITY_VISIBLE_STATUS:
				entityObserver.entityVisibleStatusChanged(GameEntity.this);
				break;
			case CHANGE_ENTITY_COLLISION_BOX:
				entityObserver.entityCollisionBoxChanged(GameEntity.this);
				break;
			case CHANGE_ENTITY_VELOCITY:
				entityObserver.entityVelocityChanged(GameEntity.this);
				break;
			case CHANGE_ENTITY_GRAPHIC:
				entityObserver.entityGraphicChanged(GameEntity.this);
				break;
			case CHANGE_ENTITY_GRAPHIC_BASE:
				entityObserver.entityGraphicBaseChanged(GameEntity.this);
				break;
			default:
				// Nothing
			}
		}
		
		
		@Override
		public boolean skipParticipationNotificationHook(String change) {
			// If the participation list is empty, skip the notification
			return getParticipation().getParticipants().isEmpty();
		}
		
		@Override
		public boolean handleParticipantHook(String change, IParticipant<IGameEntity> participant) {
			if(participant.isGeneric()) {
				return false;
			}
			else {
				return CHANGE_LIST_ENTITY_SET.contains(change);
			}
		}
		
		@Override
		public boolean notifyParticipantHook(String change, IParticipant<IGameEntity> participant) {
			if(!(participant instanceof IGameEntityParticipant)) {
				
				return participant.onSubjectChange(GameEntity.this, change);
			}
			IGameEntityParticipant entityParticipant = (IGameEntityParticipant) participant;
			
			
			switch (change) {
			case CHANGE_ENTITY_ACTIVE_STATUS:
				return entityParticipant.onEntityActiveStatusChange(GameEntity.this);
			case CHANGE_ENTITY_COORDINATES:
				return entityParticipant.onEntityCoordinatesChange(GameEntity.this);
			case CHANGE_ENTITY_ORIGIN:
				return entityParticipant.onEntityOriginChange(GameEntity.this);
			case CHANGE_ENTITY_SIZE:
				return entityParticipant.onEntitySizeChange(GameEntity.this);
			case CHANGE_ENTITY_SPEED:
				return entityParticipant.onEntitySpeedChange(GameEntity.this);
			case CHANGE_ENTITY_STATE_ADJUST:
				return entityParticipant.onEntityStateAdjust(GameEntity.this);
			case CHANGE_ENTITY_VISIBLE_STATUS:
				return entityParticipant.onEntityVisibleStatusChange(GameEntity.this);
			case CHANGE_ENTITY_COLLISION_BOX:
				return entityParticipant.onEntityCollisionBoxChange(GameEntity.this);
			case CHANGE_ENTITY_VELOCITY:
				return entityParticipant.onEntityVelocityChange(GameEntity.this);
			case CHANGE_ENTITY_GRAPHIC:
				return entityParticipant.onEntityGraphicChange(GameEntity.this);
			case CHANGE_ENTITY_GRAPHIC_BASE:
				return entityParticipant.onEntityGraphicChange(GameEntity.this);
			default:
				return false;
			}
		}
		
		
	}
	


}
