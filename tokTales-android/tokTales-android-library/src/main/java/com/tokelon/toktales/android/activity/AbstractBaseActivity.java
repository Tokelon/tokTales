package com.tokelon.toktales.android.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.tokelon.toktales.android.ui.AndroidUIService;
import com.tokelon.toktales.core.engine.TokTales;

public abstract class AbstractBaseActivity extends Activity {	// Rename to AbstractAndroidUIActivity ?

	
	// 
	/* TODO: Implement an open keyboard method,
	 * and a listener for keyboard events that calls the master input handler for ICharInputCallback
	 * 
	 */

	private Handler handler;

	public AbstractBaseActivity() {
		handler = new Handler();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Need this or we can't use the UI framework in onCreate()
		AndroidUIService as = (AndroidUIService) TokTales.getEngine().getUIService();
		as.getUserInterface().updateCurrentActivity(this);
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		
		AndroidUIService as = (AndroidUIService) TokTales.getEngine().getUIService();
		as.getUserInterface().updateCurrentActivity(this);
	}


	@Override
	protected void onPause() {
		super.onPause();
		
		AndroidUIService as = (AndroidUIService) TokTales.getEngine().getUIService();
		as.getUserInterface().clearCurrentActivity();
	}

	
	

	/* UI methods */
	
	public void showKeyboard(final View view) {
		
		handler.post(new Runnable() {
			
			@Override
			public void run() {

				InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				
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
	
	public void hideKeyboard(final View view) {
		
		handler.post(new Runnable() {
			
			@Override
			public void run() {
				InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

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

	
	protected class ProxyTextWatcher implements TextWatcher {
		
		private TextWatcher client;
		
		public ProxyTextWatcher() {
			// Needed for subclass access
		}
		
		public void setClient(TextWatcher textWatcher) {
			this.client = textWatcher;
		}

		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			client.beforeTextChanged(s, start, count, after);
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			client.onTextChanged(s, start, before, count);
		}

		@Override
		public void afterTextChanged(Editable s) {
			client.afterTextChanged(s);
		}
		
	}
	
	
}
