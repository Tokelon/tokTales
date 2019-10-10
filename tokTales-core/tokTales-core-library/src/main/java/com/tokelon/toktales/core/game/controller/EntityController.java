package com.tokelon.toktales.core.game.controller;

import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.game.graphic.animation.IGameAnimation;
import com.tokelon.toktales.core.game.logic.motion.IGameMotion;
import com.tokelon.toktales.core.game.logic.motion.IMotionCallback;
import com.tokelon.toktales.core.game.logic.motion.StraightAnimatedMotion;
import com.tokelon.toktales.core.game.model.IPlayer;
import com.tokelon.toktales.core.game.model.entity.IGameEntity;
import com.tokelon.toktales.core.game.world.ICompassDirection;
import com.tokelon.toktales.tools.core.objects.pools.SynchronizedPool;

public class EntityController {

	
	// TODO: Refactor, remove motion stuff
	
	private static final int STRAIGHT_DIRECTION_POOL_MAX_SIZE = 50;
	private final SynchronizedPool<StraightAnimatedMotion> straightDirectionPool;
	
	private final IMotionCallback<IGameMotion> motionCallback;
	
	
	public EntityController() {
		StraightDirectionFactory sdfactory = new StraightDirectionFactory();
		
		motionCallback = sdfactory;
		straightDirectionPool = new SynchronizedPool<StraightAnimatedMotion>(sdfactory, STRAIGHT_DIRECTION_POOL_MAX_SIZE);
	}
	
	
	
	
	
	public void startMoving(IGameEntity entity, int direction) {

		// Motion
		/*
		StraightAnimatedMotion motion = straightDirectionPool.newObject();
		
		motion.setVelocity(
				entity.getSpeedHorizontal() * ICompassDirection.Tools.horizontalVelocitySignFromDirection(direction),
				entity.getSpeedVertical() * ICompassDirection.Tools.verticalVelocitySignFromDirection(direction)
				);
		entity.setMotion(motion, motionCallback);
		*/
		
		entity.setVelocity(
				entity.getSpeedX() * ICompassDirection.Tools.horizontalVelocitySignFromDirection(direction),
				entity.getSpeedY() * ICompassDirection.Tools.verticalVelocitySignFromDirection(direction)
				);
		
		
		// Animation
		String animId = animationIDFromDirection(direction); // Can be nul
		IGameAnimation animation = entity.getGraphicsImage().getAssignedAnimation(animId);	// Is null allowed as a key? 
		
		if(animation != null) {
			// Could set different duration here
			//animation.setupFramesWithOneDuration(animation.getFrameCount(), frameDuration)
			
			/*
			 * Should use the GameAnimationWrapper or something else because the (assigned) animation is a shared object
			 * There could be synchronization problems if two threads try to changed it (its stateful)
			 */
			
			animation.resetAnimation();
			animation.enableLoop(true);
			entity.getGraphicsImage().startAnimation(animation, TokTales.getGame().getTimeManager().getGameTimeMillis());
			
			//motion.setAnimation(animation);
		}
		
	}
	
	public void stopMoving(IGameEntity entity) {
		/*
		IGameMotion motion = entity.getMotion();
		
		if(motion != null) {
			motion.stopMotion();
		}
		*/
		
		entity.setVelocity(0f, 0f);
	}
	
	
	//public void setFacingDirection(IGameEntity entity, int direction);
	
	
	
	public void moveBy(IGameEntity entity, int byX, int byY) {
		
	}
	
	public void moveTo(IGameEntity entity, int toX, int toY) {
		
	}
	
	
	private class StraightDirectionFactory implements SynchronizedPool.PoolObjectFactory<StraightAnimatedMotion>, IMotionCallback<IGameMotion> {

		@Override
		public StraightAnimatedMotion createObject() {
			return new StraightAnimatedMotion();
		}

		
		@Override
		public void motionStarted(IGameMotion motion) {
			// Nothing
		}
		
		@Override
		public void motionFinished(IGameMotion motion) {
			StraightAnimatedMotion am = (StraightAnimatedMotion) motion;
			
			if(am.hasAnimation()) {
				am.getAnimation().stopAnimation();
			}
			
			
			am.resetMotion();
			am.removeOrigin();
			am.removeDestination();
			am.removeAnimation();
			straightDirectionPool.free(am);
		}
		
	}
	
	
	
	private static String animationIDFromDirection(int compassDirection) {
		String res = null;
		switch (compassDirection) {
		case ICompassDirection.NORTH_WEST:
		case ICompassDirection.NORTH:
		case ICompassDirection.NORTH_EAST:
			res = IPlayer.ANIMATION_WALK_UP;
			break;
		case ICompassDirection.EAST:
			res = IPlayer.ANIMATION_WALK_LEFT;
			break;
		case ICompassDirection.SOUTH_EAST:
		case ICompassDirection.SOUTH:
		case ICompassDirection.SOUTH_WEST:
			res = IPlayer.ANIMATION_WALK_DOWN;
			break;
		case ICompassDirection.WEST:
			res = IPlayer.ANIMATION_WALK_RIGHT;
		default:
			break;
		}
		
		return res;
	}
		
}
