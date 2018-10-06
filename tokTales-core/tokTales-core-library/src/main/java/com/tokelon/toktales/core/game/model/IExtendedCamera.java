package com.tokelon.toktales.core.game.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.tokelon.toktales.core.game.logic.ITimeTrackerTool;
import com.tokelon.toktales.core.game.logic.motion.IGameMotion;
import com.tokelon.toktales.core.game.logic.motion.IMotionCallback;
import com.tokelon.toktales.core.game.logic.observers.IObservation;
import com.tokelon.toktales.core.game.logic.observers.IObserver;
import com.tokelon.toktales.core.game.logic.observers.IParticipant;
import com.tokelon.toktales.core.game.logic.observers.IParticipation;
import com.tokelon.toktales.core.game.model.IPoint2f.IMutablePoint2f;

public interface IExtendedCamera extends ICamera {

	public static final int STATE_COORDINATE_NONE = 1;
	public static final int STATE_COORDINATE_STILL = 2;
	public static final int STATE_COORDINATE_MOVING = 3;
	

	public static final String CHANGE_EXTENDED_CAMERA_MOTION = "change_extended_camera_motion";
	public static final String CHANGE_EXTENDED_CAMERA_COORDINATE_STATE = "change_extended_camera_coordinate_state";
	public static final String CHANGE_EXTENDED_CAMERA_STATIC_COORDINATES = "change_extended_camera_static_coordinates";


	public static final String[] CHANGE_LIST_EXTENDED_CAMERA =
	{
		CHANGE_EXTENDED_CAMERA_MOTION, CHANGE_EXTENDED_CAMERA_COORDINATE_STATE, CHANGE_EXTENDED_CAMERA_STATIC_COORDINATES
	};
	
	public static final Set<String> CHANGE_LIST_EXTENDED_CAMERA_SET = new HashSet<String>(Arrays.asList(CHANGE_LIST_EXTENDED_CAMERA));
	
	
	

	public void getStaticCoordinates(IMutablePoint2f result);
	
	//public IMapPosition getPosition();


	/**
	 * 
	 * @return The current motion, or null if there is no motion.
	 */
	public IGameMotion getMotion();
	
	public ITimeTrackerTool getMotionTimer();
	
	public int getCoordinateState();
	
	
	
	
	public void setStaticCoordinates(IPoint2f staticCoords);
	public void setStaticCoordinates(float staticX, float staticY);
	
	
	public void setMotion(IGameMotion motion);
	public void setMotion(IGameMotion motion, IMotionCallback<IGameMotion> callback);
	
	
	public void startMotion(IGameMotion motion, long timeMillis);
	public void startMotion(IGameMotion motion, long timeMillis, IMotionCallback<IGameMotion> callback);
	
	
	
	
	public IObservation<ICamera, IObserver<ICamera>> getExtendedCameraObservation();
	
	public interface IExtendedCameraObserver extends ICameraObserver {
		
		public boolean hasExtendedCameraInterest(IExtendedCamera subject, String change);
		
		public IExtendedCameraObserver getExtendedCameraObservationInterest(IExtendedCamera subject, String change);
		
		public void subjectChangedExtendedCamera(IExtendedCamera subject, String change);
		

		public void extendedCameraMotionChanged(IExtendedCamera camera);
		public void extendedCameraCoordinateStateChanged(IExtendedCamera camera);
		public void extendedCameraStaticCoordinatesChanged(IExtendedCamera camera);
	}
	
	
	public abstract class ExtendedCameraObserver extends CameraObserver implements IExtendedCameraObserver {
		
		@Override
		public IExtendedCameraObserver getExtendedCameraObservationInterest(IExtendedCamera subject, String change) {
			return this;
		}
		
		@Override
		public void subjectChangedExtendedCamera(IExtendedCamera subject, String change) {
			// Nothing
		}

		
		@Override
		public void extendedCameraMotionChanged(IExtendedCamera camera) { }
		
		@Override
		public void extendedCameraCoordinateStateChanged(IExtendedCamera camera) { }

		@Override
		public void extendedCameraStaticCoordinatesChanged(IExtendedCamera camera) { }
	}
	
	
	
	public IParticipation<ICamera, IObserver<ICamera>, IParticipant<ICamera>> getExtendedCameraParticipation();
	
	public interface IExtendedCameraParticipant extends ICameraParticipant, IExtendedCameraObserver {
		
		public IExtendedCameraParticipant getExtendedCameraParticipationInterest(IExtendedCamera subject, String change);
		
		public boolean onSubjectChangeExtendedCamera(IExtendedCamera subject, String change);
		

		public boolean onExtendedCameraMotionChange(IExtendedCamera camera);
		public boolean onExtendedCameraCoordinateStateChange(IExtendedCamera camera);
		public boolean onExtendedCameraStaticCoordinatesChange(IExtendedCamera camera);
	}
	
	
	public abstract class ExtendedCameraParticipant extends CameraParticipant implements IExtendedCameraParticipant {
		
		@Override
		public IExtendedCameraParticipant getExtendedCameraParticipationInterest(IExtendedCamera subject, String change) {
			return this;
		}
		
		@Override
		public boolean onSubjectChangeExtendedCamera(IExtendedCamera subject, String change) {
			return false;
		}
		

		@Override
		public boolean onExtendedCameraMotionChange(IExtendedCamera camera) { return false; }
		
		@Override
		public boolean onExtendedCameraCoordinateStateChange(IExtendedCamera camera) { return false; }

		@Override
		public boolean onExtendedCameraStaticCoordinatesChange(IExtendedCamera camera) { return false; }
	}
	
	
}
