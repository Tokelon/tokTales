package com.tokelon.toktales.android.render.tools;

import android.graphics.Rect;

public class CrossControlOverlayView implements ICrossControlOverlayView {

	
	private final Rect mCrossBounds = new Rect();
	
	private final Rect mControlBoundsLeft = new Rect();
	private final Rect mControlBoundsUp = new Rect();
	private final Rect mControlBoundsRight = new Rect();
	private final Rect mControlBoundsDown = new Rect();
	
	
	public CrossControlOverlayView(Rect bounds) {
		this.mCrossBounds.set(bounds);
		
		int horThird = bounds.width() / 3;
		int verThird = bounds.height() / 3;
		
		
		mControlBoundsLeft.left = mCrossBounds.left;
		mControlBoundsLeft.right = mControlBoundsLeft.left + horThird;
		mControlBoundsLeft.top = mCrossBounds.top + verThird;
		mControlBoundsLeft.bottom = mControlBoundsLeft.top + verThird;
		
		mControlBoundsRight.right = mCrossBounds.right;
		mControlBoundsRight.left = mControlBoundsRight.right - horThird;
		mControlBoundsRight.top = mCrossBounds.top + verThird;
		mControlBoundsRight.bottom = mControlBoundsRight.top + verThird;
		
		mControlBoundsUp.left = mCrossBounds.left + horThird;
		mControlBoundsUp.right = mControlBoundsUp.left + horThird;
		mControlBoundsUp.top = mCrossBounds.top;
		mControlBoundsUp.bottom = mControlBoundsUp.top + verThird;
		
		mControlBoundsDown.left = mCrossBounds.left + horThird;
		mControlBoundsDown.right = mControlBoundsDown.left + horThird;
		mControlBoundsDown.bottom = mCrossBounds.bottom;
		mControlBoundsDown.top = mControlBoundsDown.bottom - verThird;
		
	}
	
	

	
	
	@Override
	public Rect getCrossBounds() {
		Rect result = new Rect(mCrossBounds);
		return result;
	}
	
	@Override
	public void getCrossBounds(Rect result) {
		result.set(mCrossBounds);
	}
	


	@Override
	public Rect getControlBounds(int crossControl) {
		Rect res = new Rect();
		getControlBounds(crossControl, res);
		return res;
	}

	@Override
	public void getControlBounds(int crossControl, Rect result) {
		if(result == null) {
			throw new NullPointerException();
		}

		
		Rect src;
		switch (crossControl) {
		case CROSS_CONTROL_LEFT:
			src = mControlBoundsLeft;
			break;
		case CROSS_CONTROL_UP:
			src = mControlBoundsUp;
			break;
		case CROSS_CONTROL_RIGHT:
			src = mControlBoundsRight;
			break;
		case CROSS_CONTROL_DOWN:
			src = mControlBoundsDown;
			break;
		default:
			throw new IllegalArgumentException("No control for value: " +crossControl);
		}
		
		
		result.set(src);
	}
	
	


	@Override
	public int getControlInCross(int x, int y) {
		if(!mCrossBounds.contains(x, y)) {
			return CROSS_OUTSIDE;
		}
		
		int controlRes;
		if(mControlBoundsLeft.contains(x, y)) {
			controlRes = CROSS_CONTROL_LEFT; 
		}
		else if(mControlBoundsUp.contains(x, y)) {
			controlRes = CROSS_CONTROL_UP;
		}
		else if(mControlBoundsRight.contains(x, y)) {
			controlRes = CROSS_CONTROL_RIGHT;
		}
		else if(mControlBoundsDown.contains(x, y)) {
			controlRes = CROSS_CONTROL_DOWN;
		}
		else {
			controlRes = CROSS_CONTROL_NONE;
		}
		
		return controlRes;
	}




	@Override
	public boolean containsPoint(int x, int y) {
		return mCrossBounds.contains(x, y);
	}
	
}
