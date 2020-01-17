package com.tokelon.toktales.desktop.ui.window;

public interface IWindow {
	// TODO: Add state with enum values?


	public static final int VSYNC_MODE_OFF = 0;
	public static final int VSYNC_MODE_ON = 1;
	public static final int VSYNC_MODE_HALF = 2;
	
	//public static final int INPUT_CURSOR = ;
	//public static final int INPUT_CURSOR_DISABLED = ;
	

	
	public void create();
	public void show();
	public void hide();
	public void destroy();

	
	public boolean shouldClose();
	
	public void makeContextCurrent();
	//public void detachContext(); // TODO: Add this?
	
	public void swapBuffers();
	
	
	public long getId();
	
	public int getWidth();
	public int getHeight();
	
	public String getTitle();
	
	public long getMonitor();
	
	public int getSwapInterval();
	
	public int getInputMode(int mode);
	
	
	public void setSize(int width, int height);
	
	public void setTitle(String title);
	
	public void setMonitor(long monitor);
	public void setMonitor(long monitor, int xpos, int ypos, int width, int height, int refreshRate);

	public void setSwapInterval(int interval);
	
	public void setInputMode(int mode, int value);
	
}
