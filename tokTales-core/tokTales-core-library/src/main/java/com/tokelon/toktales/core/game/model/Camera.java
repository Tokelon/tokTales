package com.tokelon.toktales.core.game.model;

import javax.inject.Inject;

import com.tokelon.toktales.core.game.logic.observers.IBaseParticipation.IParticipationHook;
import com.tokelon.toktales.core.game.logic.observers.IObservation;
import com.tokelon.toktales.core.game.logic.observers.IParticipation;
import com.tokelon.toktales.core.game.logic.observers.Participation;
import com.tokelon.toktales.core.game.model.IPoint2f.IMutablePoint2f;
import com.tokelon.toktales.core.game.model.IRectangle2f.IMutableRectangle2f;

public class Camera extends AbstractCameraStateDecorator implements ICamera {
	// Maybe have the parameter ctor be the injected
	
	
	private final Point2fImpl calculatedCoordinatesRe = new Point2fImpl();
	private final Point2fImpl calculatedVelocityRe = new Point2fImpl();
	
	
	private long currentUpdateTime = 0L;

	private final Participation<ICamera, ICameraObserver, ICameraParticipant> participation;

	
	@Inject
	public Camera() {
		this(new CameraModel());
	}
	
	public Camera(ICameraModel model) {
		super(model);
		
		participation = new Participation<ICamera, ICameraObserver, ICameraParticipant>(this, new CameraParticipationHook());
	}
	

	
	@Override
	public synchronized void adjustState(long timeMillis) {
		long timePassedMillis = timeMillis - currentUpdateTime;

		cameraAdjustCoordinateState(timeMillis, timePassedMillis);
		
		currentUpdateTime = timeMillis;
		
		
		getParticipation().notifyOfChange(CHANGE_CAMERA_ADJUST_STATE);
	}
	

	public void cameraAdjustCoordinateState(long timeMillis, long timePassedMillis) {
		IPoint2f calculatedVelocity = cameraCalculateVelocity(timePassedMillis);
		
		IPoint2f calculatedCoordinates = cameraCalculateCoordinates(timePassedMillis, calculatedVelocity);

		cameraMove(calculatedCoordinates);
	}
	
	
	public IPoint2f cameraCalculateVelocity(long dt) {
		getVelocity(calculatedVelocityRe);
		return calculatedVelocityRe;
	}

	
	public IPoint2f cameraCalculateCoordinates(long dt, IPoint2f velocity) {
		float dtSec = dt / 1000f;
		
		float dx = dtSec * velocity.x();
		float dy = dtSec * velocity.y();
		
		return calculatedCoordinatesRe.set(getWorldX() + dx, getWorldY() + dy);
	}
	
	public void cameraMove(IPoint2f coordinates) {
		// should use point.equals
		if(getWorldX() != coordinates.x() || getWorldY() != coordinates.y()) {
			setWorldCoordinates(coordinates);
		}
	}

	
	@Override
	public long getLastUpdateTime() {
		return currentUpdateTime;
	}

	

	@Override
	public float worldToCameraX(float wx) {
		float resx = wx - getRawWorldX();
		//int resx = worldx - (mCoords.x - mOrigin.x);
	
		/*
		// Maybe return error values?
		if(worldx < mCoords.x) {
			// Outside camera (in negative)
			//resx = worldx - mCoords.x;
		}
		else if(worldx >= mCoords.x + mWidth) {
			// Outside camera (in positive)
			//resx = worldx - mCoords.x;
		}
		else {
			//resx = worldx - mCoords.x;
		}
		*/
		
		return resx;
	}

	@Override
	public float worldToCameraY(float wy) {
		float resy = wy - getRawWorldY();
		//int resy = (mCoords.y - mOrigin.y) - worldy;
		
		/*
		if(worldy < mCoords.y) {
			//resy = worldy - mCoords.y;
		}
		else if(worldy >= mCoords.y + mHeight) {
			//resy = worldy - mCoords.y;
		}
		else {
			//resy = worldy - mCoords.y;
		}
		*/
		
		return resy;
	}

	@Override
	public void worldToCamera(float wx, float wy, IMutablePoint2f cResult) {
		cResult.set(worldToCameraX(wx), worldToCameraY(wy));
	}
	
	@Override
	public void worldToCamera(IPoint2f wPoint, IMutablePoint2f cResult) {
		cResult.set(worldToCameraX(wPoint.x()), worldToCameraY(wPoint.y()));
	}
	
	@Override
	public void worldToCamera(IRectangle2f wRect, IMutableRectangle2f cResult) {
		/*
		if(flipYAxis)
		cRect.set(worldToCameraX(wRect.left)
				, worldToCameraY(cRect.bottom)	// bottom instead of top
				, worldToCameraX(cRect.right)
				, worldToCameraY(cRect.top));	// And top instead of bottom
		*/

		cResult.set(worldToCameraX(wRect.left())
				, worldToCameraY(wRect.top())
				, worldToCameraX(wRect.right())
				, worldToCameraY(wRect.bottom()));
	}
	
	

	@Override
	public float cameraToWorldX(float cx) {
		float resx = cx + getRawWorldX();
		return resx;
	}

	@Override
	public float cameraToWorldY(float cy) {
		float resy = cy + getRawWorldY();
		return resy;
	}

	@Override
	public void cameraToWorld(float cx, float cy, IMutablePoint2f wResult) {
		wResult.set(cameraToWorldX(cx), cameraToWorldY(cy));
	}

	@Override
	public void cameraToWorld(IPoint2f cPoint, IMutablePoint2f wResult) {
		wResult.set(cameraToWorldX(cPoint.x()), cameraToWorldY(cPoint.y()));
	}

