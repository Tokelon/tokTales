package com.tokelon.toktales.desktop.ui.window;

public interface IWindowFactory {


	public long getPrimaryMonitor();

	public long[] getMonitors();
	
	
	public void setWindowHint(int hint, int value);
	
	public void setDefaultWindowHints();

	
	public IWindow create(int width, int height, String title);
	public IWindow create(int width, int height, String title, long monitor);
	public IWindow create(int width, int height, String title, long monitor, long share);
	
	
	public interface IWindowBuilder {
		
		public IWindow build();
		
		
		public IWindowBuilder withSize(int width, int height);
		public IWindowBuilder withTitle(String title);
		public IWindowBuilder withMonitor(long monitor);
		public IWindowBuilder withPrimaryMonitor();
		public IWindowBuilder withShare(long share);
		
		public IWindowBuilder withDefaultHints();
		public IWindowBuilder withWindowHint(int hint, int value);
		
		
		//public IWindowBuilder withSwapInterval(int interval);
		//public IWindowBuilder withInputMode(int mode, int value);
		//public IWindowBuilder withNoContext();
		
	}
	
}
