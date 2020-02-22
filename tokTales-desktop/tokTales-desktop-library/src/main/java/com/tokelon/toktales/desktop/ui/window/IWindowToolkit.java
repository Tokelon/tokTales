package com.tokelon.toktales.desktop.ui.window;

public interface IWindowToolkit {


	public long getPrimaryMonitor();

	public long[] getMonitors();
	
	
	public void setWindowHint(int hint, int value);
	
	public void setWindowHintString(int hint, String value);
	
	public void setDefaultWindowHints();
	
}
