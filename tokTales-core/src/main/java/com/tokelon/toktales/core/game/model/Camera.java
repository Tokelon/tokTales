package com.tokelon.toktales.core.game.model;

import com.tokelon.toktales.core.game.logic.observers.IObservation;
import com.tokelon.toktales.core.game.logic.observers.IParticipation;
import com.tokelon.toktales.core.game.logic.observers.Participation;
import com.tokelon.toktales.core.game.logic.observers.IBaseParticipation.IParticipationHook;
import com.tokelon.toktales.core.game.model.ICamera.ICameraObserver;
import com.tokelon.toktales.core.game.model.ICamera.ICameraParticipant;
import com.tokelon.toktales.core.game.model.IPoint2f.IMutablePoint2f;
import com.tokelon.toktales.core.game.model.IRectangle2f.IMutableRectangle2f;

public class Camera implements ICamera, IParticipationHook<ICameraObserver, ICameraParticipant> {
	
	private final Participation<ICamera, ICameraObserver, ICameraParticipant> mParticipation;
	
	
	private float cameraWidth = 0.0f;
	private float cameraHeight = 0.0f;
	
	private float cameraAspectRatio = 0.0f;
	
	private float cameraZoom = 1.0f;
	
	
	private final Point2fImpl cameraOrigin = new Point2fImpl();
	private final Point2fImpl cameraCoordinates = new Point2fImpl();
	private final Point2fImpl cameraVelocity = new Point2fImpl();

