package com.tokelon.toktales.android.input;

import com.tokelon.toktales.android.render.tools.IUIControl;

public class AndroidInputDriver implements IAndroidInputDriver {
	// This essentially wraps the UIControl that does all the actual input driver work


	private final IUIControl uiControl;
	
	public AndroidInputDriver(IUIControl uiControl) {
		this.uiControl = uiControl;
	}

}
