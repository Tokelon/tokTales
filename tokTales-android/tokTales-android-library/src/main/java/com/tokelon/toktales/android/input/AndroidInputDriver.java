package com.tokelon.toktales.android.input;

import com.tokelon.toktales.android.render.tools.UIControl;

public class AndroidInputDriver implements IAndroidInputDriver {
	/** This essentially wraps the UIControl that does all the actual input driver work
	 */

	
	private final UIControl uiControl;
	
	public AndroidInputDriver(UIControl uiControl) {
		this.uiControl = uiControl;
	}

}
