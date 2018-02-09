package com.tokelon.toktales.android.engine.ui;

import com.tokelon.toktales.android.activity.IConsoleActivity;
import com.tokelon.toktales.android.activity.integration.IIntegratedActivity;
import com.tokelon.toktales.android.ui.IAndroidUIService;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineService;
import com.tokelon.toktales.core.engine.ServiceException;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.engine.ui.IConsoleUIExtension;
import com.tokelon.toktales.core.game.controller.IConsoleController;

import android.text.Editable;
import android.text.TextWatcher;

public class AndroidConsoleUIExtension implements IConsoleUIExtension {

	public static final String TAG = "AndroidUIConsoleModule";
	
	
	private IAndroidUIService mUIService;
	
	public AndroidConsoleUIExtension() {
		// UI service will be attached
	}
	
	
	@Override
	public void openConsoleInput(IConsoleController consoleController) throws EngineException {
		IIntegratedActivity currentActivity = mUIService.getUserInterface().getCurrentActivity();
		
		if(currentActivity instanceof IConsoleActivity) {
			((IConsoleActivity)currentActivity).getConsoleInput(new ConsoleTextWatcher(consoleController));
		}
		else {
			throw new EngineException("Failed to get console input: The activity running must be of type " + IConsoleActivity.class.getName());
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
		if(!(service instanceof IAndroidUIService)) {
			throw new ServiceException(IAndroidUIService.class.getSimpleName() + " is required for this extension to work");
		}
		
		mUIService = (IAndroidUIService) service;
	}


}
