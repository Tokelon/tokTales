package com.tokelon.toktales.core.game.model.entity;

import java.util.HashMap;
import java.util.Map;

import com.tokelon.toktales.core.game.graphic.IBaseGraphic;
import com.tokelon.toktales.core.game.graphic.animation.IAnimationCallback;
import com.tokelon.toktales.core.game.graphic.animation.IGameAnimation;
import com.tokelon.toktales.core.game.logic.ITimeTrackerTool;
import com.tokelon.toktales.core.game.logic.TimeTrackerTool;
import com.tokelon.toktales.core.game.logic.motion.IGameMotion;
import com.tokelon.toktales.core.game.logic.motion.IMotionCallback;
import com.tokelon.toktales.core.game.logic.observers.IObservation;
import com.tokelon.toktales.core.game.logic.observers.IObserver;
import com.tokelon.toktales.core.game.logic.observers.IParticipant;
import com.tokelon.toktales.core.game.logic.observers.IParticipation;
import com.tokelon.toktales.core.game.logic.observers.Participation;
import com.tokelon.toktales.core.game.logic.observers.IBaseParticipation.IParticipationHook;
import com.tokelon.toktales.core.game.model.IPoint2f;
import com.tokelon.toktales.core.game.model.Point2fImpl;
import com.tokelon.toktales.core.game.model.IPoint2f.IMutablePoint2f;

public class ExtendedGameEntity extends GameEntity implements IExtendedGameEntity {
	
	
	private final IParticipation<IGameEntity, IObserver<IGameEntity>, IParticipant<IGameEntity>> mParticipation;
	

	private int mGraphicState = STATE_GRAPHIC_STATIC;
	private int mGraphicStatePrevious = STATE_GRAPHIC_NONE;
	
	private final Map<String, IBaseGraphic> mAssignedGraphicsMap = new HashMap<String, IBaseGraphic>();
	private final Map<String, IGameAnimation> mAssignedAnimationsMap = new HashMap<String, IGameAnimation>();
	
	private IBaseGraphic mStaticGraphic;


	private final TimeTrackerTool animationTimer = new TimeTrackerTool();
	
	private IGameAnimation setAnimation;
	private IAnimationCallback<IGameAnimation> setAnimationCallback;
	
	
	private long initAnimationTime;
	private IGameAnimation currentAnimation;
	private IAnimationCallback<IGameAnimation> currentAnimationCallback;
	
	
	
	
	private int mCoordinateState = STATE_COORDINATE_STILL;
	private int mCoordinateStatePrevious = STATE_COORDINATE_NONE;
	
	private final Point2fImpl coordsMotion = new Point2fImpl();
	private final Point2fImpl coordsStill = new Point2fImpl();
	private final Point2fImpl newCoords = new Point2fImpl();

	private final Point2fImpl entityCoordsRe = new Point2fImpl();
	
	
	private IGameMotion setMotion;
	private IMotionCallback<IGameMotion> setMotionCallback;
	private long initMotionTime;
	private IMotionCallback<IGameMotion> currentMotionCallback;
	private IGameMotion currentMotion;

	private final TimeTrackerTool motionTimer = new TimeTrackerTool();

	
	public ExtendedGameEntity() {
		mParticipation = new Participation<IGameEntity, IObserver<IGameEntity>, IParticipant<IGameEntity>>(this, new ParticipationHook());
	}
	
	
	
	
	@Override
	public void setWorldCoordinates(float worldX, float worldY) {
		float dx = worldX - getWorldX();
		float dy = worldY - getWorldY();
		
		// As above: we cannot apply the current motion here, so we just add the difference
		//coordsStill.set(worldX - coordsMotion.x, worldY - coordsMotion.y);
		coordsStill.set(coordsStill.x + dx, coordsStill.y + dy);
		

		super.setWorldCoordinates(worldX, worldY);
		
		getParticipation().notifyOfChange(CHANGE_EXTENDED_ENTITY_STATIC_COORDINATES);
	}
	
	

