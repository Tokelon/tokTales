package com.tokelon.toktales.core.game.world;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.LoggingManager;
import com.tokelon.toktales.core.game.controller.IController;
import com.tokelon.toktales.core.game.controller.map.IMapController;
import com.tokelon.toktales.core.game.model.IPoint2f;
import com.tokelon.toktales.core.game.model.IRectangle2f;
import com.tokelon.toktales.core.game.model.entity.IGameEntity;
import com.tokelon.toktales.core.game.model.map.IBlock;
import com.tokelon.toktales.core.game.model.map.IBlockMap;
import com.tokelon.toktales.core.game.state.scene.IGameScene;
import com.tokelon.toktales.core.game.state.scene.InjectGameScene;
import com.tokelon.toktales.core.values.ControllerValues;

@InjectGameScene
public class Worldspace implements IWorldspace {


	private static final ILogger logger = LoggingManager.getLogger(Worldspace.class); // Inject instead?
	
	
	private final Map<String, IGameEntity> entityMap;
	
	// TODO: This needs to be done differently. Synchronization is broken right now because entityMap can be modified regardless of entityIDList
	private final Set<String> entityIDList;
	
	
	private IGameScene gamescene;
	
	private ICollisionStrategy mCollisionStrategy;
	private final IWorld world;
	
	public Worldspace(IWorld world) {
		this(world, new DefaultCollisionStrategy());
	}
	
	@Inject
	public Worldspace(IWorld world, ICollisionStrategy strategy) {
		this.world = world;
		
		setStrategy(strategy);

		entityMap = Collections.synchronizedMap(new HashMap<String, IGameEntity>());
		
		entityIDList = Collections.synchronizedSet(new HashSet<String>());
	}

	
	@InjectGameScene
	public void injectGamescene(IGameScene gamescene) {
		this.gamescene = gamescene;
	}
	
	
	@Override
	public void adjustState(long timeMillis) {
		/* TODO:
		 * How to update entities and which entities ?
		 * 
		 */

		// Cannot add entity while iterating!!
		//synchronized (getWorldspace().getEntityIDSet()) {		// Synchronize !! HOW?

		for(String entityID: getEntityIDSet()) {
			if(entityID.toLowerCase(Locale.ENGLISH).contains("player")) {
				// TODO: How to implement this ?
				// Do not update anything player related
				continue;
			}

			IGameEntity entity = getEntity(entityID);
			entity.adjustState(timeMillis);
		}

		// TODO: Remove! Workaround
		getEntityIDSet().clear();
		getEntityIDSet().addAll(getEntityMap().keySet());

	}
	

	
	
	@Override
	public ICollisionStrategy getStrategy() {
		return mCollisionStrategy;
	}


	@Override
	public void setStrategy(ICollisionStrategy strategy) {
		this.mCollisionStrategy = strategy;
	}



	@Override
	public boolean putEntity(String id, IGameEntity entity) {
		IGameEntity previousEntity = entityMap.remove(id);
		if(previousEntity != null) {
			logger.warn("Overriding entity for id: {} | [was:{}, is:{}]", id, previousEntity, entity); // TODO: Inefficient logging?
			previousEntity.deassignWorldspace();
		}
		
		entity.assignWorldspace(this);

		entityMap.put(id, entity);
		//entityIDList.add(id);		// Workaround
		
		return previousEntity != null;
	}


	@Override
	public boolean removeEntity(String id) {
		IGameEntity entity = entityMap.remove(id);

		if(entity == null) {
			logger.warn("Removing entity failed. No entity for id: {}", id);
		}
		
		//entityIDList.remove(id);	// Workaround
		
		return entity != null;
	}

	
	@Override
	public boolean hasEntity(String id) {
		return entityMap.containsKey(id);
	}

	@Override
	public IGameEntity getEntity(String id) {
		return entityMap.get(id);
	}

	
	@Override
	public Set<String> getEntityIDSet() {
		
		// TODO: Return unmodifiable set but make sure synchronization works !!
		return entityIDList;
	}
	
