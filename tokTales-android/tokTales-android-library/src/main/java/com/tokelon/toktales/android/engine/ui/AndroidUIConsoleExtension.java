package com.tokelon.toktales.android.engine.ui;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;

import com.tokelon.toktales.android.activity.IConsoleActivity;
import com.tokelon.toktales.android.ui.AndroidUIService;
import com.tokelon.toktales.core.engine.IEngineService;
import com.tokelon.toktales.core.engine.ServiceException;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.engine.ui.IUIConsoleExtension;
import com.tokelon.toktales.core.game.controller.IConsoleController;

public class AndroidUIConsoleExtension implements IUIConsoleExtension {

	public static final String TAG = "AndroidUIConsoleModule";
	
	
	private AndroidUIService mUIService;
	
	public AndroidUIConsoleExtension() {
		// UI service will be attached
	}
	
	public AndroidUIConsoleExtension(AndroidUIService androidUIService) {
		this.mUIService = androidUIService;
		this.mUIService.addExtension(TAG, this);
	}
	
	
	@Override
	public int openConsoleInput(IConsoleController consoleController) {
		
		Activity currentActivity = mUIService.getUserInterface().getCurrentActivity().asActivity();
		
		if(currentActivity instanceof IConsoleActivity) {
			((IConsoleActivity)currentActivity).getConsoleInput(new ConsoleTextWatcher(consoleController));
			return 0;
		}
		else {
			return 1;
		}
	}

	
	

	private class ConsoleTextWatcher implements TextWatcher {

		private final IConsoleController consoleController;
		
		public ConsoleTextWatcher(IConsoleController consoleController) {
			this.consoleController = consoleController;
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void afterTextChanged(Editable s) {
			String newText = s.toString();
			TokTales.getLog().d(TAG, newText);
			
			consoleController.clear();

			
			int rPosition = newText.indexOf('\r');
			int nPosition = newText.indexOf('\n');
						
			if(rPosition >= 0 || nPosition >= 0) {
				int firstPosition = rPosition < 0 ? nPosition : rPosition;
				
				consoleController.inputText(newText.substring(0, firstPosition));
				consoleController.enter();
				s.clear();
			}
			else {
				consoleController.inputText(newText);
			}
			
		}
	}


	@Override
	public void attachService(IEngineService service) {
		if(!(service instanceof AndroidUIService)) {
			throw new ServiceException(AndroidUIService.class.getSimpleName() + " is required for this extension to work");
		}
		
		mUIService = (AndroidUIService) service;
	}


}