	@Override
	public void entityAdjustCoordinateState(long timeMillis, long timePassedMillis) {
		// Do not call super, instead override with our own implementation
		adjustCoordinateState(timeMillis);
	}
	
	
	private synchronized void adjustCoordinateState(long timeMillis) {
		
		boolean notify = assignNewCoordinates(timeMillis);
		
		
		
		boolean calcAgainAfterFinish = false;
		
		// Finish motion if stopped
		boolean motionFinished = mCoordinateState == STATE_COORDINATE_MOVING
				&& currentMotion.getState(motionTimer.timePassed(timeMillis)) == IGameMotion.STATE_FINISHED;
		
		if(motionFinished || setMotion != null) {
			if(mCoordinateState == STATE_COORDINATE_MOVING) {	// Only finish motion if there is one
				finishMotion();
			}
			
			calcAgainAfterFinish = true;
		}
		
		
		// Init new motion
		if(setMotion != null) {
			long motionTime = initMotionTime == 0 ? timeMillis : initMotionTime;

			initMotion(motionTime);
			initMotionTime = 0;
		}

		
		if(calcAgainAfterFinish) {
			// calculate new motion values
			notify = notify || assignNewCoordinates(timeMillis);
		}
		
		
		if(notify) {
			getParticipation().notifyOfChange(CHANGE_ENTITY_COORDINATES);
		}
		
	}
	

	protected void setCoordinateState(int coordinateState) {
		mCoordinateStatePrevious = mCoordinateState;
		mCoordinateState = coordinateState;
	}
	
	protected void getCoordinatesAtTime(long timeMillis, int coordinateState, IMutablePoint2f result) {
		
		switch (coordinateState) {
		case STATE_COORDINATE_STILL:
			result.set(coordsStill);
			break;
		case STATE_COORDINATE_MOVING:
			int dt = (int) motionTimer.timePassed(timeMillis);
			currentMotion.getPosition(dt, coordsMotion);
			result.set(coordsStill.x + coordsMotion.x, coordsStill.y + coordsMotion.y);
			break;
		default:
			break;
		}
		
	}

	
	/**
	 * @param timeMillis
	 * @return Whether the coordinates were changed or not.
	 */
	protected boolean assignNewCoordinates(long timeMillis) {

		getCoordinatesAtTime(timeMillis, mCoordinateState, newCoords);		// New coordinates
		
		getWorldCoordinates(entityCoordsRe);
		if(newCoords.equals(entityCoordsRe)) {
			return false;	// If the coordinates are unchanged, return false here
		}

		
		boolean canMove = checkCollisionInWorldspace(newCoords);

		if(canMove) {
			// Moving is possible

			// important - calling the super method here to make sure that coordsStill is not being modified
			super.setWorldCoordinates(newCoords);
		}
		
		return canMove;
	}
	

	private void initMotion(long timeMillis) {
		this.currentMotion = setMotion;
		this.currentMotionCallback = setMotionCallback;
		
		this.setMotion = null;
		this.setMotionCallback = null;
		
		motionTimer.start(timeMillis);
		
		setCoordinateState(STATE_COORDINATE_MOVING);
		
		
		if(currentMotionCallback != null) {
			currentMotionCallback.motionStarted(currentMotion);
		}
		
		getParticipation().notifyOfChange(CHANGE_EXTENDED_ENTITY_COORDINATE_STATE);
	}

	private void finishMotion() {
		getWorldCoordinates(coordsStill); // Set the still coords to the current ones
		coordsMotion.set(0.0f, 0.0f);	// Reset motion coordinates
		setCoordinateState(mCoordinateStatePrevious);

		
		// Callback
		if(currentMotionCallback != null) {
			currentMotionCallback.motionFinished(currentMotion);
			currentMotionCallback = null;
		}

		currentMotion = null;
		
		getParticipation().notifyOfChange(CHANGE_EXTENDED_ENTITY_COORDINATE_STATE);
	}
	
	
	@Override
	public int getCoordinateState() {
		return mCoordinateState;
	}
	
	

	@Override
	public synchronized void setMotion(IGameMotion motion) {
		if(mCoordinateState == STATE_COORDINATE_MOVING) {
			finishMotion();
		}
		
		setMotion = motion;
		setMotionCallback = null;
		
		getParticipation().notifyOfChange(CHANGE_EXTENDED_ENTITY_MOTION);
	}
	
