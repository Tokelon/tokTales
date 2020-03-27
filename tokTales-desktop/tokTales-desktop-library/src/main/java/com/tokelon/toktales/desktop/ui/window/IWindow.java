package com.tokelon.toktales.desktop.ui.window;

public interface IWindow {
	// TODO: Add state with enum?


	public void create();
	public void show();
	public void hide();
	public void destroy();


	public boolean shouldClose();

	public void makeContextCurrent();
	public void detachContext();

	public void swapBuffers();


	public long getId();

	public int getWidth();
	public int getHeight();

	public int getPositionX();
	public int getPositionY();

	public String getTitle();

	public long getMonitor();

	public int getSwapInterval();

	public int getInputMode(int mode);

	public int getAttribute(int attribute);

	public int getFrameBufferWidth();
	public int getFrameBufferHeight();

	public boolean isVisible();

	public boolean isFullscreen();

	public boolean isBorderless();

	public long getCurrentMonitor();


	public void setSize(int width, int height);

	public void setPosition(int x, int y);

	public void setTitle(String title);

	public void setMonitor(long monitor);
	public void setMonitor(long monitor, int xpos, int ypos, int width, int height, int refreshRate);

	public void setSwapInterval(int interval);

	public void setInputMode(int mode, int value);

	public void setAttribute(int attribute, int value);


	public void setWindowed();
	public void setWindowed(long monitor, int width, int height);
	public void setWindowed(int x, int y, int width, int height);

	public void setFullscreen();
	public void setFullscreen(long monitor);

	public void setBorderless(); // Should be called setBorderlessFullscreen?
	public void setBorderless(long monitor);

}
