package com.tokelon.toktales.desktop.ui.window;

public interface IWindowFactory {


	public long getPrimaryMonitor();

	public long[] getMonitors();
	
	
	public void setWindowHint(int hint, int value);
	
	public void setDefaultWindowHints();

	
	public IWindow create(int width, int height, String title);
	public IWindow create(int width, int height, String title, long monitor);
	public IWindow create(int width, int height, String title, long monitor, long share);
	
}
