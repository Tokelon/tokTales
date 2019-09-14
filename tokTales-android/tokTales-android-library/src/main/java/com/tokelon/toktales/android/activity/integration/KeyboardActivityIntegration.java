package com.tokelon.toktales.android.activity.integration;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyboardActivityIntegration implements IKeyboardActivityIntegration {
	/* TODO: Implement an open keyboard method,
	 * and a listener for keyboard events that calls the master input handler for ICharInputCallback
	 */
	
	
	private Activity activity;

	private final Handler handler;
	
	public KeyboardActivityIntegration() {
		this.handler = new Handler();
	}
	
	public KeyboardActivityIntegration(Handler uiHandler) {
		this.handler = uiHandler;
	}
	
	
	@Override
	public void onActivityStart(IIntegratedActivity activity) {
		this.activity = activity.asActivity();
	}

	
	@Override
	public void showKeyboard(final View view) {
		
		handler.post(new Runnable() {
			
			@Override
			public void run() {

				InputMethodManager manager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
				
				view.setFocusable(true);
				view.setFocusableInTouchMode(true);
				view.requestFocus();
				
				manager.showSoftInput(view, 0);
				
			}
		});
		
		
		//manager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
		//manager.toggleSoftInputFromWindow(mRenderView.getWindowToken(), InputMethodManager.SHOW_FORCED, 0);
		/*
		if(editText.requestFocus()) {
			InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			
			inputMethodManager.showSoftInput(editText, 0);	//
			//inputMethodManager.toggleSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.SHOW_FORCED, 0);
			
			//inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
			//inputMethodManager.toggleSoftInputFromWindow(getWindowToken(),0,0);
			//inputMethodManager.toggleSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.SHOW_FORCED, 0);
		 */
	}
	
	@Override
	public void hideKeyboard(final View view) {
		
		handler.post(new Runnable() {
			
			@Override
			public void run() {
				InputMethodManager manager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

				manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
				
			}
		});


		/*
		mRenderView.setFocusable(true);
		mRenderView.setFocusableInTouchMode(true);
		boolean gotFocus = mRenderView.requestFocus();
		TokTales.getLog().d(TAG, "gotFocus: " +gotFocus);
		*/
	}
	
}
