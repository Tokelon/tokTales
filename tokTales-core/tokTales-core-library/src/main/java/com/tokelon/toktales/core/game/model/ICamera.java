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
	*/
	
	/*
	public int getTranslationX();
	public int getTranslationY();
	public int getTranslation(IMutablePoint result);
	*/
	
	//public void rotate();
	//public void scale();

	
	
	public static final String CHANGE_CAMERA_ADJUST_STATE = "change_camera_adjust_state";

	public static final String[] CHANGE_LIST_CAMERA =
	{
		CHANGE_CAMERA_ADJUST_STATE
	};
	
	public static final Set<String> CHANGE_LIST_CAMERA_SET = new HashSet<String>(Arrays.asList(CHANGE_LIST_CAMERA));
	
	
	public static final int ON_CAMERA_YES = 1;
	public static final int ON_CAMERA_NO = 2;
	public static final int ON_CAMERA_PARTIAL = 3;
	
	
	
	
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
	public IObservation<ICamera, ICameraObserver> getObservation();
	
	public interface ICameraObserver extends IObserver<ICamera> {
		
		@Override
		public default ICameraObserver getObservationInterest(ICamera subject, String change) { return this; }
		
		@Override
		public default void subjectChanged(ICamera subject, String change) { }
		
		
		public default void cameraStateWasAdjusted(ICamera camera) { }
	}
	
	
	@Override
	public IParticipation<ICamera, ICameraObserver, ICameraParticipant> getParticipation();
	
	public interface ICameraParticipant extends ICameraObserver, IParticipant<ICamera> {
		
		@Override
		public default ICameraParticipant getParticipationInterest(ICamera subject, String change) { return this; }
		
		@Override
		public default boolean onSubjectChange(ICamera subject, String change) { return false; }
		
		
		public default boolean onCameraStateAdjust(ICamera camera) { return false; }
	}
	
}