	@Override
	public synchronized void setMotion(IGameMotion motion, IMotionCallback<IGameMotion> callback) {
		if(mCoordinateState == STATE_COORDINATE_MOVING) {
			finishMotion();
		}
		
		setMotion = motion;
		setMotionCallback = callback;
		
		getParticipation().notifyOfChange(CHANGE_EXTENDED_ENTITY_MOTION);
	}
	

	@Override
	public synchronized void startMotion(IGameMotion motion, long timeMillis) {
		if(mCoordinateState == STATE_COORDINATE_MOVING) {
			finishMotion();
		}
		
		setMotion = motion;
		setMotionCallback = null;
		initMotionTime = timeMillis;
		
		getParticipation().notifyOfChange(CHANGE_EXTENDED_ENTITY_MOTION);
	}
	
	@Override
	public synchronized void startMotion(IGameMotion motion, long timeMillis, IMotionCallback<IGameMotion> callback) {
		if(mCoordinateState == STATE_COORDINATE_MOVING) {
			finishMotion();
		}
		
		setMotion = motion;
		setMotionCallback = callback;
		initMotionTime = timeMillis;
		
		getParticipation().notifyOfChange(CHANGE_EXTENDED_ENTITY_MOTION);
	}
	

	@Override
	public IGameMotion getMotion() {
		return currentMotion;
	}
	

	@Override
	public ITimeTrackerTool getMotionTimer() {
		return motionTimer;
	}

	
	
	@Override
	public void setStaticCoordinates(IPoint2f staticCoords) {
		setStaticCoordinates(staticCoords.x(), staticCoords.y());
	}

	@Override
	public void setStaticCoordinates(float staticX, float staticY) {
		
		float dx = staticX - coordsStill.x;
		float dy = staticY - coordsStill.y;
		coordsStill.set(staticX, staticY);

		// We cannot apply the motion coords (coordsMotion) here!! They may have changed from the value before!
		// And They need to pass the canMove test, in assignNewCoordinates()
		//mCoords.set(coordsStill.x + coordsMotion.x, coordsStill.y + coordsMotion.y);
		// Rather, we calculate the difference in the still coords and add that to the mCoords
		
		
		// Again, call super and do not modify coordsStill here
		super.setWorldCoordinates(getWorldX() + dx, getWorldY() + dy);
		
		getParticipation().notifyOfChange(CHANGE_EXTENDED_ENTITY_STATIC_COORDINATES);
		getParticipation().notifyOfChange(CHANGE_ENTITY_COORDINATES);
	}

	
	@Override
	public float getStaticX() {
		return coordsStill.x;
	}
	
	@Override
	public float getStaticY() {
		return coordsStill.y;
	}
	
	@Override
	public void getStaticCoordinates(IMutablePoint2f result) {
		result.set(coordsStill);
	}
	
	


	
	@Override
	public void entityAdjustGraphicState(long timeMillis, long timePassedMillis) {
		// override
		adjustGraphicState(timeMillis);
	}
	
	
	private synchronized void adjustGraphicState(long timeMillis) {
		boolean notify;

		IBaseGraphic newGraphic = getGraphicAtTime(timeMillis);
		notify = newGraphic != getGraphic();
		setGraphic(newGraphic);
		
		
		boolean calcAgainAfterFinish = false;
		
		boolean animationFinished = mGraphicState == STATE_GRAPHIC_ANIMATION
				&& currentAnimation.getState(animationTimer.timePassed(timeMillis)) == IGameAnimation.STATE_FINISHED;
		
		if(animationFinished || setAnimation != null) {
			if(mGraphicState == STATE_GRAPHIC_ANIMATION) {	// Only finish animation if there actually is one
				finishAnimation();
			}
			
			calcAgainAfterFinish = true;
		}

		
		if(setAnimation != null) {
			long animTime = initAnimationTime == 0 ? timeMillis : initAnimationTime;
			
			initAnimation(animTime);
			initAnimationTime = 0;
		}
		

		if(calcAgainAfterFinish) {
			// calculate new animation values
			newGraphic = getGraphicAtTime(timeMillis);
			notify = notify || newGraphic != getGraphic();
			setGraphic(newGraphic);
		}
		
		
		// Only call notify once
		if(notify) {
			getParticipation().notifyOfChange(CHANGE_ENTITY_GRAPHIC);
		}
		
	}
	

