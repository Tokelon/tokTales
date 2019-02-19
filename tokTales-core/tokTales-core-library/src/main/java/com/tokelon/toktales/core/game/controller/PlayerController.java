package com.tokelon.toktales.core.game.controller;

import javax.inject.Inject;

import com.tokelon.toktales.core.content.sprite.SpriteImpl;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.game.graphic.IBaseGraphic;
import com.tokelon.toktales.core.game.graphic.SpriteGraphicImpl;
import com.tokelon.toktales.core.game.graphic.animation.GameAnimation;
import com.tokelon.toktales.core.game.graphic.animation.IAnimationCallback;
import com.tokelon.toktales.core.game.graphic.animation.IGameAnimation;
import com.tokelon.toktales.core.game.logic.motion.IGameMotion;
import com.tokelon.toktales.core.game.logic.motion.IMotionCallback;
import com.tokelon.toktales.core.game.logic.motion.StraightAnimatedMotion;
import com.tokelon.toktales.core.game.model.Player;
import com.tokelon.toktales.core.game.model.IActor;
import com.tokelon.toktales.core.game.model.IPlayer;
import com.tokelon.toktales.core.game.model.Point2fImpl;
import com.tokelon.toktales.core.game.model.entity.IGameEntity;
import com.tokelon.toktales.core.game.model.entity.IGameEntity.IGameEntityParticipant;
import com.tokelon.toktales.core.game.states.IGameScene;
import com.tokelon.toktales.core.game.states.InjectGameScene;
import com.tokelon.toktales.core.game.world.ICrossDirection;
import com.tokelon.toktales.core.game.world.IWorld;
import com.tokelon.toktales.core.values.ControllerValues;
import com.tokelon.toktales.core.values.PlayerValues;

public class PlayerController extends AbstractController implements IPlayerController {

	
	private boolean mPlayerResponsive = true;
	
	private final AnimationCallback playerAnimationCallback = new AnimationCallback();

	private final PlayerParticipant mPlayerParticipant;
	
	
	private final IPlayer player;
	
	@Inject
	public PlayerController(IPlayer player) {
		this.player = player;
		
		mPlayerParticipant = new PlayerParticipant();
		//mPlayer.getParticipation().addParticipant(mPlayerParticipant);
		
		setupPlayer();
	}
	
