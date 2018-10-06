package com.tokelon.toktales.core.game.model;

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
import com.tokelon.toktales.core.game.model.IPoint2f.IMutablePoint2f;

public class ExtendedCamera extends Camera implements IExtendedCamera {

	private final IParticipation<ICamera, IObserver<ICamera>, IParticipant<ICamera>> mParticipation;


	private int mCoordinateState = STATE_COORDINATE_STILL;
	private int mCoordinateStatePrevious = STATE_COORDINATE_NONE;
	

	
	private IGameMotion setMotion;
	private IMotionCallback<IGameMotion> setMotionCallback;

	
	private long initMotionTime;

	private IGameMotion currentMotion;
	private IMotionCallback<IGameMotion> currentMotionCallback;

	
	private final TimeTrackerTool motionTimer = new TimeTrackerTool();


	private final Point2fImpl coordsStill = new Point2fImpl();
	private final Point2fImpl coordsMotion = new Point2fImpl();

	private final Point2fImpl newCoords = new Point2fImpl();

	private final Point2fImpl entityCoordsRe = new Point2fImpl();

	
	public ExtendedCamera() {
		mParticipation = new Participation<ICamera, IObserver<ICamera>, IParticipant<ICamera>>(this, new ParticipationHook());
	}
	
	
	
	@Override
	public void setWorldCoordinates(float worldX, float worldY) {
		coordsStill.set(worldX - coordsMotion.x, worldY - coordsMotion.y);

		super.setWorldCoordinates(worldX, worldY);
		
		getParticipation().notifyOfChange(CHANGE_EXTENDED_CAMERA_STATIC_COORDINATES);
	}
	
	
	
