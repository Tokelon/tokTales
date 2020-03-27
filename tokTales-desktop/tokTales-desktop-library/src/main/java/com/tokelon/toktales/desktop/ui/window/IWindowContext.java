package com.tokelon.toktales.desktop.ui.window;

import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.desktop.input.IDesktopInputDriver;
import com.tokelon.toktales.desktop.render.IWindowRenderer;

public interface IWindowContext {


	public void create(IEngineContext engineContext);
	
	public void destroy();
	

	
	public IWindowFactory getWindowFactory();
	
	public IWindowToolkit getWindowToolkit();
	
	
	public IWindow getWindow();
	
	public IWindowRenderer getRenderer();
	
	public IDesktopInputDriver getInputDriver();
	
	
	public interface IWindowContextBuilder {
		public IWindowContext build();
		
		public IWindowContextBuilder withWindowFactory(IWindowFactory windowFactory);
		public IWindowContextBuilder withWindowToolkit(IWindowToolkit windowToolkit);
		
		public IWindowContextBuilder withWindow(IWindow window);
		public IWindowContextBuilder withWindow(IWindowBuilder windowBuilder);
		
		public IWindowContextBuilder withWindowConfigurator(IWindowConfigurator windowConfigurator);
		
		public IWindowContextBuilder withRenderer(IWindowRenderer windowRenderer);
		public IWindowContextBuilder withRenderer(IWindowRendererFactory windowRendererFactory);
		
		public IWindowContextBuilder withInputDriver(IDesktopInputDriver inputDriver);
		public IWindowContextBuilder withInputDriver(IDesktopInputDriverFactory inputDriverFactory);
		public IWindowContextBuilder withInputDriverNone();
	}
	
	
	public interface IWindowRendererFactory {
		
		public IWindowRenderer create(IEngineContext engineContext);
	}
	
	public interface IDesktopInputDriverFactory {
		
		public IDesktopInputDriver create(IEngineContext engineContext);
	}
	
}
