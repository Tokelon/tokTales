package com.tokelon.toktales.core.game.logic.motion;

import com.tokelon.toktales.core.game.model.map.IMapPosition;

public interface IMapMotion extends IGameMotion {

	
	public void setupPositions(IMapPosition origin, IMapPosition destination);
	public void setupPositions(int originx, int originy, int destinationx, int destinationy);
	
	public IMapPosition getOrigin();
	public IMapPosition getDestination();
	
	
	public interface MapMotionCallback {
		
		public void motionFinished(IMapMotion motion);
	}
}