	@Override
	public Map<String, IGameEntity> getEntityMap() {	// TODO: Remove! Workaround
		return entityMap;
	}
	
	
	
	
	
	/* TODO: Implement entity get closest you can move if check fails
	 * (Important for example for very fast entities)
	 * 
	 */
	
	@Override
	public boolean entityCanMoveThereCheck(IGameEntity entity, float destX, float destY) {
		
		if(entity.getCollisionBoxBack().getWidth() == 0.0f || entity.getCollisionBoxBack().getHeight() == 0.0f) {	// Float comparison; needs fixing
			// Workaround! to ignore entities with no collision box
			// TODO: Implement correctly
			return true;
		}
		
		
		
		boolean canMove = true;

		
		
		// TODO: Check if the right and bottom side of rectangles is being ignored correctly!
		
		canMove = canMove && mapCheck(entity, destX, destY);
		
		
		
		
		/* TODO: Implement entity value that says what kind of structure the entity can move through
		 * 
		 */
		
		
		// For now, only check if there is another entity in that space
		canMove = canMove && entityCheck(entity, destX, destY);
		
		
		return canMove;
	}
	
	
	@Override
	public boolean entityCanMoveThereCheck(IGameEntity entity, IPoint2f destination) {
		return entityCanMoveThereCheck(entity, destination.x(), destination.y());
	}
	
	
	@Override
	public boolean entityCanMoveThereConsidering(IGameEntity entity, float destX, float destY, IGameEntity... considerList) {

		if(entity.getCollisionBoxBack().getWidth() == 0.0f || entity.getCollisionBoxBack().getHeight() == 0.0f) {	// Float comparison; needs fixing
			// Workaround! to ignore entities with no collision box
			// TODO: Implement correctly
			return true;
		}
		
		boolean canMove = true;

		canMove = canMove && mapCheck(entity, destX, destY);
		
		canMove = canMove && entityCheckWith(entity, destX, destY, considerList);
		
		return canMove;
	}
	
	@Override
	public boolean entityCanMoveThereIgnoring(IGameEntity entity, float destX, float destY, IGameEntity... ignoreList) {
		
		if(entity.getCollisionBoxBack().getWidth() == 0.0f || entity.getCollisionBoxBack().getHeight() == 0.0f) {	// Float comparison; needs fixing
			// Workaround! to ignore entities with no collision box
			// TODO: Implement correctly
			return true;
		}
		
		boolean canMove = true;

		canMove = canMove && mapCheck(entity, destX, destY);
		
		canMove = canMove && entityCheckExclude(entity, destX, destY, ignoreList);
		
		return canMove;		
	}
	
	
	
	
	private boolean entityCheckWith(IGameEntity entity, float destX, float destY, IGameEntity... with) {
		boolean canMove = true;
		

		float destLeft = destX - entity.getOriginX() + entity.getCollisionBoxBack().getLeft();
		float destRight = destX - entity.getOriginX() + entity.getCollisionBoxBack().getRight();
		float destTop = destY - entity.getOriginY() + entity.getCollisionBoxBack().getTop();
		float destBottom = destY - entity.getOriginY() + entity.getCollisionBoxBack().getBottom();
		
		for(IGameEntity e: with) {
			if(entity.equals(e)) {
				continue;	// Do not check for the same entity	| Is this needed?
			}
			
			if(getStrategy().doRectanglesCollide(e.getCollisionBoundsBack().getLeft(), e.getCollisionBoundsBack().getTop(), e.getCollisionBoundsBack().getRight(), e.getCollisionBoundsBack().getBottom()
					, destLeft, destTop, destRight, destBottom)) {
				
				canMove = false;
				break;	// Ignore other entities
			}
		}
		
		return canMove;
	}
	
