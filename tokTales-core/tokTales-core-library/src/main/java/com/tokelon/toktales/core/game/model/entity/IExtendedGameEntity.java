package com.tokelon.toktales.core.game.model.entity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.tokelon.toktales.core.game.graphic.IBaseGraphic;
import com.tokelon.toktales.core.game.graphic.animation.IAnimationCallback;
import com.tokelon.toktales.core.game.graphic.animation.IGameAnimation;
import com.tokelon.toktales.core.game.logic.ITimeTrackerTool;
import com.tokelon.toktales.core.game.logic.motion.IGameMotion;
import com.tokelon.toktales.core.game.logic.motion.IMotionCallback;
import com.tokelon.toktales.core.game.logic.observers.IObservation;
import com.tokelon.toktales.core.game.logic.observers.IObserver;
import com.tokelon.toktales.core.game.logic.observers.IParticipant;
import com.tokelon.toktales.core.game.logic.observers.IParticipation;
import com.tokelon.toktales.core.game.model.IPoint2f;
import com.tokelon.toktales.core.game.model.IPoint2f.IMutablePoint2f;

public interface IExtendedGameEntity extends IGameEntity {


	/* TODO: Fix CHANGE_ENTITY_MOTION that is useless because it triggers when a new motion is set, but you cannot access the new motion
	 * Same with CHANGE_ENTITY_ANIMATION
	 * 
	 * IMotion getPendingMotion();
	 */
	
	public static final String CHANGE_EXTENDED_ENTITY_ANIMATION = "change_extended_entity_animation";
	public static final String CHANGE_EXTENDED_ENTITY_GRAPHIC_STATE = "change_extended_entity_graphic_state";
	public static final String CHANGE_EXTENDED_ENTITY_STATIC_GRAPHIC = "change_extended_entity_static_graphic";
	public static final String CHANGE_EXTENDED_ENTITY_MOTION = "change_extended_entity_motion";
	public static final String CHANGE_EXTENDED_ENTITY_COORDINATE_STATE = "change_extended_entity_coordinate_state";
	public static final String CHANGE_EXTENDED_ENTITY_STATIC_COORDINATES = "change_extended_entity_static_coordinates";
	
	
	public static final String[] CHANGE_LIST_EXTENDED_ENTITY = new String[]
	{
		CHANGE_EXTENDED_ENTITY_ANIMATION, CHANGE_EXTENDED_ENTITY_GRAPHIC_STATE, CHANGE_EXTENDED_ENTITY_STATIC_GRAPHIC,
		CHANGE_EXTENDED_ENTITY_MOTION, CHANGE_EXTENDED_ENTITY_COORDINATE_STATE, CHANGE_EXTENDED_ENTITY_STATIC_COORDINATES
	};
	
	public static final Set<String> CHANGE_LIST_EXTENDED_ENTITY_SET = new HashSet<String>(Arrays.asList(CHANGE_LIST_EXTENDED_ENTITY));
	
	

	public static final int STATE_GRAPHIC_NONE = 1;
	public static final int STATE_GRAPHIC_STATIC = 2;
	public static final int STATE_GRAPHIC_ANIMATION = 3;
	
	public static final int STATE_COORDINATE_NONE = 1;
	public static final int STATE_COORDINATE_STILL = 2;
	public static final int STATE_COORDINATE_MOVING = 3;
	
	
	
	/**
	 * 
	 * @return The current coordinate state.
	 */
	public int getCoordinateState();
	
	
	public void setMotion(IGameMotion motion);
	public void setMotion(IGameMotion motion, IMotionCallback<IGameMotion> callback);

	public void startMotion(IGameMotion motion, long timeMillis);
	public void startMotion(IGameMotion motion, long timeMillis, IMotionCallback<IGameMotion> callback);

	/**
	 * 
	 * @return The current running motion, or null if there is no motion.
	 */
	public IGameMotion getMotion();

	/**
	 * 
	 * @return The motion timer of this entity.
	 */
	public ITimeTrackerTool getMotionTimer();

	

	public float getStaticX();
	public float getStaticY();
	public void getStaticCoordinates(IMutablePoint2f result);


	// TODO: Check what exactly setStaticCoordinates does and what setWorldCoordinates does and document it.
	
	/** WARNING: You may want to use {@link #setWorldCoordinates(IPoint2f)}.
	 * 
	 * @param staticCoords
	 */
	public void setStaticCoordinates(IPoint2f staticCoords);
	
	/** WARNING: You may want to use {@link #setWorldCoordinates(float, float)}.
	 * 
	 * @param staticX
	 * @param staticY
	 */
	public void setStaticCoordinates(float staticX, float staticY);
	
	
	
	public IBaseGraphic getStaticGraphic();

	public IBaseGraphic getAssignedGraphic(String graphicId);

	public IGameAnimation getAssignedAnimation(String animationId);

	
	/**
	 * @return The current running animation, or null if there is no animation.
	 */
	public IGameAnimation getAnimation();
	
	/**
	 * @return The animation timer of this entity.
	 */
	public ITimeTrackerTool getAnimationTimer();
	
	/**
	 * @return The current graphic state.
	 */
	public int getGraphicState();
	

	
	public void setStaticGraphic(IBaseGraphic graphic);

