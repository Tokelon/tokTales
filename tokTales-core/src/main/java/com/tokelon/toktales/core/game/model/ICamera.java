package com.tokelon.toktales.core.game.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.tokelon.toktales.core.game.logic.observers.IObservation;
import com.tokelon.toktales.core.game.logic.observers.IObserver;
import com.tokelon.toktales.core.game.logic.observers.IParticipable;
import com.tokelon.toktales.core.game.logic.observers.IParticipant;
import com.tokelon.toktales.core.game.logic.observers.IParticipation;
import com.tokelon.toktales.core.game.model.IPoint2f.IMutablePoint2f;
import com.tokelon.toktales.core.game.model.IRectangle2f.IMutableRectangle2f;

public interface ICamera extends IParticipable<ICamera> {

	/*
	 * Maybe refactor to use matrices
	 */

	// Implement speed?

	
	public static final int ON_CAMERA_YES = 1;
	public static final int ON_CAMERA_NO = 2;
	public static final int ON_CAMERA_PARTIAL = 3;
	
	
	public static final String CHANGE_CAMERA_ORIGIN = "change_camera_origin";
	public static final String CHANGE_CAMERA_SIZE = "change_camera_size";
	public static final String CHANGE_CAMERA_ZOOM = "change_camera_zoom";
	public static final String CHANGE_CAMERA_ASPECT_RATIO = "change_camera_aspect_ratio";
	public static final String CHANGE_CAMERA_ORIENTATION = "change_camera_orientation";
	public static final String CHANGE_CAMERA_COORDINATES = "change_camera_coordinates";
	public static final String CHANGE_CAMERA_ADJUST_STATE = "change_camera_adjust_state";
	public static final String CHANGE_CAMERA_VELOCITY = "change_camera_velocity";
	
	
	public static final String[] CHANGE_LIST_CAMERA =
	{
		CHANGE_CAMERA_ORIGIN, CHANGE_CAMERA_SIZE, CHANGE_CAMERA_ZOOM, CHANGE_CAMERA_ASPECT_RATIO, CHANGE_CAMERA_ORIENTATION,
		CHANGE_CAMERA_COORDINATES, CHANGE_CAMERA_ADJUST_STATE, CHANGE_CAMERA_VELOCITY
	};
	
	public static final Set<String> CHANGE_LIST_CAMERA_SET = new HashSet<String>(Arrays.asList(CHANGE_LIST_CAMERA));
	

	
	
	public void adjustState(long timeMillis);

	
	/**
	 * 
	 * @return A value >= 1.0
	 */
	public float getAspectRatio();
	
	
	/**
	 * 
	 * @return A value > 0.0
	 */
	public float getZoom();

	
	public boolean hasPortraitOrientation();

	
	public float getWidth();
	public float getHeight();
	
	
	public float getOriginX();
	public float getOriginY();
	public IMutablePoint2f getOrigin(IMutablePoint2f result);
	
	public float getVelocityX();
	public float getVelocityY();
	public IMutablePoint2f getVelocity(IMutablePoint2f result);
	
	
	public float getRawWorldX();
	public float getRawWorldY();
	public IMutablePoint2f getRawWorldCoordinates(IMutablePoint2f result);
	
	public float getWorldX();
	public float getWorldY();
	public IMutablePoint2f getWorldCoordinates(IMutablePoint2f result);

	
	public IMutableRectangle2f getWorldBounds(IMutableRectangle2f result);
	
	/** Useful when the result will not be assigned.
	 * <br><br>
	 * <b>Caution:</b> The result may be modified over time.
	 * <br>
	 * Preferable use of {@link #getWorldBounds(IMutableRectangle2f)} is recommended.
	 * 
	 * @return The backing object for the camera bounds.
	 */
	public IRectangle2f getWorldBoundsBack();
	
	
	
	public long getLastUpdateTime();

	
	
	
	
	/** Size is in world units. Resets the zoom to 1.0.
	 * 
	 * @param width
	 * @param height
	 */
	public void setSize(float width, float height);		// Same problem with centering ! Should we add a center boolean?
	
	// We set the zoom value here but what about centering ? (Its not happening)
	//public void setSize(float width, float height, float zoom);
	
	
	public void setPortraitOrientation(boolean portrait);
	
	/**
	 * 
	 * @param ratio A value >= 1.0
	 */
	public void setAspectRatio(float ratio);
	
	/**
	 * 
	 * @param zoom A value > 0. Default is 1.0.
	 * @param center
	 */
	public void setZoom(float zoom, boolean center);
	

	public void setVelocityX(float vx);
	public void setVelocityY(float vy);
	public void setVelocity(float vx, float vy);
	
	
	public void setWorldCoordinates(float worldX, float worldY);
	public void setWorldCoordinates(IPoint2f worldCoords);

	
	
	
	
