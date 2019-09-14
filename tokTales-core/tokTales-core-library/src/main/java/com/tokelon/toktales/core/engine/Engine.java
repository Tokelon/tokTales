package com.tokelon.toktales.core.engine;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.content.IContentService;
import com.tokelon.toktales.core.engine.input.IInputService;
import com.tokelon.toktales.core.engine.render.IRenderService;
import com.tokelon.toktales.core.engine.storage.IStorageService;
import com.tokelon.toktales.core.engine.ui.IUIService;

public class Engine implements IEngine {


	private IEnvironment mEnvironment;
	private IUIService mUIService;
	private IContentService mContentService;
	private IStorageService mStorageService;
	private IRenderService mRenderService;
	private IInputService mInputService;
	
	@Inject
	public Engine(
			IEnvironment environment,
			IUIService uiService,
			IContentService contentService,
			IStorageService storageService,
			IRenderService renderService,
			IInputService inputService
	) {
		this.mEnvironment = environment;
		this.mUIService = uiService;
		this.mContentService = contentService;
		this.mStorageService = storageService;
		this.mRenderService = renderService;
		this.mInputService = inputService;
	}
	
	
	
	@Override
	public IEnvironment getEnvironment() {
		return mEnvironment;
	}

	@Override
	public IUIService getUIService() {
		return mUIService;
	}

	@Override
	public IContentService getContentService() {
		return mContentService;
	}

	@Override
	public IStorageService getStorageService() {
		return mStorageService;
	}

	@Override
	public IRenderService getRenderService() {
		return mRenderService;
	}

	@Override
	public IInputService getInputService() {
		return mInputService;
	}
	
}