	public void setAnimation(IGameAnimation animation);
	public void setAnimation(IGameAnimation animation, IAnimationCallback<IGameAnimation> callback);
	
	public void startAnimation(IGameAnimation animation, long timeMillis);
	public void startAnimation(IGameAnimation animation, long timeMillis, IAnimationCallback<IGameAnimation> callback);

	
	public void setAssignedGraphic(String graphicId, IBaseGraphic graphic);
	
	public void setAssignedAnimation(String animationId, IGameAnimation animation);

	
	
	public IObservation<IGameEntity, IObserver<IGameEntity>> getExtendedGameEntityObservation();
	
	public interface IExtendedGameEntityObserver extends IGameEntityObserver {
		
		public boolean hasExtendedInterest(IExtendedGameEntity subject, String change);
		
		public IExtendedGameEntityObserver getExtendedObservationInterest(IExtendedGameEntity subject, String change);
		
		public void subjectChangedExtended(IExtendedGameEntity subject, String change);
		
		

		public void extendedEntityAnimationChanged(IExtendedGameEntity entity);
		public void extendedEntityGraphicStateChanged(IExtendedGameEntity entity);
		public void extendedEntityGraphicChanged(IExtendedGameEntity entity);
		public void extendedEntityStaticGraphicChanged(IExtendedGameEntity entity);
		
		public void extendedEntityMotionChanged(IExtendedGameEntity entity);
		public void extendedEntityCoordinateStateChanged(IExtendedGameEntity entity);
		public void extendedEntityStaticCoordinatesChanged(IExtendedGameEntity entity);
		
		
	}
	
	public abstract class ExtendedGameEntityObserver extends GameEntityObserver implements IExtendedGameEntityObserver {
		
		@Override
		public IExtendedGameEntityObserver getExtendedObservationInterest(IExtendedGameEntity subject, String change) {
			return this;
		}
		
		@Override
		public void subjectChangedExtended(IExtendedGameEntity subject, String change) {
			// Nothing
		}
		
		
		
		@Override
		public void extendedEntityAnimationChanged(IExtendedGameEntity entity) { }
		
		@Override
		public void extendedEntityGraphicStateChanged(IExtendedGameEntity entity) { }
		
		@Override
		public void extendedEntityGraphicChanged(IExtendedGameEntity entity) { }
		
		@Override
		public void extendedEntityStaticGraphicChanged(IExtendedGameEntity entity) { }
		
		@Override
		public void extendedEntityMotionChanged(IExtendedGameEntity entity) { }
		
		@Override
		public void extendedEntityCoordinateStateChanged(IExtendedGameEntity entity) { }
		
		@Override
		public void extendedEntityStaticCoordinatesChanged(IExtendedGameEntity extendedEntity) { }
		
	}
	
	
	
	public IParticipation<IGameEntity, IObserver<IGameEntity>, IParticipant<IGameEntity>> getExtendedGameEntityParticipation();
	
	public interface IExtendedGameEntityParticipant extends IGameEntityParticipant, IExtendedGameEntityObserver {
		
		public IExtendedGameEntityParticipant getExtendedParticipationInterest(IExtendedGameEntity subject, String change);
		
		public boolean onSubjectChangeExtended(IExtendedGameEntity subject, String change);
		

		public boolean onExtendedEntityAnimationChange(IExtendedGameEntity entity);
		public boolean onExtendedEntityGraphicStateChange(IExtendedGameEntity entity);
		public boolean onExtendedEntityGraphicChange(IExtendedGameEntity entity);
		public boolean onExtendedEntityStaticGraphicChange(IExtendedGameEntity entity);
		
		public boolean onExtendedEntityMotionChange(IExtendedGameEntity entity);
		public boolean onExtendedEntityCoordinateStateChange(IExtendedGameEntity entity);
		public boolean onExtendedEntityStaticCoordinatesChange(IExtendedGameEntity entity);
		
	}
	
	public abstract class ExtendedGameEntityParticipant extends GameEntityParticipant implements IExtendedGameEntityParticipant {
		
		@Override
		public IExtendedGameEntityParticipant getExtendedParticipationInterest(IExtendedGameEntity subject, String change) {
			return this;
		}
		
		@Override
		public boolean onSubjectChangeExtended(IExtendedGameEntity subject, String change) {
			return false;
		}
		

		@Override
		public boolean onExtendedEntityAnimationChange(IExtendedGameEntity entity) { return false; }

		@Override
		public boolean onExtendedEntityGraphicStateChange(IExtendedGameEntity entity) { return false; }

		@Override
		public boolean onExtendedEntityGraphicChange(IExtendedGameEntity entity) { return false; }

		@Override
		public boolean onExtendedEntityStaticGraphicChange(IExtendedGameEntity entity) { return false; }

		@Override
		public boolean onExtendedEntityMotionChange(IExtendedGameEntity entity) { return false; }

		@Override
		public boolean onExtendedEntityCoordinateStateChange(IExtendedGameEntity entity) { return false; }

		@Override
		public boolean onExtendedEntityStaticCoordinatesChange(IExtendedGameEntity entity) { return false; }

	}
	
	
}
