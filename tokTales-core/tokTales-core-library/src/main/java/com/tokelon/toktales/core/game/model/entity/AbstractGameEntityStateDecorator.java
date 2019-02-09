package com.tokelon.toktales.core.game.model.entity;

import com.tokelon.toktales.core.game.logic.observers.IParticipable;
import com.tokelon.toktales.core.game.model.IPoint2f;
import com.tokelon.toktales.core.game.model.IPoint2f.IMutablePoint2f;
import com.tokelon.toktales.core.game.model.IRectangle2f;
import com.tokelon.toktales.core.game.model.IRectangle2f.IMutableRectangle2f;

public abstract class AbstractGameEntityStateDecorator implements IGameEntityState, IParticipable<IGameEntity> {
	
	
	private IGameEntityModel gameEntityModel;

	public AbstractGameEntityStateDecorator(IGameEntityModel model) {
		this.gameEntityModel = model;
	}
	
	
	public IGameEntityModel getModel() {
		return gameEntityModel;
	}
	
	public void setModel(IGameEntityModel model) {
		if(model == null) {
			throw new NullPointerException();
		}
		
		this.gameEntityModel = model;
	}



	@Override
	public boolean isActive() {
		return gameEntityModel.isActive();
	}

	@Override
	public boolean isVisible() {
		return gameEntityModel.isVisible();
	}

	@Override
	public float getHeight() {
		return gameEntityModel.getHeight();
	}

	@Override
	public float getWidth() {
		return gameEntityModel.getWidth();
	}

	@Override
	public float getSpeedX() {
		return gameEntityModel.getSpeedX();
	}

	@Override
	public float getSpeedY() {
		return gameEntityModel.getSpeedY();
	}
	
	@Override
	public float getVelocityX() {
		return gameEntityModel.getVelocityX();
	}

	@Override
	public float getVelocityY() {
		return gameEntityModel.getVelocityY();
	}
	
	@Override
	public IMutablePoint2f getVelocity(IMutablePoint2f result) {
		return gameEntityModel.getVelocity(result);
	}
	
	@Override
	public IMutableRectangle2f getBounds(IMutableRectangle2f result) {
		return gameEntityModel.getBounds(result);
	}

	@Override
	public IRectangle2f getBoundsBack() {
		return gameEntityModel.getBoundsBack();
	}

	@Override
	public IMutableRectangle2f getCollisionBounds(IMutableRectangle2f result) {
		return gameEntityModel.getCollisionBounds(result);
	}

	@Override
	public IRectangle2f getCollisionBoundsBack() {
		return gameEntityModel.getCollisionBoundsBack();
	}

	@Override
	public IMutableRectangle2f getCollisionBox(IMutableRectangle2f result) {
		return gameEntityModel.getCollisionBox(result);
	}

	@Override
	public IRectangle2f getCollisionBoxBack() {
		return gameEntityModel.getCollisionBoxBack();
	}

	@Override
	public float getOriginX() {
		return gameEntityModel.getOriginX();
	}

	@Override
	public float getOriginY() {
		return gameEntityModel.getOriginY();
	}

	@Override
	public IMutablePoint2f getOrigin(IMutablePoint2f result) {
		return gameEntityModel.getOrigin(result);
	}

	@Override
	public float getRawWorldX() {
		return gameEntityModel.getRawWorldX();
	}

	@Override
	public float getRawWorldY() {
		return gameEntityModel.getRawWorldY();
	}

	@Override
	public IMutablePoint2f getRawWorldCoordinates(IMutablePoint2f result) {
		return gameEntityModel.getRawWorldCoordinates(result);
	}

	@Override
	public float getWorldX() {
		return gameEntityModel.getWorldX();
	}

	@Override
	public float getWorldY() {
		return gameEntityModel.getWorldY();
	}

	@Override
	public IMutablePoint2f getWorldCoordinates(IMutablePoint2f result) {
		return gameEntityModel.getWorldCoordinates(result);
	}
	
	

