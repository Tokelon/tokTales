package com.tokelon.toktales.core.game.model;

import com.tokelon.toktales.core.game.graphic.IBaseGraphic;
import com.tokelon.toktales.core.game.logic.entity.IGraphicsImage;
import com.tokelon.toktales.core.game.logic.observers.IObservation;
import com.tokelon.toktales.core.game.logic.observers.IObserver;
import com.tokelon.toktales.core.game.logic.observers.IParticipant;
import com.tokelon.toktales.core.game.logic.observers.IParticipation;
import com.tokelon.toktales.core.game.model.IPoint2f.IMutablePoint2f;
import com.tokelon.toktales.core.game.model.IRectangle2f.IMutableRectangle2f;
import com.tokelon.toktales.core.game.model.entity.IGameEntity;
import com.tokelon.toktales.core.game.world.IWorldspace;

public class ActorPlayer implements IPlayer {
	
	// TODO: Refactor to contain an entity instead of being an entity?

	
	private final IActor mActor;

	public ActorPlayer(IActor actor) {
		this.mActor = actor;
	}

	

	@Override
	public void adjustState(long timeMillis) {
		mActor.adjustState(timeMillis);
	}
	
	@Override
	public void setVelocity(float vx, float vy) {
		mActor.setVelocity(vx, vy);
	}
	
	@Override
	public void setVelocityX(float vx) {
		mActor.setVelocityX(vx);
	}
	
	@Override
	public void setVelocityY(float vy) {
		mActor.setVelocityY(vy);
	}


	@Override
	public void assignWorldspace(IWorldspace worldspace) {
		mActor.assignWorldspace(worldspace);
	}

	@Override
	public boolean hasWorldspaceAssigned() {
		return mActor.hasWorldspaceAssigned();
	}

	@Override
	public IWorldspace getAssignedWorldspace() {
		return mActor.getAssignedWorldspace();
	}

	@Override
	public boolean deassignWorldspace() {
		return mActor.deassignWorldspace();
	}

	@Override
	public int hashCode() {
		return mActor.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return mActor.equals(obj) || super.equals(obj);
	}
	
	
	
	@Override
	public boolean hasPayload() {
		return mActor.hasPayload();
	}
	
	@Override
	public Object getPayload() {
		return mActor.getPayload();
	}
	
	@Override
	public <T> T getPayloadAs(Class<T> desiredClass) {
		return mActor.getPayloadAs(desiredClass);
	}
	
	@Override
	public boolean isActive() {
		return mActor.isActive();
	}

	@Override
	public boolean isVisible() {
		return mActor.isVisible();
	}

	@Override
	public float getHeight() {
		return mActor.getHeight();
	}

	@Override
	public float getWidth() {
		return mActor.getWidth();
	}

	@Override
	public IMutableRectangle2f getBounds(IMutableRectangle2f result) {
		return mActor.getBounds(result);
	}

	@Override
	public IRectangle2f getBoundsBack() {
		return mActor.getBoundsBack();
	}

	@Override
	public IMutableRectangle2f getCollisionBounds(IMutableRectangle2f result) {
		return mActor.getCollisionBounds(result);
	}

	@Override
	public IRectangle2f getCollisionBoundsBack() {
		return mActor.getCollisionBoundsBack();
	}

	@Override
	public IMutableRectangle2f getCollisionBox(IMutableRectangle2f result) {
		return mActor.getCollisionBox(result);
	}

	@Override
	public IRectangle2f getCollisionBoxBack() {
		return mActor.getCollisionBoxBack();
	}

	@Override
	public float getOriginX() {
		return mActor.getOriginX();
	}

	@Override
	public float getOriginY() {
		return mActor.getOriginY();
	}

	@Override
	public IMutablePoint2f getOrigin(IMutablePoint2f result) {
		return mActor.getOrigin(result);
	}

	@Override
	public float getRawWorldX() {
		return mActor.getRawWorldX();
	}

	@Override
	public float getRawWorldY() {
		return mActor.getRawWorldY();
	}

	@Override
	public IMutablePoint2f getRawWorldCoordinates(IMutablePoint2f result) {
		return mActor.getRawWorldCoordinates(result);
	}

