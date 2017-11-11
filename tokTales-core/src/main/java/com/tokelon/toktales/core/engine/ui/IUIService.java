package com.tokelon.toktales.core.engine.ui;

import com.tokelon.toktales.core.engine.IEngineService;

public interface IUIService extends IEngineService {

	public static final int EXTERNAL_UI_CODE_DEBUG = 1;

	
	public void openExternalUI(int uiCode);
	
}
