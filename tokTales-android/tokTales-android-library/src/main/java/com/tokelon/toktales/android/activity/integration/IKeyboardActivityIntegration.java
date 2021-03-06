package com.tokelon.toktales.android.activity.integration;

import com.tokelon.toktales.tools.android.activity.integration.IActivityIntegration;

import android.os.Handler;
import android.view.View;

public interface IKeyboardActivityIntegration extends IActivityIntegration {


	public void showKeyboard(View view);

	public void hideKeyboard(View view);
	
	
	public interface IKeyboardActivityIntegrationFactory extends IActivityIntegrationFactory {
		
		public IKeyboardActivityIntegration create(Handler handler);
	}
	
}