	@Override
	public void cameraToWorld(IRectangle2f cRect, IMutableRectangle2f wResult) {
		wResult.set(
				cameraToWorldX(cRect.left()),
				cameraToWorldY(cRect.top()),
				cameraToWorldX(cRect.right()),
				cameraToWorldY(cRect.bottom())
				);
	}
	
	
	
	@Override
	public boolean isOnCamera(float wx, float wy) {
		return wx >= getWorldX() - getOriginX() && wx < getWorldX() + getOriginX()
				&& wy >= getWorldY() - getOriginY() && wy < getWorldY() + getOriginY();
		/*
		return worldx >= mCoords.x && worldx < mCoords.x + mWidth
				&& worldy >= mCoords.y && worldy < mCoords.y + mHeight;
		*/
	}
	
	
	@Override
	public int isOnCamera(IRectangle2f wRect) {
		
		boolean contains = wRect.left() >= getWorldX() - getOriginX() && wRect.right() < getWorldX() + getOriginX()
				&& wRect.bottom() >= getWorldY() - getOriginY() && wRect.top() < getWorldY() + getOriginY();
		
		if(contains) {
			return ON_CAMERA_YES;
		}
		
		
		/* This was wrong
		boolean intersects = wRect.left <= mCoords.x + mOrigin.x && mCoords.x - mOrigin.x <= wRect.right
				&& wRect.bottom <= mCoords.y + mOrigin.y && mCoords.y - mOrigin.y <= wRect.top;	// Fixed bug (mCoords.y - mOrigin.x)
		*/
		// TODO: Test that this actually works
		boolean intersects = wRect.left() <= getWorldX() + getOriginX() && getWorldX() - getOriginX() <= wRect.right()
				&& wRect.top() <= getWorldY() + getOriginY() && getWorldY() - getOriginY() <= wRect.bottom();
		
		
		if(intersects) {
			return ON_CAMERA_PARTIAL;
		}
		
		
		return ON_CAMERA_NO;
	}
	
	

	// Normally observation and participation would be in a separate class...
	//private class ParticipationHook implements IParticipationHook<IObserver<ICamera>, IParticipant<ICamera>> { }
	// Probably just remove it all - it's so complicated that it's not worth it
	// This implementation for ICamera in particular does not allow generic obervers and participators


	@Override
	public IObservation<ICamera, ICameraObserver> getObservation() {
		return participation;
	}

	@Override
	public IParticipation<ICamera, ICameraObserver, ICameraParticipant> getParticipation() {
		return participation;
	}


	protected class CameraParticipationHook implements IParticipationHook<ICameraObserver, ICameraParticipant> {
		
		@Override
		public boolean skipObservationNotificationHook(String change) {
			return getParticipation().getObservers().isEmpty() && getParticipation().getParticipants().isEmpty();
		}

		@Override
		public boolean handleObserverHook(String change, ICameraObserver observer) {
			if(observer.isGeneric()) {
				return false;
			}
			else {
				return ICamera.CHANGE_LIST_CAMERA_SET.contains(change);
			}
		}

		@Override
		public void notifyObserverHook(String change, ICameraObserver observer) {
			switch (change) {
			case ICamera.CHANGE_CAMERA_ASPECT_RATIO:
				observer.cameraAspectRatioChanged(Camera.this);
				break;
			case ICamera.CHANGE_CAMERA_COORDINATES:
				observer.cameraCoordinatesChanged(Camera.this);
				break;
			case ICamera.CHANGE_CAMERA_ORIENTATION:
				observer.cameraOrientationChanged(Camera.this);
				break;
			case ICamera.CHANGE_CAMERA_ORIGIN:
				observer.cameraOriginChanged(Camera.this);
				break;
			case ICamera.CHANGE_CAMERA_SIZE:
				observer.cameraSizeChanged(Camera.this);
				break;
			case ICamera.CHANGE_CAMERA_ZOOM:
				observer.cameraZoomChanged(Camera.this);
				break;
			case ICamera.CHANGE_CAMERA_VELOCITY:
				observer.cameraVelocityChanged(Camera.this);
				break;
			case ICamera.CHANGE_CAMERA_ADJUST_STATE:
				observer.cameraStateWasAdjusted(Camera.this);
				break;
			default:
				// Nothing
			}
		}


		@Override
		public boolean skipParticipationNotificationHook(String change) {
			return getParticipation().getParticipants().isEmpty();
		}

		@Override
		public boolean handleParticipantHook(String change, ICameraParticipant participant) {
			if(participant.isGeneric()) {
				return false;
			}
			else {
				return ICamera.CHANGE_LIST_CAMERA_SET.contains(change);
			}
		}

		@Override
		public boolean notifyParticipantHook(String change, ICameraParticipant participant) {
			switch (change) {
			case ICamera.CHANGE_CAMERA_ASPECT_RATIO:
				return participant.onCameraAspectRatioChange(Camera.this);
			case ICamera.CHANGE_CAMERA_COORDINATES:
				return participant.onCameraCoordinatesChange(Camera.this);
			case ICamera.CHANGE_CAMERA_ORIENTATION:
				return participant.onCameraOrientationChange(Camera.this);
			case ICamera.CHANGE_CAMERA_ORIGIN:
				return participant.onCameraOriginChange(Camera.this);
			case ICamera.CHANGE_CAMERA_SIZE:
				return participant.onCameraSizeChange(Camera.this);
			case ICamera.CHANGE_CAMERA_ZOOM:
				return participant.onCameraZoomChange(Camera.this);
			case ICamera.CHANGE_CAMERA_VELOCITY:
				return participant.onCameraVelocityChange(Camera.this);
			case ICamera.CHANGE_CAMERA_ADJUST_STATE:
				return participant.onCameraStateAdjust(Camera.this);
			default:
				return false;
			}
		}
	}
	
}