	@Override
	public void setActive(boolean isActive) {
		boolean notify = isActive() != isActive;

		gameEntityModel.setActive(isActive);
		
		if(notify) {
			getParticipation().notifyOfChange(IGameEntity.CHANGE_ENTITY_ACTIVE_STATUS);
		}
	}

	@Override
	public void setVisible(boolean isVisible) {
		boolean notify = isVisible() != isVisible;

		gameEntityModel.setVisible(isVisible);

		if(notify) {
			getParticipation().notifyOfChange(IGameEntity.CHANGE_ENTITY_VISIBLE_STATUS);
		}
	}

	@Override
	public void setWidth(float width) {
		gameEntityModel.setWidth(width);

		getParticipation().notifyOfChange(IGameEntity.CHANGE_ENTITY_SIZE);
	}

	@Override
	public void setHeight(float height) {
		gameEntityModel.setHeight(height);
		
		getParticipation().notifyOfChange(IGameEntity.CHANGE_ENTITY_SIZE);
	}

	@Override
	public void setSize(float width, float height) {
		gameEntityModel.setSize(width, height);
		
		getParticipation().notifyOfChange(IGameEntity.CHANGE_ENTITY_SIZE);
	}

	@Override
	public void setSpeedX(float sx) {
		gameEntityModel.setSpeedX(sx);
		
		getParticipation().notifyOfChange(IGameEntity.CHANGE_ENTITY_SPEED);
	}

	@Override
	public void setSpeedY(float sy) {
		gameEntityModel.setSpeedY(sy);

		getParticipation().notifyOfChange(IGameEntity.CHANGE_ENTITY_SPEED);
	}

	@Override
	public void setSpeed(float sx, float sy) {
		gameEntityModel.setSpeed(sx, sy);

		getParticipation().notifyOfChange(IGameEntity.CHANGE_ENTITY_SPEED);
	}

	@Override
	public void setVelocityX(float vx) {
		gameEntityModel.setVelocityX(vx);

		getParticipation().notifyOfChange(IGameEntity.CHANGE_ENTITY_VELOCITY);
	}

	@Override
	public void setVelocityY(float vy) {
		gameEntityModel.setVelocityY(vy);
		
		getParticipation().notifyOfChange(IGameEntity.CHANGE_ENTITY_VELOCITY);
	}

	@Override
	public void setVelocity(float vx, float vy) {
		gameEntityModel.setVelocity(vx, vy);
		
		getParticipation().notifyOfChange(IGameEntity.CHANGE_ENTITY_VELOCITY);
	}

	@Override
	public void setOrigin(float originx, float originy) {
		gameEntityModel.setOrigin(originx, originy);
		
		getParticipation().notifyOfChange(IGameEntity.CHANGE_ENTITY_ORIGIN);
	}

	@Override
	public void setCollisionBox(float left, float top, float right, float bottom) {
		gameEntityModel.setCollisionBox(left, top, right, bottom);
		
		getParticipation().notifyOfChange(IGameEntity.CHANGE_ENTITY_COLLISION_BOX);
	}

	@Override
	public void setCollisionBox(IRectangle2f box) {
		gameEntityModel.setCollisionBox(box);
		
		getParticipation().notifyOfChange(IGameEntity.CHANGE_ENTITY_COLLISION_BOX);
	}

	@Override
	public void setWorldCoordinates(float worldX, float worldY) {
		gameEntityModel.setWorldCoordinates(worldX, worldY);
		
		getParticipation().notifyOfChange(IGameEntity.CHANGE_ENTITY_COORDINATES);
	}

	@Override
	public void setWorldCoordinates(IPoint2f worldCoords) {
		gameEntityModel.setWorldCoordinates(worldCoords);
		
		getParticipation().notifyOfChange(IGameEntity.CHANGE_ENTITY_COORDINATES);
	}

}
