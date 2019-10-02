package com.tokelon.toktales.android.render.tools;

import com.tokelon.toktales.android.input.dispatch.IAndroidInputProducer;
import com.tokelon.toktales.core.game.screen.view.IScreenViewport;

import android.view.MotionEvent;

public interface IUIControl extends Runnable {


	public void setViewport(IScreenViewport viewport);

	public void startControl();
	public void stopControl();
	
	public boolean onTouch(MotionEvent event);

	
	public interface IUIControlFactory {
		
		public IUIControl create(IUIOverlayProvider overlayProvider);
		public IUIControl create(IUIOverlayProvider overlayProvider, IAndroidInputProducer inputProducer);
	}

}