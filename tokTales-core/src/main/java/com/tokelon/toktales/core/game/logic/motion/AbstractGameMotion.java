package com.tokelon.toktales.core.game.logic.motion;



public abstract class AbstractGameMotion implements IGameMotion {



	private boolean mMotionStopped;

	private float mVelocityHorizontal = 0.0f;
	private float mVelocityVertical = 0.0f;
	
	private float mOriginX = 0.0f;
	private float mOriginY = 0.0f;
	private boolean mHasOrigin = false;
	
	private float mDestinationX = 0.0f;
	private float mDestinationY = 0.0f;
	private boolean mHasDestination = false;
	
	

	@Override
	public int getState(int timePassedMillis) {
		
		// TODO: Float Equals
		if(mVelocityHorizontal == 0.0f && mVelocityVertical == 0.0f) {
			return STATE_NONE;
		}
		else if(timePassedMillis <= 0) {
			return STATE_STILL;
		}
		else if(mMotionStopped) {	// Return finished regardless whether there is a destination or not
			return STATE_FINISHED;  // Maybe return STATE_STOPPED instead of this
		}
		else if(mHasDestination) {
			// Calculate remaning time
			
			float positionx = getHorizontalPosition(timePassedMillis);
			float positiony = getVerticalPosition(timePassedMillis);
			
			if(positionx >= mDestinationX && positiony >= mDestinationY) {	// TODO: FLOAT compare
				return STATE_FINISHED;
			}
			else {
				return STATE_MOVING;
			}
		}
		else {
			return STATE_MOVING;
		}
	}

	
	
	
	@Override
	public void stopMotion() {
		this.mMotionStopped = true;
	}
	
	@Override
	public void resetMotion() {
		this.mMotionStopped = false;
	}
	
	@Override
	public boolean isMotionStopped() {
		return mMotionStopped;
	}
	

	@Override
	public void setVelocity(float velocityHorizontal, float velocityVertical) {
		this.mVelocityHorizontal = velocityHorizontal;
		this.mVelocityVertical = velocityVertical;
	}

	@Override
	public float getHorizontalVelocity() {
		return mVelocityHorizontal;
	}

	@Override
	public float getVerticalVelocity() {
		return mVelocityVertical;
	}

	
	@Override
	public void setOrigin(float originx, float originy) {
		this.mOriginX = originx;
		this.mOriginY = originy;
		this.mHasOrigin = true;
	}

	@Override
	public void removeOrigin() {
		this.mOriginX = 0.0f;
		this.mOriginY = 0.0f;
		this.mHasOrigin = false;
	}

	@Override
	public float getOriginX() {
		return mOriginX;
	}

	@Override
	public float getOriginY() {
		return mOriginY;
	}

	@Override
	public boolean hasOrigin() {
		return mHasOrigin;
	}

	
	@Override
	public void setDestination(float destinationx, float destinationy) {
		this.mDestinationX = destinationx;
		this.mDestinationY = destinationy;
		this.mHasDestination = true;
	}

	@Override
	public void removeDestination() {
		this.mDestinationX = 0.0f;
		this.mDestinationY = 0.0f;
		this.mHasDestination = false;
	}

	@Override
	public float getDestinationX() {
		return mDestinationX;
	}

	@Override
	public float getDestinationY() {
		return mDestinationY;
	}

	@Override
	public boolean hasDestination() {
		return mHasDestination;
	}
	
	
}