	private boolean entityCheckExclude(IGameEntity entity, float destX, float destY, IGameEntity... excluded) {
		boolean canMove = true;
		
		float destLeft = destX - entity.getOriginX() + entity.getCollisionBoxBack().getLeft();
		float destRight = destX - entity.getOriginX() + entity.getCollisionBoxBack().getRight();
		float destTop = destY - entity.getOriginY() + entity.getCollisionBoxBack().getTop();
		float destBottom = destY - entity.getOriginY() + entity.getCollisionBoxBack().getBottom();
		
		synchronized (entityMap) {
			for(IGameEntity e: entityMap.values()) {
				if(entity.equals(e)) {
					continue;	// Do not check for the same entity
				}
				
				// Check if entity is contained in the without list
				boolean contains = false;
				for(IGameEntity withoutEntity: excluded) {
					if(e.equals(withoutEntity)) {
						contains = true;
						break;
					}
				}
				if(contains) {
					continue;
				}
				
				
				
				if(getStrategy().doRectanglesCollide(e.getCollisionBoundsBack().getLeft(), e.getCollisionBoundsBack().getTop(), e.getCollisionBoundsBack().getRight(), e.getCollisionBoundsBack().getBottom()
						, destLeft, destTop, destRight, destBottom)) {
					
					canMove = false;
					break;	// Ignore other entities
				}
			}
		}
		
		return canMove;
	}
	
	
	private boolean entityCheck(IGameEntity entity, float destX, float destY) {
		boolean canMove = true;
		
		float destLeft = destX - entity.getOriginX() + entity.getCollisionBoxBack().getLeft();
		float destRight = destX - entity.getOriginX() + entity.getCollisionBoxBack().getRight();
		float destTop = destY - entity.getOriginY() + entity.getCollisionBoxBack().getTop();
		float destBottom = destY - entity.getOriginY() + entity.getCollisionBoxBack().getBottom();
		
		synchronized (entityMap) {
			for(IGameEntity e: entityMap.values()) {
				
				// TODO: Define when two entities are equal 
				// and implement equals and hashCode for GameEntity
				if(entity.equals(e) || e.equals(entity)) {
					continue;	// Do not check for the same entity
				}
				
				if(getStrategy().doRectanglesCollide(e.getCollisionBoundsBack().getLeft(), e.getCollisionBoundsBack().getTop(), e.getCollisionBoundsBack().getRight(), e.getCollisionBoundsBack().getBottom()
						, destLeft, destTop, destRight, destBottom)) {
					
					canMove = false;
					break;	// Ignore other entities
				}
			}
		}
		
		return canMove;
	}
	
	
	
	
	private boolean mapCheck(IGameEntity entity, float destX, float destY) {
		boolean canMove = true;
		
		IController controller = gamescene.getControllerManager().getController(ControllerValues.CONTROLLER_MAP);
		if(controller == null) {
			return true;
		}
		else if(!(controller instanceof IMapController)) {
			// TODO: throw exception or log once?
			return true;
		}
		IMapController mapController = (IMapController) controller;

		// Check map for area
		IBlockMap map = mapController.getMap();


		// Calculate which is the entity front size (moving direction)
		// then check all blocks that the whole side moves through
		// Do this for both vertically and horizontal movement

		float startx = entity.getWorldX();
		float endx = destX;

		if(startx > endx) {
			// Moving WEST
			// Take the left side of the collision bounds

			// -originx to get to the 0 (left side) + left collision value
			endx += -entity.getOriginX() + entity.getCollisionBoxBack().getLeft();	// If collision box is 0, this will just be the left bound
			startx += -entity.getOriginX() + entity.getCollisionBoxBack().getLeft(); // startx also needs to be ajusted to the collision box!!
		}
		else if(startx < endx) {
			// Moving EAST
			// Take the right side

			endx += -entity.getOriginX() + entity.getCollisionBoxBack().getRight();
			startx += -entity.getOriginX() + entity.getCollisionBoxBack().getRight();
		}
		else {
			// Not moving
		}


		int tileIndexStartX = world.getGrid().worldToTile(startx);
		int tileIndexEndX = world.getGrid().worldToTile(endx);

		int posOffsetX = tileIndexEndX - tileIndexStartX > 0 ? 1 : -1;

		int blockDistanceX = Math.abs(tileIndexStartX - tileIndexEndX);





		float starty = entity.getWorldY();
		float endy = destY;

		if(starty > endy) {
			// Moving NORTH
			// Take the top side of the entity bounds

			endy += -entity.getOriginY() + entity.getCollisionBoxBack().getTop();
			starty += -entity.getOriginY() + entity.getCollisionBoxBack().getTop();
		}
		else if(starty < endy) {
			// Moving SOUTH
			// Take the bottom side

			endy += -entity.getOriginY() + entity.getCollisionBoxBack().getBottom();
			starty += -entity.getOriginY() + entity.getCollisionBoxBack().getBottom();
		}
		else {
			// Not moving
		}


		int tileIndexStartY = world.getGrid().worldToTile(starty);
		int tileIndexEndY = world.getGrid().worldToTile(endy);

		int posOffsetY = tileIndexEndY - tileIndexStartY > 0 ? 1 : -1;

		int blockDistanceY = Math.abs(tileIndexStartY - tileIndexEndY);




		/* Some sort of optimization possible with this ?
			if(worldDistanceX < world.getGridTileSize() && worldDistanceY < world.getGridTileSize()) {
				// This will usually be the case except for fast entities ?
				// Only check 1 or two blocks in each direction
			}
		 */


		if(blockDistanceX == 0 && blockDistanceY == 0) {
			// Moving inside the same block
			// Normally this is fine

			// Basically end the map checking
		}
		else if(blockDistanceX == 0) {

			int leftTile = world.getGrid().worldToTile(entity.getCollisionBoundsBack().getLeft());	//entity.getLeftBound());
			int rightTile = world.getGrid().worldToTile(entity.getCollisionBoundsBack().getRight());

			for(int tileY = tileIndexStartY; tileY - tileIndexEndY != posOffsetY; tileY += posOffsetY) {

				for(int tileX = leftTile; tileX <= rightTile; tileX++) {	// Entity tile coverage horizontally

					if(tileX < 0 || tileX >= map.getHorizontalSize() || tileY < 0 || tileY >= map.getVerticalSize()) {
						// Not in map bounds
						continue;
					}


					IBlock block = map.getBlockAt(tileX, tileY);

					if(!block.isWalkableForPlayer()) {	// TODO: Make generic is walkable or something
						// Iterate through elements and compare with entity walkable value
						// Or use dominant walkable value of element
						//IMapElement.WALKABLE_YES;

						canMove = false;
						break;
					}
				}
			}
		}
		else if(blockDistanceY == 0) {

			int upperTile = world.getGrid().worldToTile(entity.getCollisionBoundsBack().getTop());
			int lowerTile = world.getGrid().worldToTile(entity.getCollisionBoundsBack().getBottom());


			for(int tileX = tileIndexStartX; tileX - tileIndexEndX != posOffsetX; tileX += posOffsetX) {

				for(int tileY = upperTile; tileY <= lowerTile; tileY++) {	// Entity tile coverage vertically

					if(tileX < 0 || tileX >= map.getHorizontalSize() || tileY < 0 || tileY >= map.getVerticalSize()) {
						// Not in map bounds
						continue;
					}


					IBlock block = map.getBlockAt(tileX, tileY);

					if(!block.isWalkableForPlayer()) {	// TODO: Make generic is walkable or something
						// Iterate through elements and compare with entity walkable value
						// Or use dominant walkable value of element
						//IMapElement.WALKABLE_YES;

						canMove = false;
						break;
					}
				}
			}
		}
		else {
			/*
				// TODO: This most likely does not work yet!

				for(int tileX = tileIndexStartX; tileX - tileIndexEndX != posOffsetX; tileX += posOffsetX) {
					for(int tileY = tileIndexStartY; tileY - tileIndexEndY != posOffsetY; tileY += posOffsetY) {

						if(tileX < 0 || tileX >= map.getHorizontalSize() || tileY < 0 || tileY >= map.getVerticalSize()) {
							continue;
						}


						IBlock block = map.getBlockAt(tileX, tileY);

						if(!block.isWalkableForPlayer()) {	// TODO: Make generic is walkable or something
							// Iterate through elements and compare with entity walkable value
							// Or use dominant walkable value of element
							//IMapElement.WALKABLE_YES;

							canMove = false;
							break;
						}
					}

					if(canMove) {
						break;
					}
				}
			 */



			// vertical
			int upperTile = world.getGrid().worldToTile(entity.getCollisionBoundsBack().getTop());
			int lowerTile = world.getGrid().worldToTile(entity.getCollisionBoundsBack().getBottom());

			for(int tileX = tileIndexStartX; tileX - tileIndexEndX != posOffsetX; tileX += posOffsetX) {

				for(int tileY = upperTile; tileY <= lowerTile; tileY++) {	// Entity tile coverage vertically

					if(tileX < 0 || tileX >= map.getHorizontalSize() || tileY < 0 || tileY >= map.getVerticalSize()) {
						// Not in map bounds
						continue;
					}


					IBlock block = map.getBlockAt(tileX, tileY);

					if(!block.isWalkableForPlayer()) {	// TODO: Make generic is walkable or something
						// Iterate through elements and compare with entity walkable value
						// Or use dominant walkable value of element
						//IMapElement.WALKABLE_YES;

						canMove = false;
						break;
					}
				}
			}


			if(canMove == true) {	// If not already cannot move

				// horizontal
				int leftTile = world.getGrid().worldToTile(entity.getCollisionBoundsBack().getLeft());	//entity.getLeftBound());
				int rightTile = world.getGrid().worldToTile(entity.getCollisionBoundsBack().getRight());

				for(int tileY = tileIndexStartY; tileY - tileIndexEndY != posOffsetY; tileY += posOffsetY) {

					for(int tileX = leftTile; tileX <= rightTile; tileX++) {	// Entity tile coverage horizontally

						if(tileX < 0 || tileX >= map.getHorizontalSize() || tileY < 0 || tileY >= map.getVerticalSize()) {
							// Not in map bounds
							continue;
						}


						IBlock block = map.getBlockAt(tileX, tileY);

						if(!block.isWalkableForPlayer()) {	// TODO: Make generic is walkable or something
							// Iterate through elements and compare with entity walkable value
							// Or use dominant walkable value of element
							//IMapElement.WALKABLE_YES;

							canMove = false;
							break;
						}
					}
				}

			}

		}
		
		
		return canMove;
	}
	
	
	
	
	@Override
	public boolean doEntitiesCollide(IGameEntity first, IGameEntity second) {
		
		return getStrategy().doRectanglesCollide(first.getCollisionBoundsBack(), second.getCollisionBoundsBack());
	}


	@Override
	public void getRectangleCollision(IRectangle2f rectangle, List<IGameEntity> collisionList) {
		
		// Synchronization is needed !
		synchronized (entityMap) {
			
			for(IGameEntity entity: entityMap.values()) {
				
				if(getStrategy().doRectanglesCollide(rectangle, entity.getCollisionBoundsBack())) {
					collisionList.add(entity);
				}
			}
		}
		
	}


	@Override
	public void getRectangleCollision(float left, float top, float right, float bottom, List<IGameEntity> collisionList) {

		// Synchronization is needed !
		synchronized (entityMap) {

			for(IGameEntity entity: entityMap.values()) {

				if(getStrategy().doRectanglesCollide(left, top, right, bottom, entity.getCollisionBoundsBack().getLeft(), entity.getCollisionBoundsBack().getTop(), entity.getCollisionBoundsBack().getRight(), entity.getCollisionBoundsBack().getBottom())) {
					collisionList.add(entity);
				}
			}
		}

	}
	
}
