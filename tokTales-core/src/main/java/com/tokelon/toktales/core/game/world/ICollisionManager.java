package com.tokelon.toktales.core.game.world;

import java.util.List;

import com.tokelon.toktales.core.game.model.IPoint2f;
import com.tokelon.toktales.core.game.model.IRectangle2f;
import com.tokelon.toktales.core.game.model.entity.IGameEntity;

public interface ICollisionManager {


	public ICollisionStrategy getStrategy();
	
	public void setStrategy(ICollisionStrategy strategy);
	
	
	public boolean entityCanMoveThereCheck(IGameEntity entity, IPoint2f destination);
	public boolean entityCanMoveThereCheck(IGameEntity entity, float destX, float destY);
	//public boolean entityCanMoveThereCheck(float left, float top, float right, float bottom);
	//public boolean entityCanMoveThereCheck(IMWRectangle bounds);
	
	public boolean entityCanMoveThereConsidering(IGameEntity entity, float destX, float destY, IGameEntity... considerList);
	public boolean entityCanMoveThereIgnoring(IGameEntity entity, float destX, float destY, IGameEntity... ignoreList);		//entityCanMoveThereExcluding

	
	
	public boolean doEntitiesCollide(IGameEntity first, IGameEntity second);
	
	//public boolean doesEntityCollideWith(IGameEntity entity, IGameEntity... with);
	//public boolean doesEntityCollideWithout(IGameEntity entity, IGameEntity... without);

	
	
	
	public void getRectangleCollision(IRectangle2f rectangle, List<IGameEntity> collisionList);
	
	public void getRectangleCollision(float left, float top, float right, float bottom, List<IGameEntity> collisionList);
	
	
}
