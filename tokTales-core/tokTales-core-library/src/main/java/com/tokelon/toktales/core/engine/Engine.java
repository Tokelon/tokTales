package com.tokelon.toktales.core.engine;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.content.IContentService;
import com.tokelon.toktales.core.engine.input.IInputService;
import com.tokelon.toktales.core.engine.render.IRenderService;
import com.tokelon.toktales.core.engine.storage.IStorageService;
import com.tokelon.toktales.core.engine.ui.IUIService;

public class Engine implements IEngine {


	private IEngineDriver engineDriver;
	private IEnvironment environment;
	private IUIService uiService;
	private IContentService contentService;
	private IStorageService storageService;
	private IRenderService renderService;
	private IInputService inputService;
	
	@Inject
	public Engine(
			IEngineDriver engineDriver,
			IEnvironment environment,
			IUIService uiService,
			IContentService contentService,
			IStorageService storageService,
			IRenderService renderService,
			IInputService inputService
	) {
		this.engineDriver = engineDriver;
		this.environment = environment;
		this.uiService = uiService;
		this.contentService = contentService;
		this.storageService = storageService;
		this.renderService = renderService;
		this.inputService = inputService;
	}
	
	
	@Override
	public IEngineDriver getEngineDriver() {
		return engineDriver;
	}
	
	@Override
	public IEnvironment getEnvironment() {
		return environment;
	}

	@Override
	public IUIService getUIService() {
		return uiService;
	}

	@Override
	public IContentService getContentService() {
		return contentService;
	}

	@Override
	public IStorageService getStorageService() {
		return storageService;
	}

	@Override
	public IRenderService getRenderService() {
		return renderService;
	}

	@Override
	public IInputService getInputService() {
		return inputService;
	}
	
}
