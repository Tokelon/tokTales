package com.tokelon.toktales.core.game.logic.map;

import com.tokelon.toktales.core.game.logic.map.IMapCoverageState.IMutableMapCoverageState;
import com.tokelon.toktales.core.game.model.map.IMapPosition;
import com.tokelon.toktales.core.game.model.map.MapPositionImpl;

public class MapCoverageState implements IMutableMapCoverageState {

	
	private int mState = STATE_COVERAGE_NONE;
	private int mPreviousState = STATE_COVERAGE_NONE;
	
	private final MapPositionImpl mCenterPosition = new MapPositionImpl();

	private final MapPositionImpl mTransitionOrigin = new MapPositionImpl();
	private final MapPositionImpl mTransitionDestination = new MapPositionImpl();
	
	
	
	@Override
	public int getState() {
		return mState;
	}
	@Override
	public int getPreviousState() {
		return mPreviousState;
	}
	
	
	@Override
	public IMapPosition getCenterPosition() {
		return mCenterPosition;
	}

	@Override
	public IMapPosition getTransitionOrigin() {
		return mTransitionOrigin;
	}
	@Override
	public IMapPosition getTransitionDestination() {
		return mTransitionDestination;
	}
	
	
	@Override
	public void setState(int state) {
		this.mPreviousState = mState;
		this.mState = state;
	}

	
	@Override
	public void setCenterPosition(IMapPosition position) {
		mCenterPosition.set(position);
	}
	@Override
	public void setCenterPosition(int posx, int posy) {
		mCenterPosition.set(posx, posy);
	}

	@Override
	public void setTransitionOrigin(IMapPosition position) {
		mTransitionOrigin.set(position);
	}
	@Override
	public void setTransitionOrigin(int posx, int posy) {
		mTransitionOrigin.set(posx, posy);
	}

	@Override
	public void setTransitionDestination(IMapPosition position) {
		mTransitionDestination.set(position);
	}
	@Override
	public void setTransitionDestination(int posx, int posy) {
		mTransitionDestination.set(posx, posy);
	}

}
