package com.tokelon.toktales.core.engine.setup.manual;

import com.tokelon.toktales.core.engine.Engine;
import com.tokelon.toktales.core.engine.IEnvironment;
import com.tokelon.toktales.core.engine.content.IContentService;
import com.tokelon.toktales.core.engine.input.IInputService;
import com.tokelon.toktales.core.engine.log.ILogService;
import com.tokelon.toktales.core.engine.render.IRenderService;
import com.tokelon.toktales.core.engine.storage.IStorageService;
import com.tokelon.toktales.core.engine.ui.IUIService;

@Deprecated
public class EngineFactory {

	private IEnvironment environment;
	private IUIService uiService;
	private IContentService contentService;
	private ILogService logService;
	private IStorageService storageService;
	private IRenderService renderService;
	private IInputService inputService;
	
	public Engine build() {
		Engine engine = new Engine(
				environment,
				uiService,
				contentService,
				logService,
				storageService,
				renderService,
				inputService);
		
		return engine;
	}
	
	
	public void setEnvironment(IEnvironment environment) {
		this.environment = environment;
	}
	
	public void setUIService(IUIService uiService) {
		this.uiService = uiService;
	}
	
	public void setContentService(IContentService contentService) {
		this.contentService = contentService;
	}
	
	public void setLogService(ILogService logService) {
		this.logService = logService;
	}
	
	public void setStorageService(IStorageService storageService) {
		this.storageService = storageService;
	}
	
	public void setRenderService(IRenderService renderService) {
		this.renderService = renderService;
	}
	
	public void setInputService(IInputService inputService) {
		this.inputService = inputService;
	}
	
}