	private void initAnimation(long timeMillis) {
		this.currentAnimation = setAnimation;
		this.currentAnimationCallback = setAnimationCallback;
		
		this.setAnimation = null;
		this.setAnimationCallback = null;
		
		animationTimer.start(timeMillis);
		
		setGraphicState(STATE_GRAPHIC_ANIMATION);
		
		
		if(currentAnimationCallback != null) {
			currentAnimationCallback.animationStarted(currentAnimation);
		}
		
		getParticipation().notifyOfChange(CHANGE_EXTENDED_ENTITY_GRAPHIC_STATE);
	}
	
	
	private void finishAnimation() {
		setGraphicState(mGraphicStatePrevious);

		
		// Callback
		if(currentAnimationCallback != null) {
			currentAnimationCallback.animationFinished(currentAnimation);
			currentAnimationCallback = null;
		}
		
		currentAnimation = null;
		
		getParticipation().notifyOfChange(CHANGE_EXTENDED_ENTITY_GRAPHIC_STATE);
	}
	

	protected void setGraphicState(int graphicState) {
		mGraphicStatePrevious = mGraphicState;
		mGraphicState = graphicState;
	}
	
	
	protected IBaseGraphic getGraphicAtTime(long timeMillis) {
		
		switch (mGraphicState) {
		case STATE_GRAPHIC_STATIC:
			return mStaticGraphic;
		case STATE_GRAPHIC_ANIMATION:
			return currentAnimation.getKeyframe(animationTimer.timePassed(timeMillis));
		default:
			break;
		}
		
		return null;
	}
	
	
	
	@Override
	public void setStaticGraphic(IBaseGraphic graphic) {
		this.mStaticGraphic = graphic;
		
		getParticipation().notifyOfChange(CHANGE_EXTENDED_ENTITY_STATIC_GRAPHIC);
	}
	
	@Override
	public synchronized void setAnimation(IGameAnimation animation) {
		setAnimation = animation;
		setAnimationCallback = null;
		
		getParticipation().notifyOfChange(CHANGE_EXTENDED_ENTITY_ANIMATION);
	}
	@Override
	public synchronized void setAnimation(IGameAnimation animation, IAnimationCallback<IGameAnimation> callback) {
		setAnimation = animation;
		setAnimationCallback = callback;
		
		getParticipation().notifyOfChange(CHANGE_EXTENDED_ENTITY_ANIMATION);
	}
	
	
	@Override
	public synchronized void startAnimation(IGameAnimation animation, long timeMillis) {
		setAnimation = animation;
		setAnimationCallback = null;
		initAnimationTime = timeMillis;
		
		getParticipation().notifyOfChange(CHANGE_EXTENDED_ENTITY_ANIMATION);
	}
	
	@Override
	public synchronized void startAnimation(IGameAnimation animation, long timeMillis, IAnimationCallback<IGameAnimation> callback) {
		setAnimation = animation;
		setAnimationCallback = callback;
		initAnimationTime = timeMillis;
		
		getParticipation().notifyOfChange(CHANGE_EXTENDED_ENTITY_ANIMATION);
	}
	
	
	@Override
	public IBaseGraphic getStaticGraphic() {
		return mStaticGraphic;
	}

	@Override
	public IGameAnimation getAnimation() {
		return currentAnimation;
	}
	
	
	@Override
	public ITimeTrackerTool getAnimationTimer() {
		return animationTimer;
	}
	

	@Override
	public int getGraphicState() {
		return mGraphicState;
	}
	
	
	@Override
	public IBaseGraphic getAssignedGraphic(String graphicId) {
		return mAssignedGraphicsMap.get(graphicId);
	}
	