	@Override
	public float getWorldX() {
		return mActor.getWorldX();
	}

	@Override
	public float getWorldY() {
		return mActor.getWorldY();
	}

	@Override
	public IMutablePoint2f getWorldCoordinates(IMutablePoint2f result) {
		return mActor.getWorldCoordinates(result);
	}

	@Override
	public IBaseGraphic getGraphic() {
		return mActor.getGraphic();
	}

	@Override
	public IBaseGraphic getGraphic(String name) {
		return mActor.getGraphic();
	}
	
	@Override
	public IBaseGraphic getGraphicBase() {
		return mActor.getGraphicBase();
	}
	
	@Override
	public void setGraphicBase(IBaseGraphic graphic) {
		mActor.setGraphicBase(graphic);
	}

	@Override
	public IGraphicsImage getGraphicsImage() {
		return mActor.getGraphicsImage();
	}
	
	
	@Override
	public float getSpeedX() {
		return mActor.getSpeedX();
	}

	@Override
	public float getSpeedY() {
		return mActor.getSpeedY();
	}

	@Override
	public long getCurrentUpdateTime() {
		return mActor.getCurrentUpdateTime();
	}
	
	@Override
	public long getPreviousUpdateTime() {
		return mActor.getPreviousUpdateTime();
	}
	
	@Override
	public long getDeltaUpdateTime() {
		return mActor.getDeltaUpdateTime();
	}
	
	
	@Override
	public IMutablePoint2f getVelocity(IMutablePoint2f result) {
		return mActor.getVelocity(result);
	}
	
	@Override
	public float getVelocityX() {
		return mActor.getVelocityX();
	}
	
	public float getVelocityY() {
		return mActor.getVelocityY(); 
	}
	

	@Override
	public void setActive(boolean isActive) {
		mActor.setActive(isActive);
	}

	@Override
	public void setVisible(boolean isVisible) {
		mActor.setVisible(isVisible);
	}

	
	@Override
	public void setWidth(float width) {
		mActor.setWidth(width);
	}
	
	@Override
	public void setHeight(float height) {
		mActor.setHeight(height);
	}
	
	@Override
	public void setSize(float width, float height) {
		mActor.setSize(width, height);
	}

	
	@Override
	public void setOrigin(float originx, float originy) {
		mActor.setOrigin(originx, originy);
	}
	
	
	@Override
	public void setCollisionBox(float left, float top, float right, float bottom) {
		mActor.setCollisionBox(left, top, right, bottom);
	}

	@Override
	public void setCollisionBox(IRectangle2f box) {
		mActor.setCollisionBox(box);
	}
	
	@Override
	public void setWorldCoordinates(IPoint2f coords) {
		mActor.setWorldCoordinates(coords);
	}
	
	@Override
	public void setWorldCoordinates(float worldX, float worldY) {
		mActor.setWorldCoordinates(worldX, worldY);
	}

	
	@Override
	public void setSpeedX(float sx) {
		mActor.setSpeedX(sx);
	}

	@Override
	public void setSpeedY(float sy) {
		mActor.setSpeedY(sy);
	}
	
	@Override
	public void setSpeed(float sx, float sy) {
		mActor.setSpeed(sx, sy);
	}
	

	@Override
	public IObservation<IGameEntity, IObserver<IGameEntity>> getObservation() {
		return mActor.getObservation();
	}

	@Override
	public IParticipation<IGameEntity, IObserver<IGameEntity>, IParticipant<IGameEntity>> getParticipation() {
		return mActor.getParticipation();
	}

	@Override
	public void addInheritedObserver(IObserver<IGameEntity> observer) {
		mActor.addInheritedObserver(observer);
	}

	@Override
	public void removeInheritedObserver(IObserver<IGameEntity> observer) {
		mActor.removeInheritedObserver(observer);
	}

	@Override
	public void addInheritedParticipant(IParticipant<IGameEntity> participant) {
		mActor.addInheritedParticipant(participant);
	}

	@Override
	public void removeInheritedParticipant(IParticipant<IGameEntity> participant) {
		mActor.removeInheritedParticipant(participant);
	}
	
	
}
