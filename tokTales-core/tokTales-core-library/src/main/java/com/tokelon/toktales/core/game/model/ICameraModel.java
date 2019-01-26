package com.tokelon.toktales.core.game.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.tokelon.toktales.core.game.logic.observers.IObservation;
import com.tokelon.toktales.core.game.logic.observers.IObserver;
import com.tokelon.toktales.core.game.logic.observers.IParticipable;
import com.tokelon.toktales.core.game.logic.observers.IParticipant;
import com.tokelon.toktales.core.game.logic.observers.IParticipation;

public interface ICameraModel extends ICameraState, IParticipable<ICameraModel> {
	// Maybe move participation/observation back into Camera
	

	public static final String CHANGE_CAMERA_ORIGIN = "change_camera_origin";
	public static final String CHANGE_CAMERA_SIZE = "change_camera_size";
	public static final String CHANGE_CAMERA_ZOOM = "change_camera_zoom";
	public static final String CHANGE_CAMERA_ASPECT_RATIO = "change_camera_aspect_ratio";
	public static final String CHANGE_CAMERA_ORIENTATION = "change_camera_orientation";
	public static final String CHANGE_CAMERA_COORDINATES = "change_camera_coordinates";
	public static final String CHANGE_CAMERA_VELOCITY = "change_camera_velocity";
	public static final String CHANGE_CAMERA_SPEED = "change_camera_speed";

	
	public static final String[] CHANGE_LIST_CAMERA_MODEL =
	{
		CHANGE_CAMERA_ORIGIN, CHANGE_CAMERA_SIZE, CHANGE_CAMERA_ZOOM, CHANGE_CAMERA_ASPECT_RATIO, CHANGE_CAMERA_ORIENTATION,
		CHANGE_CAMERA_COORDINATES, CHANGE_CAMERA_VELOCITY, CHANGE_CAMERA_SPEED
	};
	
	public static final Set<String> CHANGE_LIST_CAMERA_MODEL_SET = new HashSet<String>(Arrays.asList(CHANGE_LIST_CAMERA_MODEL));
	

	@Override
	public IObservation<ICameraModel, ICameraModelObserver> getObservation();
	
	public interface ICameraModelObserver extends IObserver<ICameraModel> {
		
		@Override
		public default ICameraModelObserver getObservationInterest(ICameraModel subject, String change) { return this; }
		
		@Override
		public default void subjectChanged(ICameraModel subject, String change) { }
		
		public default void cameraOriginChanged(ICameraModel camera) { }
		public default void cameraSizeChanged(ICameraModel camera) { }
		public default void cameraZoomChanged(ICameraModel camera) { }
		public default void cameraAspectRatioChanged(ICameraModel camera) { }
		public default void cameraOrientationChanged(ICameraModel camera) { }
		public default void cameraCoordinatesChanged(ICameraModel camera) { }
		public default void cameraStateWasAdjusted(ICameraModel camera) { }
		public default void cameraVelocityChanged(ICameraModel camera) { }
		public default void cameraSpeedChanged(ICameraModel camera) { }
		
	}
	
	
	@Override
	public IParticipation<ICameraModel, ICameraModelObserver, ICameraModelParticipant> getParticipation();
	
	public interface ICameraModelParticipant extends ICameraModelObserver, IParticipant<ICameraModel> {
		
		@Override
		public default ICameraModelParticipant getParticipationInterest(ICameraModel subject, String change) { return this; }
		
		@Override
		public default boolean onSubjectChange(ICameraModel subject, String change) { return false;	}
		
		
		public default boolean onCameraOriginChange(ICameraModel camera) { return false; }
		public default boolean onCameraSizeChange(ICameraModel camera) { return false; }
		public default boolean onCameraZoomChange(ICameraModel camera) { return false; }
		public default boolean onCameraAspectRatioChange(ICameraModel camera) { return false; }
		public default boolean onCameraOrientationChange(ICameraModel camera) { return false; }
		public default boolean onCameraCoordinatesChange(ICameraModel camera) { return false; }
		public default boolean onCameraStateAdjust(ICameraModel camera) { return false; }
		public default boolean onCameraVelocityChange(ICameraModel camera) { return false; }
		public default boolean onCameraSpeedChange(ICameraModel camera) { return false; }
		
	}
	
}
