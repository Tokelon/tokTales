package com.tokelon.toktales.android.engine.ui;

import com.tokelon.toktales.android.activity.IDebugActivity;
import com.tokelon.toktales.android.ui.IAndroidUIService;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineService;
import com.tokelon.toktales.core.engine.ServiceException;
import com.tokelon.toktales.core.engine.ui.IDebugUIExtension;
import com.tokelon.toktales.tools.android.activity.integration.IIntegratedActivity;

public class AndroidDebugUIExtension implements IDebugUIExtension {

	
	private IAndroidUIService uiService;
	

	@Override
	public void openContextMenu() throws EngineException {
		IIntegratedActivity currentActivity = uiService.getUserInterface().getCurrentActivity();
		
		if(currentActivity instanceof IDebugActivity) {
			IDebugActivity da = (IDebugActivity) currentActivity;
			da.openContextMenu();
		}
		else {
			throw new EngineException("Could not open context menu. Unsupported Activity running");
		}
	}

	
	@Override
	public void attachService(IEngineService service) {
		if(!(service instanceof IAndroidUIService)) {
			throw new ServiceException(IAndroidUIService.class.getSimpleName() + " is required for this extension to work");
		}
		
		uiService = (IAndroidUIService) service;
	}
	
	
}