	public PlayerController(IActor playerActor) {
		mPlayerParticipant = new PlayerParticipant();
		
		this.player = new Player(playerActor);
		//mPlayer.getParticipation().addParticipant(mPlayerParticipant);
	}
	
	
	// TODO: Remove this and do it via script
	private void setupPlayer() {
		IActor actor = player.getActor();
		
		//TODO: Initialize player values correctly
		float tileSize = IWorld.GRID_TILE_SIZE;
		
		float playerWalkSpeedUnits = PlayerValues.PLAYER_WALK_SPEED_UNITS;
		int playerMoveDuration = (int) (1000.0f * tileSize / playerWalkSpeedUnits);

		actor.setSpeedX(playerWalkSpeedUnits);
		actor.setSpeedY(playerWalkSpeedUnits);
		
		// TODO: Initialize player size correctly
		actor.setSize(tileSize, tileSize);
		actor.setOrigin(tileSize / 2.0f, tileSize / 2.0f);
		
		actor.setCollisionBox(tileSize/4.0f, tileSize/4.0f, tileSize/4.0f + tileSize/2.0f, tileSize); //tileSize/4.0f + tileSize/2.0f);	// Small size
		//mPlayer.setCollisionBox(0.0f, 0.0f, tileSize, tileSize);	// Full size
		
		
		//playerEntity.setWalkOneBlockDuration(playerMoveDuration);
		//playerEntity.setWalkMinBlockDistance(playerMoveBlockDistance);
		
		
		
		actor.getGraphicsImage().assignGraphic(IPlayer.GRAPHIC_IDLE_LEFT, new SpriteGraphicImpl(new SpriteImpl(PlayerValues.PLAYER_SPRITE_STATIC_LEFT)));
		actor.getGraphicsImage().assignGraphic(IPlayer.GRAPHIC_IDLE_UP, new SpriteGraphicImpl(new SpriteImpl(PlayerValues.PLAYER_SPRITE_STATIC_UP)));
		actor.getGraphicsImage().assignGraphic(IPlayer.GRAPHIC_IDLE_RIGHT, new SpriteGraphicImpl(new SpriteImpl(PlayerValues.PLAYER_SPRITE_STATIC_RIGHT)));
		actor.getGraphicsImage().assignGraphic(IPlayer.GRAPHIC_IDLE_DOWN, new SpriteGraphicImpl(new SpriteImpl(PlayerValues.PLAYER_SPRITE_STATIC_DOWN)));

		actor.setGraphicBase(actor.getGraphicsImage().getAssignedGraphic(IPlayer.GRAPHIC_IDLE_DOWN));
		
		
		GameAnimation playerAnimationLeft = new GameAnimation();
		playerAnimationLeft.setupGraphics(
				new SpriteGraphicImpl(new SpriteImpl(PlayerValues.PLAYER_SPRITE_ANIMATION_LEFT_01)),
				new SpriteGraphicImpl(new SpriteImpl(PlayerValues.PLAYER_SPRITE_ANIMATION_LEFT_02)));
		playerAnimationLeft.setupFramesWithOneDuration(2, playerMoveDuration/2);
		actor.getGraphicsImage().assignAnimation(IPlayer.ANIMATION_WALK_LEFT, playerAnimationLeft);
		
		
		GameAnimation playerAnimationUp = new GameAnimation();
		playerAnimationUp.setupGraphics(
				new SpriteGraphicImpl(new SpriteImpl(PlayerValues.PLAYER_SPRITE_ANIMATION_UP_01)),
				new SpriteGraphicImpl(new SpriteImpl(PlayerValues.PLAYER_SPRITE_ANIMATION_UP_02)));
		playerAnimationUp.setupFramesWithOneDuration(2, playerMoveDuration/2);
		actor.getGraphicsImage().assignAnimation(IPlayer.ANIMATION_WALK_UP, playerAnimationUp);

		
		GameAnimation playerAnimationRight = new GameAnimation();
		playerAnimationRight.setupGraphics(
				new SpriteGraphicImpl(new SpriteImpl(PlayerValues.PLAYER_SPRITE_ANIMATION_RIGHT_01)),
				new SpriteGraphicImpl(new SpriteImpl(PlayerValues.PLAYER_SPRITE_ANIMATION_RIGHT_02)));
		playerAnimationRight.setupFramesWithOneDuration(2, playerMoveDuration/2);
		actor.getGraphicsImage().assignAnimation(IPlayer.ANIMATION_WALK_RIGHT, playerAnimationRight);

		
		GameAnimation playerAnimationDown = new GameAnimation();
		playerAnimationDown.setupGraphics(
				new SpriteGraphicImpl(new SpriteImpl(PlayerValues.PLAYER_SPRITE_ANIMATION_DOWN_01)),
				new SpriteGraphicImpl(new SpriteImpl(PlayerValues.PLAYER_SPRITE_ANIMATION_DOWN_02)));
		playerAnimationDown.setupFramesWithOneDuration(2, playerMoveDuration/2);
		actor.getGraphicsImage().assignAnimation(IPlayer.ANIMATION_WALK_DOWN, playerAnimationDown);
		
	}
	
	
	@InjectGameScene
	public void injectGamescene(IGameScene gamescene) {
		// Since the script manager comes from the game, this would be done on injection
		
		//gamescene.getGamestate().getGame().getScriptManager();
		//scriptManager.addLuaObject(player, IGameScriptManager.MODULE_PLAYER);
	}
	
	
	@Override
	public void onControllerChange(IControllerManager manager, String controllerId, IController oldController, IController newController) {
		super.onControllerChange(manager, controllerId, oldController, newController);

		// Aquire anything map related if map controller has loaded
		if(controllerId.equals(ControllerValues.CONTROLLER_MAP)) {
			//TODO ?
		}
	}
	
	
	@Override
	public IPlayer getPlayer() {
		return player;
	}

	
	