	/*
	 * Map world coordinates to local coordinates
	 */
	
	/**
	 * 
	 * @param worldx
	 * @return A number in [0 and width)
	 */
	public float worldToCameraX(float wx);	// toScreenX()
	public float worldToCameraY(float wy);
	public void worldToCamera(float wx, float wy, IMutablePoint2f cResult);
	public void worldToCamera(IPoint2f wPoint, IMutablePoint2f cResult);
	public void worldToCamera(IRectangle2f wRect, IMutableRectangle2f cResult);
	
	
	public float cameraToWorldX(float cx);
	public float cameraToWorldY(float cy);
	public void cameraToWorld(float cx, float cy, IMutablePoint2f wResult);
	public void cameraToWorld(IPoint2f cPoint, IMutablePoint2f wResult);
	public void cameraToWorld(IRectangle2f cRect, IMutableRectangle2f wResult);
	
	

	public boolean isOnCamera(float wx, float wy);
	public int isOnCamera(IRectangle2f wRect);
	
	
	/*
	public int[] getViewMatrix()
	
	public void update();
	
	public void translate(int translatex, int translatey);
	*/
	
	/*
	public int getTranslationX();
	public int getTranslationY();
	public int getTranslation(IMutablePoint result);
	*/
	
	//public void rotate();
	
	//public void scale();
	
	
	
	
	@Override
	public IObservation<ICamera, ICameraObserver> getObservation();
	
	public interface ICameraObserver extends IObserver<ICamera> {
		
		@Override
		public ICameraObserver getObservationInterest(ICamera subject, String change);
		
		
		public void cameraOriginChanged(ICamera camera);
		public void cameraSizeChanged(ICamera camera);
		public void cameraZoomChanged(ICamera camera);
		public void cameraAspectRatioChanged(ICamera camera);
		public void cameraOrientationChanged(ICamera camera);
		public void cameraCoordinatesChanged(ICamera camera);
		public void cameraStateWasAdjusted(ICamera camera);
		public void cameraVelocityChanged(ICamera camera);
		
	}
	
	
	public abstract class CameraObserver implements ICameraObserver {
		
		@Override
		public ICameraObserver getObservationInterest(ICamera subject, String change) {
			return this;
		}
		
		@Override
		public void subjectChanged(ICamera subject, String change) {
			// Nothing
		}
		
		
		
		@Override
		public void cameraOriginChanged(ICamera camera) { }
		
		@Override
		public void cameraSizeChanged(ICamera camera) { }
		
		@Override
		public void cameraZoomChanged(ICamera camera) { }
		
		@Override
		public void cameraAspectRatioChanged(ICamera camera) { }
		
		@Override
		public void cameraOrientationChanged(ICamera camera) { }
		
		@Override
		public void cameraCoordinatesChanged(ICamera camera) { }
		
		@Override
		public void cameraStateWasAdjusted(ICamera camera) { }
		
		@Override
		public void cameraVelocityChanged(ICamera camera) {	}
	}
	
	
	
	@Override
	public IParticipation<ICamera, ICameraObserver, ICameraParticipant> getParticipation();
	
	public interface ICameraParticipant extends ICameraObserver, IParticipant<ICamera> {
		
		@Override
		public ICameraParticipant getParticipationInterest(ICamera subject, String change);
		
		
		
		public boolean onCameraOriginChange(ICamera camera);
		public boolean onCameraSizeChange(ICamera camera);
		public boolean onCameraZoomChange(ICamera camera);
		public boolean onCameraAspectRatioChange(ICamera camera);
		public boolean onCameraOrientationChange(ICamera camera);
		public boolean onCameraCoordinatesChange(ICamera camera);
		public boolean onCameraStateAdjust(ICamera camera);
		public boolean onCameraVelocityChange(ICamera camera);
		
	}
	
	public abstract class CameraParticipant extends CameraObserver implements ICameraParticipant {
		
		@Override
		public ICameraParticipant getParticipationInterest(ICamera subject, String change) {
			return this;
		}
		
		@Override
		public boolean onSubjectChange(ICamera subject, String change) {
			return false;
		}
		
		
		
		@Override
		public boolean onCameraOriginChange(ICamera camera) { return false; }

		@Override
		public boolean onCameraSizeChange(ICamera camera) {	return false; }

		@Override
		public boolean onCameraZoomChange(ICamera camera) { return false; }
		
		@Override
		public boolean onCameraAspectRatioChange(ICamera camera) { return false; }
		
		@Override
		public boolean onCameraOrientationChange(ICamera camera) { return false; }
		
		@Override
		public boolean onCameraCoordinatesChange(ICamera camera) { return false; }

		@Override
		public boolean onCameraStateAdjust(ICamera camera) { return false; }
		
		@Override
		public boolean onCameraVelocityChange(ICamera camera) { return false; }
	}
	
	
}