	@Override
	public IGameAnimation getAssignedAnimation(String animationId) {
		return mAssignedAnimationsMap.get(animationId);
	}
	
	
	@Override
	public void setAssignedGraphic(String graphicId, IBaseGraphic graphic) {
		mAssignedGraphicsMap.put(graphicId, graphic);
	}
	@Override
	public void setAssignedAnimation(String animationId, IGameAnimation animation) {
		mAssignedAnimationsMap.put(animationId, animation);
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
	public IObservation<IGameEntity, IObserver<IGameEntity>> getExtendedGameEntityObservation() {
		return mParticipation;
	}
	
	@Override
	public IParticipation<IGameEntity, IObserver<IGameEntity>, IParticipant<IGameEntity>> getExtendedGameEntityParticipation() {
		return mParticipation;
	}
	
	
	private class ParticipationHook implements IParticipationHook<IObserver<IGameEntity>, IParticipant<IGameEntity>> {

		@Override
		public boolean skipObservationNotificationHook(String change) {
			return getExtendedGameEntityObservation().getObservers().isEmpty()
					&& getExtendedGameEntityParticipation().getObservers().isEmpty()
					&& getExtendedGameEntityParticipation().getParticipants().isEmpty();
		}

		@Override
		public boolean handleObserverHook(String change, IObserver<IGameEntity> observer) {
			if(observer.isGeneric()) {
				return false;	
			}
			else {
				return CHANGE_LIST_EXTENDED_ENTITY_SET.contains(change);
			}
		}

		@Override
		public void notifyObserverHook(String change, IObserver<IGameEntity> observer) {
			if(!(observer instanceof IExtendedGameEntityObserver)) {
				observer.subjectChanged(ExtendedGameEntity.this, change);
				return;
			}
			IExtendedGameEntityObserver extendedObserver = (IExtendedGameEntityObserver) observer;
			
			switch (change) {
			case CHANGE_EXTENDED_ENTITY_ANIMATION:
				extendedObserver.extendedEntityAnimationChanged(ExtendedGameEntity.this);
				break;
			case CHANGE_EXTENDED_ENTITY_COORDINATE_STATE:
				extendedObserver.extendedEntityCoordinateStateChanged(ExtendedGameEntity.this);
				break;
			case CHANGE_EXTENDED_ENTITY_GRAPHIC_STATE:
				extendedObserver.extendedEntityGraphicStateChanged(ExtendedGameEntity.this);
				break;
			case CHANGE_EXTENDED_ENTITY_MOTION:
				extendedObserver.extendedEntityMotionChanged(ExtendedGameEntity.this);
				break;
			case CHANGE_EXTENDED_ENTITY_STATIC_COORDINATES:
				extendedObserver.extendedEntityStaticCoordinatesChanged(ExtendedGameEntity.this);
				break;
			case CHANGE_EXTENDED_ENTITY_STATIC_GRAPHIC:
				extendedObserver.extendedEntityStaticGraphicChanged(ExtendedGameEntity.this);
				break;
			default:
				// Nothing
			}
		}

		@Override
		public boolean skipParticipationNotificationHook(String change) {
			return getExtendedGameEntityParticipation().getParticipants().isEmpty();
		}

		@Override
		public boolean handleParticipantHook(String change, IParticipant<IGameEntity> participant) {
			if(participant.isGeneric()) {
				return false;
			}
			else {
				return CHANGE_LIST_EXTENDED_ENTITY_SET.contains(change);
			}
		}

		@Override
		public boolean notifyParticipantHook(String change, IParticipant<IGameEntity> participant) {
			if(!(participant instanceof IExtendedGameEntityParticipant)) {
				return participant.onSubjectChange(ExtendedGameEntity.this, change);
			}
			IExtendedGameEntityParticipant extendedParticipant = (IExtendedGameEntityParticipant) participant;
			
			switch (change) {
			case CHANGE_EXTENDED_ENTITY_ANIMATION:
				return extendedParticipant.onExtendedEntityAnimationChange(ExtendedGameEntity.this);
			case CHANGE_EXTENDED_ENTITY_COORDINATE_STATE:
				return extendedParticipant.onExtendedEntityCoordinateStateChange(ExtendedGameEntity.this);
			case CHANGE_EXTENDED_ENTITY_GRAPHIC_STATE:
				return extendedParticipant.onExtendedEntityGraphicStateChange(ExtendedGameEntity.this);
			case CHANGE_EXTENDED_ENTITY_MOTION:
				return extendedParticipant.onExtendedEntityMotionChange(ExtendedGameEntity.this);
			case CHANGE_EXTENDED_ENTITY_STATIC_COORDINATES:
				return extendedParticipant.onExtendedEntityStaticCoordinatesChange(ExtendedGameEntity.this);
			case CHANGE_EXTENDED_ENTITY_STATIC_GRAPHIC:
				return extendedParticipant.onExtendedEntityStaticGraphicChange(ExtendedGameEntity.this);
			default:
				return false;
			}
		}
		
	}

	
	
}