	@Override
	public boolean playerLook(int direction) {
		
		if(mPlayerResponsive) {

			IBaseGraphic staticGraphic = player.getActor().getGraphicsImage().getAssignedGraphic(selectGraphicIDForDirection(direction));

			// TODO: What to do here if graphic is null?
			if(staticGraphic != null) {
				player.getActor().setGraphicBase(staticGraphic);
			}
			
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean playerStartMoving(int direction) {

		if(mPlayerResponsive) {

			player.getActor().setVelocity(
					player.getActor().getSpeedX() * ICrossDirection.Tools.horizontalVelocitySignFromDirection(direction),
					player.getActor().getSpeedY() * ICrossDirection.Tools.verticalVelocitySignFromDirectionInvertY(direction)
			);


			IGameAnimation selAnimation = player.getActor().getGraphicsImage().getAssignedAnimation(selectAnimationIDForDirection(direction));
			if(selAnimation == null) {
				IGameAnimation animation = player.getActor().getGraphicsImage().getAnimation();
				if(animation != null) {
					animation.stopAnimation();
				}
			}
			else {
				selAnimation.enableLoop(true);

				//mPlayer.setAnimation(selAnimation, playerAnimationCallback);
				player.getActor().getGraphicsImage().startAnimation(selAnimation, TokTales.getGame().getTimeManager().getGameTimeMillis());
			}

			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean playerStopMoving() {
		
		if(mPlayerResponsive) {

			player.getActor().setVelocity(0f, 0f);
			
			/* Old motion logic
			IGameMotion motion = mPlayer.getMotion();
			if(motion != null) {
				motion.stopMotion();
			}
			*/
			
			IGameAnimation ani = player.getActor().getGraphicsImage().getAnimation();
			if(ani != null) {
				ani.stopAnimation();
			}
			
			return true;
		}
		else {
			return false;
		}
	}
	
	
	@Override
	public boolean playerJump() {
		mPlayerParticipant.doJump(); // Does not actually do anything?
		
		player.getActor().getParticipation().notifyOfChange("gravity_DoJump");
		
		return true;
	}
	
	
	private class PlayerParticipant implements IGameEntityParticipant {

		
		private final float FALLING_SPEED_PER_SECOND = 300.0f;
		
		private final float INITIAL_JUMPING_SPEED_PER_SECOND = 15000.0f; 
		
		private final float JUMPING_DURATION_MILLIS = 1000.0f;	//1000.0f;
		
		private long lastUpdateTime = 0L;
		
		
		
		// Testing...
		private final Point2fImpl coords = new Point2fImpl();
		
		private float addx = 10.0f;
		private float offsetx = 0.0f;
		
		
		
		
		// Gravity tests
		private boolean doJump = false;
		
		private boolean isJumping = false;
		private long jumpStartingTime = 0L;
		
		
		public void doJump() {
			doJump = true;
		}
		
		
		@Override
		public boolean hasInterest(IGameEntity subject, String change) {
			return true;
		}
		
		
		@Override
		public boolean isGeneric() {
			return true;
		}
		
		@Override
		public boolean onSubjectChange(IGameEntity subject, String change) {
			//TokTales.getLog().d("PlayerController", "onSubjectChange: " + change);
			boolean changeWasChanged = false;

			
			if(change.equals(IGameEntity.CHANGE_ENTITY_STATE_ADJUST)) {
				
				/*
				if(offsetx < -150.0f || offsetx > 150.0f) {
					addx = -1 * addx;
				}
				offsetx += addx;
				
				subject.getStaticCoordinates(coords);
				subject.setStaticCoordinates(coords.x + addx, coords.y);
				
				
				subjectChanged = true;
				*/
				
				
				
				
				// Init jumpStartingTime
				if(doJump && !isJumping) {

					//Start jump
					// Use a motion object?
					isJumping = true;
					jumpStartingTime = subject.getCurrentUpdateTime();
					
					
					doJump = false;
				}
				
				
				
				
				long dt = subject.getCurrentUpdateTime() - lastUpdateTime;	// Millis

				
				// Gravity
				float gravityValue = (FALLING_SPEED_PER_SECOND / 1000.0f) * dt;
				
				// Jumping
				float jumpingValue = 0.0f;
				if(isJumping) {
					long jumpDt = subject.getCurrentUpdateTime() - jumpStartingTime;
					
					if(jumpDt > JUMPING_DURATION_MILLIS) {
						// Finish jumping
						
						isJumping = false;
						jumpStartingTime = 0L;
						
						doJump = false;	// Override so that it does not autojump if you've pressed it before
					}
					else {
						jumpingValue = (INITIAL_JUMPING_SPEED_PER_SECOND/1000.0f) - (jumpDt/JUMPING_DURATION_MILLIS * (INITIAL_JUMPING_SPEED_PER_SECOND/1000.0f));
					}
					
				}
				
				float targetX = subject.getWorldX();
				float targetY = subject.getWorldY() + gravityValue - jumpingValue;
				
				//float targetStaticX = subject.getStaticX();
				//float targetStaticY = subject.getStaticY() + gravityValue - jumpingValue;
				
				
				boolean canMove = true;
				if(subject.hasWorldspaceAssigned()) {
					canMove = subject.getAssignedWorldspace().entityCanMoveThereCheck(subject, targetX, targetY);
				}
				if(canMove) {
					subject.setWorldCoordinates(targetX, targetY);
					//subject.setStaticCoordinates(targetStaticX, targetStaticY);
					changeWasChanged = true;
				}
				
				
				lastUpdateTime = subject.getCurrentUpdateTime();
				
				
			}
			//else if(change.equals(IGameEntity.CHANGE_ENTITY_COORDINATE_STATE)) { Velocity?
				
				
				/*
				if(subject.getCoordinateState() == IGameEntity.STATE_COORDINATE_MOVING && subject.getMotion().getVerticalVelocity() < 0) {		// Moving up

					if(!isJumping) { // Disable double jumping

						//Start jump
						// Use a motion object?
						isJumping = true;
						jumpStartingTime = subject.getLastUpdateTime();
						
						subject.setMotion(null);
					}
					
				}
				*/
			//}

			

			return changeWasChanged;
		}

		@Override
		public void subjectChanged(IGameEntity subject, String change) {
			
			/*
			if(!change.equals(IGameEntity.CHANGE_ENTITY_STATE_ADJUST)) {
				TokTales.getLog().d("PlayerController", "subjectChanged: " + change);	
			}
			*/
		}
		
	}
	
	
	private class MotionCallback implements IMotionCallback<IGameMotion> {

		@Override
		public void motionStarted(IGameMotion motion) {
			
		}
		
		@Override
		public void motionFinished(IGameMotion motion) {
			StraightAnimatedMotion am = (StraightAnimatedMotion) motion;
			
			if(am.hasAnimation()) {
				am.getAnimation().stopAnimation();
				am.removeAnimation();
			}
			
			am.resetMotion();
		}
		
	}
	
	private class AnimationCallback implements IAnimationCallback<IGameAnimation> {

		@Override
		public void animationStarted(IGameAnimation animation) {
			
		}
		
		@Override
		public void animationFinished(IGameAnimation animation) {
			animation.resetAnimation();
		}
		
	}
	

	
	private static String selectGraphicIDForDirection(int crossDirection) {
		String res = null;
		switch (crossDirection) {
		case ICrossDirection.UP:
			res = IPlayer.GRAPHIC_IDLE_UP;
			break;
		case ICrossDirection.RIGHT:
			res = IPlayer.GRAPHIC_IDLE_RIGHT;
			break;
		case ICrossDirection.DOWN:
			res = IPlayer.GRAPHIC_IDLE_DOWN;
			break;
		case ICrossDirection.LEFT:
			res = IPlayer.GRAPHIC_IDLE_LEFT;
			break;

		default:
			break;
		}
		
		return res;
	}
	
	private static String selectAnimationIDForDirection(int crossDirection) {
		String res = null;
		switch (crossDirection) {
		case ICrossDirection.UP:
			res = IPlayer.ANIMATION_WALK_UP;
			break;
		case ICrossDirection.RIGHT:
			res = IPlayer.ANIMATION_WALK_RIGHT;
			break;
		case ICrossDirection.DOWN:
			res = IPlayer.ANIMATION_WALK_DOWN;
			break;
		case ICrossDirection.LEFT:
			res = IPlayer.ANIMATION_WALK_LEFT;
			break;

		default:
			break;
		}
		
		return res;
	}
	
	
	public static class PlayerControllerFactory implements IPlayerControllerFactory {

		@Override
		public IPlayerController create(IPlayer player) {
			return new PlayerController(player);
		}
	}
	
}
