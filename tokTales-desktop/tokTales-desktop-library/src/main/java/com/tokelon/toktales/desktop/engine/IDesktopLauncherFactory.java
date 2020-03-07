package com.tokelon.toktales.desktop.engine;

import com.tokelon.toktales.core.engine.IEngineLooper;
import com.tokelon.toktales.core.engine.log.ILoggerFactory;
import com.tokelon.toktales.desktop.ui.window.IWindowBuilder;
import com.tokelon.toktales.desktop.ui.window.IWindowConfigurator;
import com.tokelon.toktales.desktop.ui.window.IWindowContext;
import com.tokelon.toktales.desktop.ui.window.IWindowContext.IWindowContextBuilder;
import com.tokelon.toktales.desktop.ui.window.IWindowHandler;
import com.tokelon.toktales.tools.core.sub.inject.config.IHierarchicalInjectConfig;

public interface IDesktopLauncherFactory {


	public IWindowEngineLauncher createDefault();
	public IWindowEngineLauncherBuilder createDefaultBuilder();
	
	public IWindowEngineLauncher createWindowLauncher();
	public IWindowEngineLauncherBuilder createWindowLauncherBuilder();

	public IDesktopEngineLauncher createDesktopLauncher();
	public IDesktopEngineLauncherBuilder createDesktopLauncherBuilder();
	
	
	
	public interface IDesktopEngineLauncherBuilder {
		public IDesktopEngineLauncher build();
		
		public IDesktopEngineLauncherBuilder withInjectConfig(IHierarchicalInjectConfig injectConfig);
		
		public IDesktopEngineLauncherBuilder withEngineLooper(IEngineLooper defaultLooper);
		public IDesktopEngineLauncherBuilder withLoggerFactory(ILoggerFactory loggerFactory);
	}
	
	public interface IWindowEngineLauncherBuilder {
		public IWindowEngineLauncher build();
		
		public IWindowEngineLauncherBuilder withWindow(IWindowHandler windowHandler);
		public IWindowEngineLauncherBuilder withWindow(IWindowContext windowContext);
		public IWindowEngineLauncherBuilder withWindow(IWindowContextBuilder windowContextBuilder);
		
		public IWindowEngineLauncherBuilder withWindow(IWindowBuilder windowBuilder);
		public IWindowEngineLauncherBuilder withWindow(IWindowConfigurator windowConfigurator);
		public IWindowEngineLauncherBuilder withWindow(IWindowBuilder windowBuilder, IWindowConfigurator windowConfigurator);
		
		public IWindowEngineLauncherBuilder withInjectConfig(IHierarchicalInjectConfig injectConfig);
		public IWindowEngineLauncherBuilder withEngineLooper(IEngineLooper defaultLooper);
		public IWindowEngineLauncherBuilder withLoggerFactory(ILoggerFactory loggerFactory);
	}
	
}
