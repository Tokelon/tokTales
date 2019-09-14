package com.tokelon.toktales.core.engine;

import com.tokelon.toktales.core.engine.content.IContentService;
import com.tokelon.toktales.core.engine.input.IInputService;
import com.tokelon.toktales.core.engine.render.IRenderService;
import com.tokelon.toktales.core.engine.storage.IStorageService;
import com.tokelon.toktales.core.engine.ui.IUIService;

public interface IEngine {

	//public IConfigManager getConfigManager();

	// Add support for custom services?
	//public void addService(String name, IEngineService service);

	public IEnvironment getEnvironment();
	
	
	public IUIService getUIService();
	
	public IContentService getContentService();
	
	public IStorageService getStorageService();
	
	public IRenderService getRenderService();
	
	public IInputService getInputService();
	
}
