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

public interface ICamera extends ICameraState, IParticipable<ICamera> {
	/* Maybe refactor to use matrices
	 */
	/*
	public int[] getViewMatrix()
	
	public void update();
	
	public void translate(int translatex, int translatey);

	public void rotate();
	public void scale();
	
	public int getTranslationX();
	public int getTranslationY();
	public int getTranslation(IMutablePoint result);
	*/
	

	public static final int ON_CAMERA_YES = 1;
	public static final int ON_CAMERA_NO = 2;
	public static final int ON_CAMERA_PARTIAL = 3;
	

	public static final String CHANGE_CAMERA_ORIGIN = "change_camera_origin";
	public static final String CHANGE_CAMERA_SIZE = "change_camera_size";
	public static final String CHANGE_CAMERA_ZOOM = "change_camera_zoom";
	public static final String CHANGE_CAMERA_ASPECT_RATIO = "change_camera_aspect_ratio";
	public static final String CHANGE_CAMERA_ORIENTATION = "change_camera_orientation";
	public static final String CHANGE_CAMERA_COORDINATES = "change_camera_coordinates";
	public static final String CHANGE_CAMERA_VELOCITY = "change_camera_velocity";
	public static final String CHANGE_CAMERA_SPEED = "change_camera_speed";

	public static final String CHANGE_CAMERA_ADJUST_STATE = "change_camera_adjust_state";

	
	public static final String[] CHANGE_LIST_CAMERA =
	{
		CHANGE_CAMERA_ORIGIN, CHANGE_CAMERA_SIZE, CHANGE_CAMERA_ZOOM, CHANGE_CAMERA_ASPECT_RATIO, CHANGE_CAMERA_ORIENTATION,
		CHANGE_CAMERA_COORDINATES, CHANGE_CAMERA_VELOCITY, CHANGE_CAMERA_SPEED,
		CHANGE_CAMERA_ADJUST_STATE
	};
	
	public static final Set<String> CHANGE_LIST_CAMERA_SET = new HashSet<String>(Arrays.asList(CHANGE_LIST_CAMERA));
	
	
	
	
	
	public void adjustState(long timeMillis);

	public long getLastUpdateTime();

	
	public ICameraModel getModel();
	public void setModel(ICameraModel model);
	

	
	/* Map world coordinates to local coordinates */
	
	/**
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
	
	
	



	@Override
	public IObservation<ICamera, IObserver<ICamera>> getObservation();

	@Override
	public IParticipation<ICamera, IObserver<ICamera>, IParticipant<ICamera>> getParticipation();
	
	
	public interface ICameraObserver extends IObserver<ICamera> {
		
		@Override
		public default boolean isGeneric() { return false; }
		
		@Override
		public default ICameraObserver getObservationInterest(ICamera subject, String change) { return this; }
		
		@Override
		public default void subjectChanged(ICamera subject, String change) { }
		
		public default void cameraOriginChanged(ICamera camera) { }
		public default void cameraSizeChanged(ICamera camera) { }
		public default void cameraZoomChanged(ICamera camera) { }
		public default void cameraAspectRatioChanged(ICamera camera) { }
		public default void cameraOrientationChanged(ICamera camera) { }
		public default void cameraCoordinatesChanged(ICamera camera) { }
		public default void cameraVelocityChanged(ICamera camera) { }
		public default void cameraSpeedChanged(ICamera camera) { }
		
		public default void cameraStateWasAdjusted(ICamera camera) { }
	}
	

	public interface ICameraParticipant extends ICameraObserver, IParticipant<ICamera> {
		
		@Override
		public default ICameraParticipant getParticipationInterest(ICamera subject, String change) { return this; }
		
		@Override
		public default boolean onSubjectChange(ICamera subject, String change) { return false;	}
		
		
		public default boolean onCameraOriginChange(ICamera camera) { return false; }
		public default boolean onCameraSizeChange(ICamera camera) { return false; }
		public default boolean onCameraZoomChange(ICamera camera) { return false; }
		public default boolean onCameraAspectRatioChange(ICamera camera) { return false; }
		public default boolean onCameraOrientationChange(ICamera camera) { return false; }
		public default boolean onCameraCoordinatesChange(ICamera camera) { return false; }
		public default boolean onCameraVelocityChange(ICamera camera) { return false; }
		public default boolean onCameraSpeedChange(ICamera camera) { return false; }
		
		public default boolean onCameraStateAdjust(ICamera camera) { return false; }
	}
	
}
