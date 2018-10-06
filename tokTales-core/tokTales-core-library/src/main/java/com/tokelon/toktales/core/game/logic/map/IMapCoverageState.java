package com.tokelon.toktales.core.game.logic.map;

import com.tokelon.toktales.core.game.model.map.IMapPosition;

public interface IMapCoverageState {

	// Which package should this be in?

	public static final int STATE_COVERAGE_NONE = 1;
	public static final int STATE_COVERAGE_SINGLE = 2;
	public static final int STATE_COVERAGE_MULTIPLE = 3;
	public static final int STATE_COVERAGE_TRANSITION = 4;
	
		
	public int getState();
	public int getPreviousState();
	
	
	public IMapPosition getCenterPosition();
	
	
	public IMapPosition getTransitionOrigin();
	
	public IMapPosition getTransitionDestination();
	
	
	//public IMapRegion getRegion();	// for multiple
	
	
	public interface IMutableMapCoverageState extends IMapCoverageState {
		
		public void setState(int state);
		
		public void setCenterPosition(IMapPosition position);
		public void setCenterPosition(int posx, int posy);
	
		public void setTransitionOrigin(IMapPosition position);
		public void setTransitionOrigin(int posx, int posy);
	
		public void setTransitionDestination(IMapPosition position);
		public void setTransitionDestination(int posx, int posy);
		
	}
}
