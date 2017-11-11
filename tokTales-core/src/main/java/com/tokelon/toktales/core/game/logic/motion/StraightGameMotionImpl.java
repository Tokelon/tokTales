package com.tokelon.toktales.core.game.logic.motion;

import com.tokelon.toktales.core.game.model.IPoint2f.IMutablePoint2f;

public class StraightGameMotionImpl extends AbstractGameMotion {



	@Override
	public void getPosition(int timePassedMillis, IMutablePoint2f result) {
		result.set(getHorizontalPosition(timePassedMillis), getVerticalPosition(timePassedMillis));
	}

	@Override
	public float getHorizontalPosition(int timePassedMillis) {
		float pos;
		if(getHorizontalVelocity() == 0.0f) {	// TODO: FLOAT equals
			pos = getOriginX();
		}
		else {
			pos = getOriginX() + getHorizontalVelocity() * timePassedMillis / 1000.0f;
		}
		
		return pos;
	}

	@Override
	public float getVerticalPosition(int timePassedMillis) {
		float pos;
		if(getVerticalVelocity() == 0.0f) {	// TODO: FLOAT equals
			pos = getOriginY();
		}
		else {
			pos = getOriginX() + getVerticalVelocity() * timePassedMillis / 1000.0f;
		}
		
		return pos;
	}

}
