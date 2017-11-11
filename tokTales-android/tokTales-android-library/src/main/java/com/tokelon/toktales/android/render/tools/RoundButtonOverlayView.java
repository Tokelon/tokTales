package com.tokelon.toktales.android.render.tools;

import android.graphics.Rect;

public class RoundButtonOverlayView implements IButtonOverlayView {

	protected int id;
	protected int centerX;
	protected int centerY;
	protected int radius;
	
	
	public RoundButtonOverlayView(int id, int centerX, int centerY, int radius) {
		this.id = id;
		this.centerX = centerX;
		this.centerY = centerY;
		this.radius = radius;
	}

	
	@Override
	public boolean containsPoint(int x, int y) {
		return Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2) < Math.pow(radius, 2);
	}
	
	


	@Override
	public Rect getButtonBounds() {
		Rect res = new Rect();
		getButtonBounds(res);
		return res;
	}


	@Override
	public void getButtonBounds(Rect result) {
		result.left = centerX - radius;
		result.right = centerX + radius;
		result.top = centerY - radius;
		result.bottom = centerY + radius;
	}
	
	
	@Override
	public int getButtonID() {
		return id;
	}
	
	
}