	private final Rectangle2fImpl cameraBounds = new Rectangle2fImpl();	

	
	private long currentUpdateTime = 0L;

	
	private final Point2fImpl calculatedCoordinatesRe = new Point2fImpl();
	private final Point2fImpl calculatedVelocityRe = new Point2fImpl();


	
	public Camera() {
		mParticipation = new Participation<ICamera, ICameraObserver, ICameraParticipant>(this, this);
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
	public float getAspectRatio() {
		return cameraAspectRatio;
	}
	
	@Override
	public float getZoom() {
		return cameraZoom;
	}


	@Override
	public boolean hasPortraitOrientation() {
		return getHeight() >= getWidth();
	}
	
	@Override
	public float getWidth() {
		return cameraWidth; //* (1.0f / mZoom);
	}
	
	@Override
	public float getHeight() {
		return cameraHeight;
	}

	
	@Override
	public float getOriginX() {
		return cameraOrigin.x;
	}
	
	@Override
	public float getOriginY() {
		return cameraOrigin.y;
	}
	
	@Override
	public IMutablePoint2f getOrigin(IMutablePoint2f result) {
		return result.set(cameraOrigin);
	}
	

	@Override
	public float getVelocityX() {
		return cameraVelocity.x;
	}
	
	@Override
	public float getVelocityY() {
		return cameraVelocity.y;
	}

	@Override
	public IMutablePoint2f getVelocity(IMutablePoint2f result) {
		return result.set(cameraVelocity);		
	}
	
	
	
	@Override
	public float getRawWorldX() {
		return cameraCoordinates.x - cameraOrigin.x;
	}
	
	@Override
	public float getRawWorldY() {
		return cameraCoordinates.y - cameraOrigin.y;
	}
	
	@Override
	public IMutablePoint2f getRawWorldCoordinates(IMutablePoint2f result) {
		return result.set(getRawWorldX(), getRawWorldY());
	}

	@Override
	public IMutableRectangle2f getWorldBounds(IMutableRectangle2f result) {
		return result.set(cameraBounds);
	}
	
	@Override
	public IRectangle2f getWorldBoundsBack() {
		return cameraBounds;
	}

	
	@Override
	public float getWorldX() {
		return cameraCoordinates.x;
	}

	@Override
	public float getWorldY() {
		return cameraCoordinates.y;
	}

	@Override
	public IMutablePoint2f getWorldCoordinates(IMutablePoint2f result) {
		return result.set(cameraCoordinates);
	}

	
	@Override
	public long getLastUpdateTime() {
		return currentUpdateTime;
	}

	
	
	
	
	@Override
	public void setSize(float width, float height) {
		setSize(width, height, 1.0f);
	}
	
	private void setSize(float width, float height, float zoom) {
		this.cameraWidth = width;
		this.cameraHeight = height;
		this.cameraZoom = zoom;
		
		this.cameraOrigin.x = width / 2.0f;
		this.cameraOrigin.y = height / 2.0f;
		
		this.cameraAspectRatio = calcAspectRatio(width, height);
		
		this.cameraBounds.setWidth(width);
		this.cameraBounds.setHeight(height);
		
		// Re-center camera by correcting the camera bounds
		cameraBounds.moveTo(cameraCoordinates.x - cameraOrigin.x, cameraCoordinates.y - cameraOrigin.y);

		
		getParticipation().notifyOfChange(CHANGE_CAMERA_SIZE);
		getParticipation().notifyOfChange(CHANGE_CAMERA_ORIGIN);
		getParticipation().notifyOfChange(CHANGE_CAMERA_ASPECT_RATIO);
	}
	
	private float calcAspectRatio(float width, float height) {
		float res;
		if(hasPortraitOrientation()) {	// TODO: Fix: width or height could be 0
			res = height / width;
		}
		else {
			res = width / height;
		}
		
		return res;
	}
	
	
	@Override
	public void setPortraitOrientation(boolean portrait) {
		if(portrait != hasPortraitOrientation()) {
			setSize(cameraHeight, cameraWidth, cameraZoom);
			
			getParticipation().notifyOfChange(CHANGE_CAMERA_ORIENTATION);
		}
	}
	
	@Override
	public void setAspectRatio(float ratio) {
		
		if(hasPortraitOrientation()) {
			// Portrait
			setSize(cameraWidth, ratio * cameraWidth, cameraZoom);
		}
		else {
			// Landscape
			setSize(ratio * cameraHeight, cameraHeight, cameraZoom);
		}
	}
	
	@Override
	public void setZoom(float zoom, boolean center) {

		float newWidth = getWidth() * cameraZoom / zoom;
		float newHeight = getHeight() * cameraZoom / zoom;
		
		
		/* TODO: Fix: It's already centering in setSize()
		if(center) {
			float diffWidth = Math.abs(mWidth - newWidth);
			float diffHeight = Math.abs(mHeight - newHeight);
			
			coordsStill.set(coordsStill.x + (diffWidth / 2.0f), coordsStill.y + (diffHeight / 2.0f));
			mCoords.set(coordsStill.x + coordsMotion.x, coordsStill.y + coordsMotion.y);
		}
		*/
		
		setSize(newWidth, newHeight, zoom);
		
		getParticipation().notifyOfChange(CHANGE_CAMERA_ZOOM);
	}
	

	@Override
	public void setVelocityX(float vx) {
		cameraVelocity.setX(vx);
		
		getParticipation().notifyOfChange(CHANGE_CAMERA_VELOCITY);
	}
	
	@Override
	public void setVelocityY(float vy) {
		cameraVelocity.setY(vy);
		
		getParticipation().notifyOfChange(CHANGE_CAMERA_VELOCITY);
	}
	
	@Override
	public void setVelocity(float vx, float vy) {
		cameraVelocity.set(vx, vy);
		
		getParticipation().notifyOfChange(CHANGE_CAMERA_VELOCITY);
	}
	
	
	
	
	

	@Override
	public void setWorldCoordinates(IPoint2f worldCoords) {
		setWorldCoordinates(worldCoords.x(), worldCoords.y());
	}
	
	@Override
	public void setWorldCoordinates(float worldX, float worldY) {
		cameraCoordinates.set(worldX, worldY);
		
		cameraBounds.moveTo(cameraCoordinates.x - cameraOrigin.x, cameraCoordinates.y - cameraOrigin.y);
		
		getParticipation().notifyOfChange(CHANGE_CAMERA_COORDINATES);
	}
	
	

	@Override
	public float worldToCameraX(float wx) {
		float rawCoordx = cameraCoordinates.x - cameraOrigin.x;
		
		float resx = wx - rawCoordx;
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
		float rawCoordy = cameraCoordinates.y - cameraOrigin.y;
		
		float resy = wy - rawCoordy;
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
		float rawCoordx = cameraCoordinates.x - cameraOrigin.x;
		float resx = cx + rawCoordx;
		return resx;
	}

	@Override
	public float cameraToWorldY(float cy) {
		float rawCoordy = cameraCoordinates.y - cameraOrigin.y;
		float resy = cy + rawCoordy;
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
		return wx >= cameraCoordinates.x - cameraOrigin.x && wx < cameraCoordinates.x + cameraOrigin.x
				&& wy >= cameraCoordinates.y - cameraOrigin.y && wy < cameraCoordinates.y + cameraOrigin.y;
		/*
		return worldx >= mCoords.x && worldx < mCoords.x + mWidth
				&& worldy >= mCoords.y && worldy < mCoords.y + mHeight;
		*/
	}
	
	
	@Override
	public int isOnCamera(IRectangle2f wRect) {
		
		boolean contains = wRect.left() >= cameraCoordinates.x - cameraOrigin.x && wRect.right() < cameraCoordinates.x + cameraOrigin.x
				&& wRect.bottom() >= cameraCoordinates.y - cameraOrigin.y && wRect.top() < cameraCoordinates.y + cameraOrigin.y;
		
		if(contains) {
			return ON_CAMERA_YES;
		}
		
		
		/* This was wrong
		boolean intersects = wRect.left <= mCoords.x + mOrigin.x && mCoords.x - mOrigin.x <= wRect.right
				&& wRect.bottom <= mCoords.y + mOrigin.y && mCoords.y - mOrigin.y <= wRect.top;	// Fixed bug (mCoords.y - mOrigin.x)
		*/
		// TODO: Test that this actually works
		boolean intersects = wRect.left() <= cameraCoordinates.x + cameraOrigin.x && cameraCoordinates.x - cameraOrigin.x <= wRect.right()
				&& wRect.top() <= cameraCoordinates.y + cameraOrigin.y && cameraCoordinates.y - cameraOrigin.y <= wRect.bottom();
		
		
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
		return mParticipation;
	}
	
	@Override
	public boolean skipObservationNotificationHook(String change) {
		return mParticipation.getObservers().isEmpty() && mParticipation.getParticipants().isEmpty();
	}
	
	@Override
	public boolean handleObserverHook(String change, ICameraObserver observer) {
		if(observer.isGeneric()) {
			return false;
		}
		else {
			return CHANGE_LIST_CAMERA_SET.contains(change);
		}
	}
	
	@Override
	public void notifyObserverHook(String change, ICameraObserver observer) {
		switch (change) {
		case CHANGE_CAMERA_ADJUST_STATE:
			observer.cameraStateWasAdjusted(this);
			break;
		case CHANGE_CAMERA_ASPECT_RATIO:
			observer.cameraAspectRatioChanged(this);
			break;
		case CHANGE_CAMERA_COORDINATES:
			observer.cameraCoordinatesChanged(this);
			break;
		case CHANGE_CAMERA_ORIENTATION:
			observer.cameraOrientationChanged(this);
			break;
		case CHANGE_CAMERA_ORIGIN:
			observer.cameraOriginChanged(this);
			break;
		case CHANGE_CAMERA_SIZE:
			observer.cameraSizeChanged(this);
			break;
		case CHANGE_CAMERA_ZOOM:
			observer.cameraZoomChanged(this);
			break;
		case CHANGE_CAMERA_VELOCITY:
			observer.cameraVelocityChanged(this);
			break;
		default:
			// Nothing
		}
	}
	
	
	@Override
	public IParticipation<ICamera, ICameraObserver, ICameraParticipant> getParticipation() {
		return mParticipation;
	}
	
	@Override
	public boolean skipParticipationNotificationHook(String change) {
		return mParticipation.getParticipants().isEmpty();
	}
	
	@Override
	public boolean handleParticipantHook(String change, ICameraParticipant participant) {
		if(participant.isGeneric()) {
			return false;
		}
		else {
			return CHANGE_LIST_CAMERA_SET.contains(change);
		}
	}

	@Override
	public boolean notifyParticipantHook(String change, ICameraParticipant participant) {
		switch (change) {
		case CHANGE_CAMERA_ADJUST_STATE:
			return participant.onCameraStateAdjust(this);
		case CHANGE_CAMERA_ASPECT_RATIO:
			return participant.onCameraAspectRatioChange(this);
		case CHANGE_CAMERA_COORDINATES:
			return participant.onCameraCoordinatesChange(this);
		case CHANGE_CAMERA_ORIENTATION:
			return participant.onCameraOrientationChange(this);
		case CHANGE_CAMERA_ORIGIN:
			return participant.onCameraOriginChange(this);
		case CHANGE_CAMERA_SIZE:
			return participant.onCameraSizeChange(this);
		case CHANGE_CAMERA_ZOOM:
			return participant.onCameraZoomChange(this);
		case CHANGE_CAMERA_VELOCITY:
			return participant.onCameraVelocityChange(this);
		default:
			return false;
		}
	}

	
}
