package com.tokelon.toktales.core.engine;

import com.tokelon.toktales.core.engine.content.IContentService;
import com.tokelon.toktales.core.engine.input.IInputService;
import com.tokelon.toktales.core.engine.log.ILogService;
import com.tokelon.toktales.core.engine.render.IRenderService;
import com.tokelon.toktales.core.engine.storage.IStorageService;
import com.tokelon.toktales.core.engine.ui.IUIService;

public class Engine implements IEngine {

	
	private IEnvironment mEnvironment;
	private IUIService mUIService;
	private IContentService mContentService;
	private ILogService mLogService;
	private IStorageService mStorageService;
	private IRenderService mRenderService;
	private IInputService mInputService;
	
	
	private Engine(
			IEnvironment environment,
			IUIService uiService,
			IContentService contentService,
			ILogService logService,
			IStorageService storageService,
			IRenderService renderService,
			IInputService inputService
			) {
		
		this.mEnvironment = environment;
		this.mUIService = uiService;
		this.mContentService = contentService;
		this.mLogService = logService;
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
	public ILogService getLogService() {
		return mLogService;
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
	
	
	public static class EngineFactory {
		
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
	
	
}