	@Override
	public void cameraAdjustCoordinateState(long timeMillis, long timePassedMillis) {
		adjustCoordinateState(timeMillis);
	}

	
	private synchronized void adjustCoordinateState(long timeMillis) {
		boolean notify;
		
		getCoordinatesAtTime(timeMillis, newCoords);		// Adjust coords
		getWorldCoordinates(entityCoordsRe);
		
		if(newCoords.equals(entityCoordsRe)) {
			notify = false;
		}
		else {
			notify = true;
			super.setWorldCoordinates(newCoords);	// call super to not modify still coords
		}
		
		

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
			getCoordinatesAtTime(timeMillis, newCoords);
			getWorldCoordinates(entityCoordsRe);
			
			notify = notify || !newCoords.equals(entityCoordsRe);
			
			if(!newCoords.equals(entityCoordsRe)) {
				super.setWorldCoordinates(newCoords);	// call super to not modify still coords
			}
		}

		
		if(notify) {
			getParticipation().notifyOfChange(CHANGE_CAMERA_COORDINATES);
		}
	}
	
	
	private void finishMotion() {
		getWorldCoordinates(coordsStill); // Set the still coords to the current ones
		setCoordinateState(mCoordinateStatePrevious);

		
		// Callback
		if(currentMotionCallback != null) {
			currentMotionCallback.motionFinished(currentMotion);
			currentMotionCallback = null;
		}

		currentMotion = null;
		
		getParticipation().notifyOfChange(CHANGE_EXTENDED_CAMERA_COORDINATE_STATE);
	}
	

	
	private void initMotion(long timeMillis) {
		this.currentMotion = setMotion;
		this.currentMotionCallback = setMotionCallback;
		
		this.setMotion = null;
		this.setMotionCallback = null;
		
		motionTimer.start(timeMillis);
		
		setCoordinateState(STATE_COORDINATE_MOVING);
		
		getParticipation().notifyOfChange(CHANGE_EXTENDED_CAMERA_COORDINATE_STATE);
	}
	

	protected void setCoordinateState(int coordinateState) {
		mCoordinateStatePrevious = mCoordinateState;
		mCoordinateState = coordinateState;
	}
	
	protected void getCoordinatesAtTime(long timeMillis, IMutablePoint2f result) {
		
		switch (mCoordinateState) {
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
	
	
	
	
	@Override
	public void getStaticCoordinates(IMutablePoint2f result) {
		result.set(coordsStill);
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
	public int getCoordinateState() {
		return mCoordinateState;
	}
	

	@Override
	public synchronized void setStaticCoordinates(IPoint2f staticCoords) {
		setStaticCoordinates(staticCoords.x(), staticCoords.y());
	}

	@Override
	public synchronized void setStaticCoordinates(float staticX, float staticY) {
		coordsStill.set(staticX, staticY);
		
		setWorldCoordinates(coordsStill.x + coordsMotion.x, coordsStill.y + coordsMotion.y);
		
		getParticipation().notifyOfChange(CHANGE_EXTENDED_CAMERA_STATIC_COORDINATES);
	}
	


	@Override
	public synchronized void setMotion(IGameMotion motion) {
		if(mCoordinateState == STATE_COORDINATE_MOVING) {
			finishMotion();
		}
		
		setMotion = motion;
		setMotionCallback = null;
		
		getParticipation().notifyOfChange(CHANGE_EXTENDED_CAMERA_MOTION);
	}

	@Override
	public synchronized void setMotion(IGameMotion motion, IMotionCallback<IGameMotion> callback) {
		if(mCoordinateState == STATE_COORDINATE_MOVING) {
			finishMotion();
		}
		
		setMotion = motion;
		setMotionCallback = callback;
		
		getParticipation().notifyOfChange(CHANGE_EXTENDED_CAMERA_MOTION);
	}

	@Override
	public synchronized void startMotion(IGameMotion motion, long timeMillis) {
		if(mCoordinateState == STATE_COORDINATE_MOVING) {
			finishMotion();
		}
		
		setMotion = motion;
		setMotionCallback = null;
		initMotionTime = timeMillis;
		
		getParticipation().notifyOfChange(CHANGE_EXTENDED_CAMERA_MOTION);
	}

	@Override
	public synchronized void startMotion(IGameMotion motion, long timeMillis, IMotionCallback<IGameMotion> callback) {
		if(mCoordinateState == STATE_COORDINATE_MOVING) {
			finishMotion();
		}
		
		setMotion = motion;
		setMotionCallback = callback;
		initMotionTime = timeMillis;
		
		getParticipation().notifyOfChange(CHANGE_EXTENDED_CAMERA_MOTION);
	}
	
	
	
	
	@Override
	public IObservation<ICamera, IObserver<ICamera>> getExtendedCameraObservation() {
		return mParticipation;
	}
	
	@Override
	public IParticipation<ICamera, IObserver<ICamera>, IParticipant<ICamera>> getExtendedCameraParticipation() {
		return mParticipation;
	}
	
	private class ParticipationHook implements IParticipationHook<IObserver<ICamera>, IParticipant<ICamera>> {

		@Override
		public boolean skipObservationNotificationHook(String change) {
			return getExtendedCameraObservation().getObservers().isEmpty()
					&& getExtendedCameraParticipation().getObservers().isEmpty()
					&& getExtendedCameraParticipation().getParticipants().isEmpty();
		}

		@Override
		public boolean handleObserverHook(String change, IObserver<ICamera> observer) {
			if(observer.isGeneric()) {
				return false;
			}
			else {
				return CHANGE_LIST_EXTENDED_CAMERA_SET.contains(change);
			}
		}

		@Override
		public void notifyObserverHook(String change, IObserver<ICamera> observer) {
			if(!(observer instanceof IExtendedCameraObserver)) {
				observer.subjectChanged(ExtendedCamera.this, change);
				return;
			}
			IExtendedCameraObserver extendedCameraObserver = (IExtendedCameraObserver) observer;
			
			switch (change) {
			case CHANGE_EXTENDED_CAMERA_COORDINATE_STATE:
				extendedCameraObserver.extendedCameraCoordinateStateChanged(ExtendedCamera.this);
				break;
			case CHANGE_EXTENDED_CAMERA_MOTION:
				extendedCameraObserver.extendedCameraMotionChanged(ExtendedCamera.this);
				break;
			case CHANGE_EXTENDED_CAMERA_STATIC_COORDINATES:
				extendedCameraObserver.extendedCameraStaticCoordinatesChanged(ExtendedCamera.this);
				break;
			default:
				// Nothing
			}
		}

		
		@Override
		public boolean skipParticipationNotificationHook(String change) {
			return getExtendedCameraParticipation().getParticipants().isEmpty();
		}

		@Override
		public boolean handleParticipantHook(String change, IParticipant<ICamera> participant) {
			if(participant.isGeneric()) {
				return false;
			}
			else {
				return CHANGE_LIST_EXTENDED_CAMERA_SET.contains(change);
			}
		}

		@Override
		public boolean notifyParticipantHook(String change, IParticipant<ICamera> participant) {
			if(!(participant instanceof IExtendedCameraParticipant)) {
				return participant.onSubjectChange(ExtendedCamera.this, change);
			}
			IExtendedCameraParticipant extendedCameraParticipant = (IExtendedCameraParticipant) participant;
			
			switch (change) {
			case CHANGE_EXTENDED_CAMERA_COORDINATE_STATE:
				return extendedCameraParticipant.onExtendedCameraCoordinateStateChange(ExtendedCamera.this);
			case CHANGE_EXTENDED_CAMERA_MOTION:
				return extendedCameraParticipant.onExtendedCameraMotionChange(ExtendedCamera.this);
			case CHANGE_EXTENDED_CAMERA_STATIC_COORDINATES:
				return extendedCameraParticipant.onExtendedCameraStaticCoordinatesChange(ExtendedCamera.this);
			default:
				return false;
			}
		}
		
	}
	
	
}
